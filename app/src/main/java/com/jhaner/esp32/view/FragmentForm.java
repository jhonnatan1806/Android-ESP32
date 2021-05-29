package com.jhaner.esp32.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.navigation.Navigation;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import com.google.android.material.snackbar.Snackbar;
import com.jhaner.esp32.R;
import com.jhaner.esp32.databinding.FragmentFormBinding;
import com.jhaner.esp32.helper.WorkerOperation;
import com.jhaner.esp32.helper.WorkerStatus;
import com.jhaner.esp32.helper.WorkerTask;
import com.jhaner.esp32.model.ModelOperation;
import com.jhaner.esp32.presenter.PresenterForm;


public class FragmentForm extends Fragment {

    private FragmentFormBinding binding;
    private Data outputData;
    private PresenterForm presenterForm;
    private WorkManager workManager;
    private LiveData<WorkInfo> workInfoLiveData;
    private Button btn_send;
    private Button btn_on;
    private Button btn_off;
    private Button btn_clear;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.outputData = new Data.Builder()
                .putString("SHIELD_ID", getArguments().getString("SHIELD_ID"))
                .putString("MODULE_ID", getArguments().getString("MODULE_ID"))
                .build();
        WorkRequest workRequest  = new OneTimeWorkRequest.Builder(WorkerOperation.class)
                .setInputData(outputData)
                .addTag("FRAGMENTFORM_OPERATION")
                .build();
        workManager = WorkManager.getInstance(getContext());
        workManager.enqueue(workRequest);
        workInfoLiveData = workManager.getWorkInfoByIdLiveData(workRequest.getId());
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
        btn_on = view.findViewById(R.id.cs_btn_on);
        btn_off = view.findViewById(R.id.cs_btn_off);
        btn_send = view.findViewById(R.id.ct_btn_send);
        presenterForm = new PresenterForm(view);
        this.getOutputWorkInfo().observe(getViewLifecycleOwner(), workInfo -> {
            boolean finished = workInfo.getState().isFinished();
            if ( workInfo!=null && finished)
            {
                Data outputData = workInfo.getOutputData();
                presenterForm.updateCardView(outputData.getString("HTML"));
            }
        });
        btn_on.setOnClickListener(v -> {
            Data data = new Data.Builder()
                    .putString("SHIELD_ID", outputData.getString("SHIELD_ID"))
                    .putString("MODULE_ID", outputData.getString("MODULE_ID"))
                    .putString("STATUS", "1")
                    .build();
            WorkRequest workStatus  = new OneTimeWorkRequest.Builder(WorkerStatus.class)
                    .setInputData(data)
                    .addTag("FRAGMENTFORM_STATUS")
                    .build();
            workManager.enqueue(workStatus);
            Bundle bundle = new Bundle();
            bundle.putString("SHIELD_ID", data.getString("SHIELD_ID"));
            Navigation.findNavController(view).navigate(R.id.action_FragmentForm_to_FragmentModule, bundle);
        });
        btn_off.setOnClickListener(v -> {
            Data data = new Data.Builder()
                    .putString("SHIELD_ID", outputData.getString("SHIELD_ID"))
                    .putString("MODULE_ID", outputData.getString("MODULE_ID"))
                    .putString("STATUS", "0")
                    .build();
            WorkRequest workStatus  = new OneTimeWorkRequest.Builder(WorkerStatus.class)
                    .setInputData(data)
                    .addTag("FRAGMENTFORM_STATUS")
                    .build();
            workManager.enqueue(workStatus);
            Bundle bundle = new Bundle();
            bundle.putString("SHIELD_ID", data.getString("SHIELD_ID"));
            Navigation.findNavController(view).navigate(R.id.action_FragmentForm_to_FragmentModule, bundle);
        });
        btn_send.setOnClickListener(v -> {
            ModelOperation modelOperation = presenterForm.makeOperation(presenterForm.dosage.getText().toString(),
                    presenterForm.quantity.getText().toString(),
                    presenterForm.days.getText().toString(),
                    presenterForm.maxWorking.getText().toString());
            if(modelOperation!=null)
            {
                Data data = new Data.Builder()
                        .putString("SHIELD_ID", outputData.getString("SHIELD_ID"))
                        .putString("MODULE_ID", outputData.getString("MODULE_ID"))
                        .putString("STATUS", modelOperation.getStatus())
                        .putString("CREATION_DATE", modelOperation.getCreation_date())
                        .putString("CYCLES", modelOperation.getCycles())
                        .putString("CYCLES_COMPLETED", modelOperation.getCycles_completed())
                        .putString("TIME_ON", modelOperation.getTime_on())
                        .putString("TIME_OFF", modelOperation.getTime_off())
                        .build();
                WorkRequest workStatus  = new OneTimeWorkRequest.Builder(WorkerTask.class)
                        .setInputData(data)
                        .addTag("FRAGMENTFORM_TASK")
                        .build();
                workManager.enqueue(workStatus);
                Bundle bundle = new Bundle();
                bundle.putString("SHIELD_ID", data.getString("SHIELD_ID"));
                Navigation.findNavController(view).navigate(R.id.action_FragmentForm_to_FragmentModule, bundle);
            }else{
                Snackbar.make(view, "ERROR: INVALID DATA", Snackbar.LENGTH_LONG)
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

    public LiveData<WorkInfo> getOutputWorkInfo()
    {
        return workInfoLiveData;
    }
}