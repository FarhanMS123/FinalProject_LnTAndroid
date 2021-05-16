package com.example.finalproject_lntandroid;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.finalproject_lntandroid.databinding.FragmentCounterBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CounterFragment extends Fragment implements View.OnClickListener {
    public FragmentCounterBinding binding;
    public SharedPreferences sharedPref;

    private int number = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);

        binding = FragmentCounterBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.btnMin.setOnClickListener(this);
        binding.btnPlus.setOnClickListener(this);

        return view;
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        home parActivity = (home) getActivity();
        BottomNavigationView navBottom = parActivity.binding.navBottom;

        if(navBottom.getSelectedItemId() != R.id.page_1){
            parActivity.ignoreSetNavBottom = true;
            navBottom.setSelectedItemId(R.id.page_1);
        }

        number = sharedPref.getInt("counter", 0);
        binding.txtNum.setText(String.valueOf(number));
    }

    public void incNumber(View view){
        ++number;

        saveCounter(number);
        binding.txtNum.setText(String.valueOf(number));
    }

    public void decNumber(View view){
        --number;

        saveCounter(number);
        binding.txtNum.setText(String.valueOf(number));
    }

    public void saveCounter(int num){
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("counter", num);
        editor.apply();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnMin:
                decNumber(v);
                break;
            case R.id.btnPlus:
                incNumber(v);
                break;
        }
    }
}