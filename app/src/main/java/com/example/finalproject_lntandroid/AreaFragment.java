package com.example.finalproject_lntandroid;

import android.icu.text.MessagePattern;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.finalproject_lntandroid.databinding.FragmentAreaBinding;
import com.example.finalproject_lntandroid.databinding.FragmentCounterBinding;
import com.google.android.gms.common.util.NumberUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AreaFragment extends Fragment implements RadioGroup.OnCheckedChangeListener, TextWatcher {
    public FragmentAreaBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAreaBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.rgType.setOnCheckedChangeListener(this);
        binding.input1.addTextChangedListener(this);
        binding.input2.addTextChangedListener(this);

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

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.radio1:
                binding.label1.setText("s =");
                binding.label2.setVisibility(View.GONE);
                binding.input2.setVisibility(View.GONE);
                break;
            case R.id.radio2:
                binding.label1.setText("b =");
                binding.label2.setText("h =");
                binding.label2.setVisibility(View.VISIBLE);
                binding.input2.setVisibility(View.VISIBLE);
                break;
            case R.id.radio3:
                binding.label1.setText("r =");
                binding.label2.setVisibility(View.GONE);
                binding.input2.setVisibility(View.GONE);
                break;
        }

        calculate();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        calculate();
    }

    public void calculate(){
        Number hasil = 0;

        if(!binding.input1.getText().toString().matches("^\\-?\\d+(?:\\.\\d+)?$")
           || !binding.input2.getText().toString().matches("^\\-?\\d+(?:\\.\\d+)?$") ) return;

        switch (binding.rgType.getCheckedRadioButtonId()){
            case R.id.radio1:
                hasil = Double.parseDouble(binding.input1.getText().toString()) * Double.parseDouble(binding.input1.getText().toString());
                break;
            case R.id.radio2:
                hasil = Double.parseDouble(binding.input1.getText().toString()) * Double.parseDouble(binding.input2.getText().toString()) / 2;
                break;
            case R.id.radio3:
                hasil = Math.PI * Double.parseDouble(binding.input1.getText().toString()) * Double.parseDouble(binding.input1.getText().toString());
                break;
        }

        binding.txtHasil.setText(hasil.toString());
    }
}