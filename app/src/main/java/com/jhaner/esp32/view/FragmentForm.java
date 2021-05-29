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

import com.jhaner.esp32.databinding.FragmentFormBinding;
import com.jhaner.esp32.helper.WorkerOperation;
import com.jhaner.esp32.presenter.PresenterForm;


public class FragmentForm extends Fragment {

    private FragmentFormBinding binding;
    private Data outputData;
    private PresenterForm presenterForm;
    private WorkManager workManager;
    private LiveData<WorkInfo> workInfoLiveData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            this.outputData = new Data.Builder()
                    .putString("SHIELD_ID", getArguments().getString("SHIELD_ID"))
                    .putString("MODULE_ID", getArguments().getString("MODULE_ID"))
                    .build();
        }
        if (this.outputData != null)
        {
            WorkRequest workRequest  = new OneTimeWorkRequest.Builder(WorkerOperation.class)
                    .setInputData(outputData)
                    .addTag("FRAGMENTFORM_OPERATION")
                    .build();
            workManager = WorkManager.getInstance(getContext());
            workManager.enqueue(workRequest);
            workInfoLiveData = workManager.getWorkInfoByIdLiveData(workRequest.getId());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentFormBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        presenterForm = new PresenterForm(view);
        this.getOutputWorkInfo().observe(getViewLifecycleOwner(), workInfo ->
        {
            boolean finished = workInfo.getState().isFinished();
            if ( workInfo!=null && finished)
            {
                Data outputData = workInfo.getOutputData();
                presenterForm.updateCardView(outputData.getString("HTML"));
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