package com.keepsolid.ksinternshipdemo2020.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.DrawableRes;

import com.keepsolid.ksinternshipdemo2020.R;
import com.keepsolid.ksinternshipdemo2020.activity.base.BaseActivity;
import com.keepsolid.ksinternshipdemo2020.fragment.FragmentChooser;
import com.keepsolid.ksinternshipdemo2020.fragment.FragmentViewer;
import com.keepsolid.ksinternshipdemo2020.utils.Constants;
import com.keepsolid.ksinternshipdemo2020.utils.listeners.PictureSelectListener;

public class MainActivity extends BaseActivity {

    private FragmentChooser fragmentChooser;
    private FragmentViewer fragmentViewer;

    private PictureSelectListener pictureSelectListener;

    boolean inLandscapeMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolbar(getString(R.string.app_name));

        inLandscapeMode = findViewById(R.id.fragment_two) != null;

        fragmentChooser = (FragmentChooser) getSupportFragmentManager().findFragmentById(R.id.fragment_one);
        if (inLandscapeMode) {
            fragmentViewer = (FragmentViewer) getSupportFragmentManager().findFragmentById(R.id.fragment_two);
        }

        pictureSelectListener = new PictureSelectListener() {
            @Override
            public void onCatSelected() {
                displaySelected(R.drawable.cat);
            }

            @Override
            public void onFoodSelected() {
                displaySelected(R.drawable.food);
            }

            @Override
            public void onSunsetSelected() {
                displaySelected(R.drawable.sunset);
            }

            @Override
            public void onSpaceSelected() {
                displaySelected(R.drawable.space);
            }
        };

        fragmentChooser.setPictureSelectListener(pictureSelectListener);

    }

    private void displaySelected(@DrawableRes int selectedImageResId) {

        if (inLandscapeMode) {
            fragmentViewer.displayResource(selectedImageResId);
        } else {
            Intent viewIntent = new Intent(MainActivity.this, SecondActivity.class);
            viewIntent.putExtra(Constants.KEY_RES_ID, selectedImageResId);
            startActivity(viewIntent);
        }
    }

    private void initViews() {

    }

    private void setListeners() {


    }

}
