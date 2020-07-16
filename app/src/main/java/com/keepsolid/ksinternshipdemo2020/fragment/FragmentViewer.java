package com.keepsolid.ksinternshipdemo2020.fragment;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.keepsolid.ksinternshipdemo2020.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentViewer#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentViewer extends Fragment {

    private AppCompatImageView imageView;

    public FragmentViewer() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_viewer, container, false);
        imageView = v.findViewById(R.id.image_view);

        return v;
    }

    public void displayResource(int resId) {
        imageView.setImageResource(resId);
    }

}