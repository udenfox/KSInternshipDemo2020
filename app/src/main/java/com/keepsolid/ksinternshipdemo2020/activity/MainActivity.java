package com.keepsolid.ksinternshipdemo2020.activity;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.keepsolid.ksinternshipdemo2020.R;
import com.keepsolid.ksinternshipdemo2020.base.BaseActivity;
import com.keepsolid.ksinternshipdemo2020.api.ApiCallback;
import com.keepsolid.ksinternshipdemo2020.api.RestClient;
import com.keepsolid.ksinternshipdemo2020.model.GitRepoErrorItem;
import com.keepsolid.ksinternshipdemo2020.model.GitRepoItem;
import com.keepsolid.ksinternshipdemo2020.model.GitResponse;
import com.keepsolid.ksinternshipdemo2020.screens.main.MainContract;
import com.keepsolid.ksinternshipdemo2020.screens.main.MainFragment;
import com.keepsolid.ksinternshipdemo2020.screens.main.MainLocalPresenter;
import com.keepsolid.ksinternshipdemo2020.screens.main.MainPresenter;
import com.keepsolid.ksinternshipdemo2020.utils.ApplicationSettingsManager;
import com.keepsolid.ksinternshipdemo2020.utils.KeyboardUtils;
import com.keepsolid.ksinternshipdemo2020.utils.adapter.GitRepoRecyclerAdapter;
import com.keepsolid.ksinternshipdemo2020.utils.listener.OnGitRepoRecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class MainActivity extends BaseActivity {

    private FrameLayout fragmentContainer;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentContainer = findViewById(R.id.fragment_container);

        MainContract.Presenter presenter;
        ApplicationSettingsManager applicationSettingsManager = new ApplicationSettingsManager(MainActivity.this);

        if (isNetworkConnected()) {
            presenter = new MainPresenter(applicationSettingsManager, getDatabase());
        } else {
            presenter = new MainLocalPresenter(applicationSettingsManager, getDatabase());
        }

        MainFragment mainFragment = new MainFragment();
        mainFragment.setPresenter(presenter);

        getSupportFragmentManager().beginTransaction().add(fragmentContainer.getId(), mainFragment).commit();

    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

}
