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

import com.jhaner.esp32.databinding.FragmentModuleBinding;
import com.jhaner.esp32.helper.WorkerModule;
import com.jhaner.esp32.presenter.PresenterModule;

public class FragmentModule extends Fragment {

    private FragmentModuleBinding binding;
    private String shield_id;
    private Data outputData;
    private PresenterModule presenterModule;
    private WorkManager workManager;
    private LiveData<WorkInfo> workInfoLiveData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            this.shield_id = getArguments().getString("SHIELD_ID");
            this.outputData = new Data.Builder()
                    .putString("SHIELD_ID", shield_id)
                    .build();
        }
        if (this.outputData != null)
        {
            WorkRequest workRequest  = new OneTimeWorkRequest.Builder(WorkerModule.class)
                    .setInputData(outputData)
                    .addTag("FRAGMENTMODULE")
                    .build();
            workManager = WorkManager.getInstance(getContext());
            workManager.enqueue(workRequest);
            workInfoLiveData = workManager.getWorkInfoByIdLiveData(workRequest.getId());
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentModuleBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        presenterModule = new PresenterModule(view);
        this.getOutputWorkInfo().observe(getViewLifecycleOwner(), workInfo ->
        {
            boolean finished = workInfo.getState().isFinished();
            if ( workInfo!=null && finished)
            {
                Data outputData = workInfo.getOutputData();
                presenterModule.updateRecycler(outputData.getString("HTML"));
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