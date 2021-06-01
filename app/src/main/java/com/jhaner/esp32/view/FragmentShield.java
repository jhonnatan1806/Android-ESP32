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

import java.util.Objects;

import static com.jhaner.esp32.helper.Constants.HTML_KEY;

public class FragmentShield extends Fragment {

    private FragmentShieldBinding binding;
    private PresenterShield presenterShield;
    private LiveData<WorkInfo> workInfoLiveData;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        WorkRequest workRequest  = new OneTimeWorkRequest.Builder(WorkerShield.class)
                .addTag("FRAGMENTSHIELD")
                .build();
        WorkManager workManager = WorkManager.getInstance(requireContext());
        workManager.enqueue(workRequest);
        workInfoLiveData = workManager.getWorkInfoByIdLiveData(workRequest.getId());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
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
            if (finished)
            {
                Data outputData = workInfo.getOutputData();
                presenterShield.updateRecycler(Objects.requireNonNull(outputData.getString(HTML_KEY)));
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