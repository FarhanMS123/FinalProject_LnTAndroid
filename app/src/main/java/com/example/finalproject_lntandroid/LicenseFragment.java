package com.example.finalproject_lntandroid;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.finalproject_lntandroid.databinding.FragmentLicenseBinding;
import com.example.finalproject_lntandroid.databinding.FragmentVolumeBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class LicenseFragment extends Fragment {
    public FragmentLicenseBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLicenseBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.wvLicense.loadUrl("file:///android_asset/license.html");
        binding.wvLicense.setBackgroundColor(Color.TRANSPARENT);

        return view;
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if(getActivity().getClass().getName() != home.class.getName()) return;

        home parActivity = (home) getActivity();
        BottomNavigationView navBottom = parActivity.findViewById(R.id.navBottom);

        if(navBottom.getSelectedItemId() != R.id.page_4){
            parActivity.ignoreSetNavBottom = true;
            navBottom.setSelectedItemId(R.id.page_4);
        }
    }

}