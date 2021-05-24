package com.jhaner.esp32.view;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;
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
        binding.modeWifi.setOnClickListener((View v) ->
        {
            ConnectivityManager connectivityManager = (ConnectivityManager)
                    getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected())
            {
                NavHostFragment.findNavController(FragmentMode.this)
                        .navigate(R.id.action_FragmentMode_to_FragmentShield);
            }
            else
            {
                Snackbar.make(view, "ERROR NO NETWORK CONNECTION", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }

        });

    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        binding = null;
    }
}