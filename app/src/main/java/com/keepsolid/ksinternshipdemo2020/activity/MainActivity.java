package com.keepsolid.ksinternshipdemo2020.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.keepsolid.ksinternshipdemo2020.R;
import com.keepsolid.ksinternshipdemo2020.activity.base.BaseActivity;
import com.keepsolid.ksinternshipdemo2020.api.ApiCallback;
import com.keepsolid.ksinternshipdemo2020.api.RestClient;
import com.keepsolid.ksinternshipdemo2020.model.GitRepoErrorItem;
import com.keepsolid.ksinternshipdemo2020.model.GitRepoItem;
import com.keepsolid.ksinternshipdemo2020.model.GitResponse;
import com.keepsolid.ksinternshipdemo2020.utils.ApplicationSettingsManager;
import com.keepsolid.ksinternshipdemo2020.utils.KeyboardUtils;
import com.keepsolid.ksinternshipdemo2020.utils.adapter.GitRepoRecyclerAdapter;
import com.keepsolid.ksinternshipdemo2020.utils.listener.OnGitRepoRecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class MainActivity extends BaseActivity {

    private RecyclerView recycler;
    private View loaderBlock;

    private AppCompatButton goButton;
    private ProgressBar progressBar;
    private AppCompatEditText searchQueryInput;
    private AppCompatCheckBox showUserRepos;
    private AppCompatCheckBox dontCleadList;
    private ArrayList<GitRepoItem> items;
    private GitRepoRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolbar(getString(R.string.app_name));

        loaderBlock = findViewById(R.id.loader_block);
        recycler = findViewById(R.id.rv_recycler);
        searchQueryInput = findViewById(R.id.et_username_input);
        goButton = findViewById(R.id.btn_go);
        showUserRepos = findViewById(R.id.cbx_user_repo);
        dontCleadList = findViewById(R.id.cbx_dont_clear);

        initCheckBox();

        items = new ArrayList<>();

        adapter = new GitRepoRecyclerAdapter(items, this, new OnGitRepoRecyclerItemClickListener() {
            @Override
            public void onItemClick(View v, int position, Uri url) {
                openRepo(url);
            }
        });

        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(adapter);

        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleSearchAction();
            }
        });

        searchQueryInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    handleSearchAction();
                    return true;
                }

                return false;
            }
        });

        checkCachedItems();
    }

    private void checkCachedItems() {
        List<GitRepoItem> cachedItems = ApplicationSettingsManager.getCachedItems(MainActivity.this);
        if (cachedItems != null && !cachedItems.isEmpty()) {
            items.addAll(cachedItems);
        }
    }

    private void initCheckBox() {

        showUserRepos.setChecked(ApplicationSettingsManager.isSearchByUserEnabled(MainActivity.this));
        dontCleadList.setChecked(ApplicationSettingsManager.isDontClearListEnabled(MainActivity.this));


        showUserRepos.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                ApplicationSettingsManager.setSearchByUserEnabled(MainActivity.this, isChecked);
            }
        });

        dontCleadList.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                ApplicationSettingsManager.setDontClearListEnabled(MainActivity.this, isChecked);
            }
        });

    }

    private void doRequest(String query) {
        if (showUserRepos.isChecked()) {
            getReposByUserName(query);
        } else {
            searchReposByName(query);
        }
    }

    private void openRepo(Uri url) {
        try {
            Intent myIntent = new Intent(Intent.ACTION_VIEW, url);
            startActivity(myIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "No application can handle this request."
                    + " Please install a webbrowser", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    private void handleSearchAction() {
        if (TextUtils.isEmpty(searchQueryInput.getText().toString())) {
            searchQueryInput.requestFocus();
        } else {
            KeyboardUtils.hide(searchQueryInput);
            doRequest(searchQueryInput.getText().toString());
        }
    }

    private void makeErrorToast(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }

    private void showProgressBlock() {
        if (loaderBlock != null) {
            loaderBlock.setVisibility(View.VISIBLE);
        }
    }

    private void hideProgressBlock() {
        if (loaderBlock != null) {
            loaderBlock.setVisibility(View.GONE);
        }
    }

    private void updateList(List<GitRepoItem> itemsToUpdate) {
        if (!dontCleadList.isChecked()) {
            items.clear();
        }
        items.addAll(itemsToUpdate);
        adapter.notifyDataSetChanged();
        ApplicationSettingsManager.cacheLoadedItems(MainActivity.this, items);
    }

    private void handleError(GitRepoErrorItem errorItem) {
        if (TextUtils.isEmpty(errorItem.getDocumentation_url())) {
            makeErrorToast(errorItem.getMessage());
        } else {
            makeErrorToast(errorItem.getMessage() + errorItem.getDocumentation_url());
        }
    }

    private void searchReposByName(String repoName) {
        showProgressBlock();
        RestClient.getInstance().getService().searchRepos(repoName).enqueue(new ApiCallback<GitResponse>() {

            @Override
            public void success(Response<GitResponse> response) {
                updateList(response.body().getItems());
                hideProgressBlock();
            }

            @Override
            public void failure(GitRepoErrorItem gitRepoError) {
                handleError(gitRepoError);
                hideProgressBlock();
            }
        });
    }

    private void getReposByUserName(String username) {
        showProgressBlock();
        RestClient.getInstance().getService().getReposByUserName(username).enqueue(new ApiCallback<List<GitRepoItem>>() {

            @Override
            public void success(Response<List<GitRepoItem>> response) {
                updateList(response.body());
                hideProgressBlock();
            }

            @Override
            public void failure(GitRepoErrorItem gitRepoError) {
                handleError(gitRepoError);
                hideProgressBlock();
            }
        });
    }
}
