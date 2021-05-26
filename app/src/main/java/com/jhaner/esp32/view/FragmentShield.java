package com.jhaner.esp32.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.Data;
import androidx.work.WorkInfo;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jhaner.esp32.R;
import com.jhaner.esp32.databinding.FragmentShieldBinding;
import com.jhaner.esp32.helper.AdapterShield;
import com.jhaner.esp32.presenter.PresenterShield;

public class FragmentShield extends Fragment {

    private PresenterShield presenterShield;
    private FragmentShieldBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentShieldBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        presenterShield = new PresenterShield(view);
        presenterShield.getOutputWorkInfo().observe(getViewLifecycleOwner(), listOfWorkInfos ->
        {
            if (listOfWorkInfos == null || listOfWorkInfos.isEmpty()) { return; }
            WorkInfo workInfo = listOfWorkInfos.get(0);
            boolean finished = workInfo.getState().isFinished();
            if (finished)
            {
            Data outputData = workInfo.getOutputData();
            presenterShield.fillRecycler(outputData.getString("HTML"));
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