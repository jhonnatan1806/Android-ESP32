package com.jhaner.esp32.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import com.jhaner.esp32.databinding.FragmentModuleBinding;
import com.jhaner.esp32.presenter.PresenterModule;

public class FragmentModule extends Fragment {

    private FragmentModuleBinding binding;
    private PresenterModule presenterModule;
    private WorkManager workManager;
    private LiveData<WorkInfo> workInfoLiveData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
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