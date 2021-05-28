package com.jhaner.esp32.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import com.jhaner.esp32.databinding.FragmentShieldBinding;
import com.jhaner.esp32.helper.WorkerShield;
import com.jhaner.esp32.presenter.PresenterShield;

public class FragmentShield extends Fragment {

    private FragmentShieldBinding binding;
    private PresenterShield presenterShield;
    private WorkManager workManager;
    private LiveData<WorkInfo> workInfoLiveData;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        WorkRequest workRequest  = new OneTimeWorkRequest.Builder(WorkerShield.class)
                .addTag("FRAGMENTSHIELD")
                .build();
        workManager = WorkManager.getInstance(getContext());
        workManager.enqueue(workRequest);
        workInfoLiveData = workManager.getWorkInfoByIdLiveData(workRequest.getId());
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
        this.getOutputWorkInfo().observe(getViewLifecycleOwner(), workInfo ->
        {
            boolean finished = workInfo.getState().isFinished();
            if ( workInfo!=null && finished)
            {
                Data outputData = workInfo.getOutputData();
                presenterShield.updateRecycler(outputData.getString("HTML"));
            }
        });
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        binding = null;
    }

    public LiveData<WorkInfo> getOutputWorkInfo()
    {
        return workInfoLiveData;
    }

}