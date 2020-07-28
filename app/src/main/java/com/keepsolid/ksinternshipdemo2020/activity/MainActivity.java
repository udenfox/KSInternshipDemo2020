package com.keepsolid.ksinternshipdemo2020.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
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
import com.keepsolid.ksinternshipdemo2020.model.GitRepoItem;
import com.keepsolid.ksinternshipdemo2020.utils.KeyboardUtils;
import com.keepsolid.ksinternshipdemo2020.utils.adapter.GitRepoRecyclerAdapter;
import com.keepsolid.ksinternshipdemo2020.utils.listener.OnGitRepoRecyclerItemClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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
            new LoadReposTask().execute(usernameInput.getText().toString());
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

    public class LoadReposTask extends AsyncTask<String, Void, String> {

        private OkHttpClient client = new OkHttpClient();

        // https://docs.github.com/en/rest/reference/repos#list-repositories-for-a-user
        //private final static String URL = "https://api.github.com/users/%s/repos";

        // https://docs.github.com/en/rest/reference/search#search-repositories
        private final static String URL = "https://api.github.com/search/repositories?q=%s";


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            items.clear();
            showProgressBlock();
        }

        @Override
        protected void onPostExecute(String error) {
            super.onPostExecute(error);
            hideProgressBlock();
            if (error != null) {
                makeErrorToast(error);
            } else {
                adapter.notifyDataSetChanged();
            }

        }

        @Override
        protected String doInBackground(String... strings) {

            String repoName = strings[0];

            Request repoReq = new Request.Builder()
                    .url(String.format(URL, repoName))
                    .build();


            Response response;
            try {
                response = client.newCall(repoReq).execute();
            } catch (IOException e) {
                e.printStackTrace();
                return "Unhandled server error";
            }

            String jsonString;
            try {
                jsonString = response.body().string();
            } catch (IOException | NullPointerException e) {
                e.printStackTrace();
                return "Unhandled parsing error";
            }

            if (response.isSuccessful()) {

                try {
                    JSONObject respJson = new JSONObject(jsonString);
                    JSONArray array = respJson.getJSONArray("items");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        items.add(new GitRepoItem(object));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    return "JSON parsing error";
                }

            } else {
                try {
                    JSONObject errorObject = new JSONObject(jsonString);
                    if (errorObject.has("message")) {
                        return errorObject.getString("message");
                    } else {
                        return "Request error with no message";
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    return "Error parsing error";
                }
            }

            return null;
        }
    }

}
