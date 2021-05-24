package com.jhaner.esp32.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jhaner.esp32.R;
import com.jhaner.esp32.databinding.FragmentModeBinding;

public class FragmentMode extends Fragment {

    private FragmentModeBinding binding;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState )
    {
        binding = FragmentModeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        binding.modeWifi.setOnClickListener((View v) -> {
            NavHostFragment.findNavController(FragmentMode.this)
                    .navigate(R.id.action_FragmentMode_to_FragmentShield);
        });
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        binding = null;
    }
}