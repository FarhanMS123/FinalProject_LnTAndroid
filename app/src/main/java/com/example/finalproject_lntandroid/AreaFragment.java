package com.example.finalproject_lntandroid;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.finalproject_lntandroid.databinding.FragmentAreaBinding;
import com.example.finalproject_lntandroid.databinding.FragmentCounterBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AreaFragment extends Fragment {
    public FragmentAreaBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAreaBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        return view;
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        home parActivity = (home) getActivity();
        BottomNavigationView navBottom = parActivity.binding.navBottom;

        if(navBottom.getSelectedItemId() != R.id.page_2){
            parActivity.ignoreSetNavBottom = true;
            navBottom.setSelectedItemId(R.id.page_2);
        }
    }
}