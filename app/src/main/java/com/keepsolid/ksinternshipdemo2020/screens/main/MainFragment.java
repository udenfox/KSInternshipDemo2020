package com.keepsolid.ksinternshipdemo2020.screens.main;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.keepsolid.ksinternshipdemo2020.R;
import com.keepsolid.ksinternshipdemo2020.activity.MainActivity;
import com.keepsolid.ksinternshipdemo2020.base.BaseFragment;
import com.keepsolid.ksinternshipdemo2020.base.BasePresenter;
import com.keepsolid.ksinternshipdemo2020.base.BaseView;
import com.keepsolid.ksinternshipdemo2020.model.GitRepoItem;
import com.keepsolid.ksinternshipdemo2020.utils.ApplicationSettingsManager;
import com.keepsolid.ksinternshipdemo2020.utils.KeyboardUtils;
import com.keepsolid.ksinternshipdemo2020.utils.adapter.GitRepoRecyclerAdapter;
import com.keepsolid.ksinternshipdemo2020.utils.listener.OnGitRepoRecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;


public class MainFragment extends BaseFragment implements MainContract.View {

    private RecyclerView recycler;
    private View loaderBlock;

    private AppCompatButton goButton;
    private ProgressBar progressBar;
    private AppCompatEditText searchQueryInput;
    private AppCompatCheckBox showUserRepos;
    private AppCompatCheckBox dontCleadList;
    private ArrayList<GitRepoItem> items;
    private GitRepoRecyclerAdapter adapter;

    private MainContract.Presenter presenter;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);

        initToolbar(getString(R.string.app_name), v);

        loaderBlock = v.findViewById(R.id.loader_block);
        recycler = v.findViewById(R.id.rv_recycler);
        searchQueryInput = v.findViewById(R.id.et_username_input);
        goButton = v.findViewById(R.id.btn_go);
        showUserRepos = v.findViewById(R.id.cbx_user_repo);
        dontCleadList = v.findViewById(R.id.cbx_dont_clear);

        initCheckBox();

        items = new ArrayList<>();

        adapter = new GitRepoRecyclerAdapter(items, getContext(), new OnGitRepoRecyclerItemClickListener() {
            @Override
            public void onItemClick(View v, int position, Uri url) {
                openRepo(url);
            }
        });

        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler.setAdapter(adapter);

        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.searchRepos(searchQueryInput.getText().toString());
            }
        });

        searchQueryInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    presenter.searchRepos(searchQueryInput.getText().toString());
                    return true;
                }

                return false;
            }
        });

        presenter.takeView(this);


        return v;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.dropView();
    }

    @Override
    public void showProgress() {
        loaderBlock.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        loaderBlock.setVisibility(View.GONE);
    }

    @Override
    public void hideKeyboard() {
        KeyboardUtils.hide(searchQueryInput);
    }

    @Override
    public void showInputError() {
        searchQueryInput.requestFocus();
    }

    @Override
    public void showRequestError(@NonNull String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void observeItems(LiveData<List<GitRepoItem>> itemsLiveData) {

        itemsLiveData.observe(MainFragment.this, new Observer<List<GitRepoItem>>() {
            @Override
            public void onChanged(List<GitRepoItem> gitRepoItems) {
                items.clear();
                items.addAll(gitRepoItems);
                adapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public void setDontClearChecked(boolean isChecked) {
        dontCleadList.setChecked(isChecked);
    }

    @Override
    public void setSearchByRepoChecked(boolean isChecked) {
        showUserRepos.setChecked(isChecked);
    }

    @Override
    public void stopObserving(LiveData<List<GitRepoItem>> liveRepoData) {
        liveRepoData.removeObservers(MainFragment.this);
    }

    private void initCheckBox() {

        showUserRepos.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                presenter.setSearchByUserNameEnabled(isChecked);
            }
        });

        dontCleadList.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                presenter.setDontClearResults(isChecked);
            }
        });

    }

    private void openRepo(Uri url) {
        try {
            Intent myIntent = new Intent(Intent.ACTION_VIEW, url);
            startActivity(myIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getContext(), "No application can handle this request."
                    + " Please install a webbrowser", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

}