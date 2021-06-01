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

import java.util.Objects;

import static com.jhaner.esp32.helper.Constants.HTML_KEY;
import static com.jhaner.esp32.helper.Constants.KEY_SHIELDID;
import static com.jhaner.esp32.helper.Constants.TAG_FRAGMENTMODULE;

public class FragmentModule extends Fragment {

    private FragmentModuleBinding binding;
    private PresenterModule presenterModule;
    private LiveData<WorkInfo> workInfoLiveData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            Data outputData = new Data.Builder()
                    .putString(KEY_SHIELDID, getArguments().getString(KEY_SHIELDID))
                    .build();
            WorkRequest workRequest  = new OneTimeWorkRequest.Builder(WorkerModule.class)
                    .setInputData(outputData)
                    .addTag(TAG_FRAGMENTMODULE)
                    .build();
            WorkManager workManager = WorkManager.getInstance(requireContext());
            workManager.enqueue(workRequest);
            workInfoLiveData = workManager.getWorkInfoByIdLiveData(workRequest.getId());
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
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
            if (finished)
            {
                Data outputData = workInfo.getOutputData();
                presenterModule.updateRecycler(Objects.requireNonNull(outputData.getString(HTML_KEY)));
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