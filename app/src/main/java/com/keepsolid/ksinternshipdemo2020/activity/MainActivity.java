package com.keepsolid.ksinternshipdemo2020.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.keepsolid.ksinternshipdemo2020.R;
import com.keepsolid.ksinternshipdemo2020.activity.base.BaseActivity;
import com.keepsolid.ksinternshipdemo2020.api.RestClient;
import com.keepsolid.ksinternshipdemo2020.model.GitRepoErrorItem;
import com.keepsolid.ksinternshipdemo2020.model.GitRepoItem;
import com.keepsolid.ksinternshipdemo2020.model.GitResponse;
import com.keepsolid.ksinternshipdemo2020.utils.KeyboardUtils;
import com.keepsolid.ksinternshipdemo2020.utils.adapter.GitRepoRecyclerAdapter;
import com.keepsolid.ksinternshipdemo2020.utils.listener.OnGitRepoRecyclerItemClickListener;

import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;

public class MainActivity extends BaseActivity {

    private RecyclerView recycler;
    private View loaderBlock;

    private AppCompatButton goButton;
    private ProgressBar progressBar;
    private AppCompatEditText usernameInput;
    private ArrayList<GitRepoItem> items;
    private GitRepoRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolbar(getString(R.string.app_name));

        loaderBlock = findViewById(R.id.loader_block);
        recycler = findViewById(R.id.rv_recycler);
        usernameInput = findViewById(R.id.et_username_input);
        goButton = findViewById(R.id.btn_go);

        items = new ArrayList<>();

        adapter = new GitRepoRecyclerAdapter(items, this, new OnGitRepoRecyclerItemClickListener() {
            @Override
            public void onItemClick(View v, int position, String url) {
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

        usernameInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    handleSearchAction();
                    return true;
                }

                return false;
            }
        });

    }

    private void openRepo(String url) {
        try {
            Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(myIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "No application can handle this request."
                    + " Please install a webbrowser", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    private void handleSearchAction() {
        if (TextUtils.isEmpty(usernameInput.getText().toString())) {
            usernameInput.requestFocus();
        } else {
            KeyboardUtils.hide(usernameInput);
            loadRepos(usernameInput.getText().toString());
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

    private void loadRepos(String username) {
        showProgressBlock();
        RestClient.getInstance().getService().getUserRepos(username).enqueue(new Callback<GitResponse>() {

            @Override
            public void onResponse(@NotNull Call<GitResponse> call, @NotNull Response<GitResponse> response) {

                if (!response.isSuccessful()) {
                    Converter<ResponseBody, GitRepoErrorItem> converter = RestClient.getInstance().getRetrofit().responseBodyConverter(GitRepoErrorItem.class, new Annotation[0]);

                    try {
                        GitRepoErrorItem repoError = converter.convert(response.errorBody());
                        makeErrorToast(repoError.getMessage() + " \n Details: " + repoError.getDocumentation_url());
                    } catch (Exception e) {
                        makeErrorToast("Unhandled error. Code: " + response.code());
                    }

                    hideProgressBlock();
                    return;
                }

                items.clear();
                items.addAll(response.body().getItems());
                adapter.notifyDataSetChanged();
                hideProgressBlock();
            }

            @Override
            public void onFailure(@NotNull Call<GitResponse> call, @NotNull Throwable t) {
                makeErrorToast("Error occurred during request: " + t.getMessage());
                t.printStackTrace();
                hideProgressBlock();
            }
        });
    }
}
