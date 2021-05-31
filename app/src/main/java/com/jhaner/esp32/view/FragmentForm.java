package com.jhaner.esp32.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import com.google.android.material.snackbar.Snackbar;
import com.jhaner.esp32.R;
import com.jhaner.esp32.databinding.FragmentFormBinding;
import com.jhaner.esp32.helper.WorkerOperation;
import com.jhaner.esp32.helper.WorkerTask;
import com.jhaner.esp32.model.ModelOperation;
import com.jhaner.esp32.presenter.PresenterForm;

import static com.jhaner.esp32.helper.Constants.KEY_CREATIONDATE;
import static com.jhaner.esp32.helper.Constants.KEY_CYCLES;
import static com.jhaner.esp32.helper.Constants.KEY_CYCLESCOMPLETED;
import static com.jhaner.esp32.helper.Constants.KEY_MODULEID;
import static com.jhaner.esp32.helper.Constants.KEY_SHIELDID;
import static com.jhaner.esp32.helper.Constants.KEY_STATUS;
import static com.jhaner.esp32.helper.Constants.KEY_TIMEOFF;
import static com.jhaner.esp32.helper.Constants.KEY_TIMEON;


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
                .putString(KEY_SHIELDID, getArguments().getString(KEY_SHIELDID))
                .putString(KEY_MODULEID, getArguments().getString(KEY_MODULEID))
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
        btn_clear = view.findViewById(R.id.ct_btn_clear);
        presenterForm = new PresenterForm(view);
        this.getOutputWorkInfo().observe(getViewLifecycleOwner(), workInfo -> {
            boolean finished = workInfo.getState().isFinished();
            if ( workInfo!=null && finished)
            {
                Data outputData = workInfo.getOutputData();
                presenterForm.updateCardView(outputData.getString("HTML"));
            }
        });
        //BUTTON MODULE ON + CLEAR DATA
        btn_on.setOnClickListener(v -> {
            Data data = new Data.Builder()
                    .putString(KEY_SHIELDID, outputData.getString(KEY_SHIELDID))
                    .putString(KEY_MODULEID, outputData.getString(KEY_MODULEID))
                    .putString(KEY_STATUS, "1")
                    .putString(KEY_CREATIONDATE, "2021-01-01+00:00:00")
                    .putString(KEY_CYCLES, "0")
                    .putString(KEY_CYCLESCOMPLETED, "0")
                    .putString(KEY_TIMEON, "0")
                    .putString(KEY_TIMEOFF, "0")
                    .build();
            WorkRequest workStatus  = new OneTimeWorkRequest.Builder(WorkerTask.class)
                    .setInputData(data)
                    .addTag("FRAGMENTFORM_STATUS")
                    .build();
            workManager.enqueue(workStatus);
            requireActivity().getOnBackPressedDispatcher().onBackPressed();
        });
        //BUTTON MODULE OFF + CLEAR TASK
        btn_off.setOnClickListener(v -> {
            Data data = new Data.Builder()
                    .putString(KEY_SHIELDID, outputData.getString(KEY_SHIELDID))
                    .putString(KEY_MODULEID, outputData.getString(KEY_MODULEID))
                    .putString(KEY_STATUS, "0")
                    .putString(KEY_CREATIONDATE, "2021-01-01+00:00:00")
                    .putString(KEY_CYCLES, "0")
                    .putString(KEY_CYCLESCOMPLETED, "0")
                    .putString(KEY_TIMEON, "0")
                    .putString(KEY_TIMEOFF, "0")
                    .build();
            WorkRequest workStatus  = new OneTimeWorkRequest.Builder(WorkerTask.class)
                    .setInputData(data)
                    .addTag("FRAGMENTFORM_STATUS")
                    .build();
            workManager.enqueue(workStatus);
            requireActivity().getOnBackPressedDispatcher().onBackPressed();
        });
        //BUTTON SEND DATA
        btn_send.setOnClickListener(v -> {
            ModelOperation modelOperation = presenterForm.makeOperation(presenterForm.dosage.getText().toString(),
                    presenterForm.quantity.getText().toString(),
                    presenterForm.days.getText().toString(),
                    presenterForm.maxWorking.getText().toString());
            if(modelOperation!=null)
            {
                Data data = new Data.Builder()
                        .putString(KEY_SHIELDID, outputData.getString(KEY_SHIELDID))
                        .putString(KEY_MODULEID, outputData.getString(KEY_MODULEID))
                        .putString(KEY_STATUS, modelOperation.getStatus())
                        .putString(KEY_CREATIONDATE, modelOperation.getCreation_date())
                        .putString(KEY_CYCLES, modelOperation.getCycles())
                        .putString(KEY_CYCLESCOMPLETED, modelOperation.getCycles_completed())
                        .putString(KEY_TIMEON, modelOperation.getTime_on())
                        .putString(KEY_TIMEOFF, modelOperation.getTime_off())
                        .build();
                WorkRequest workStatus  = new OneTimeWorkRequest.Builder(WorkerTask.class)
                        .setInputData(data)
                        .addTag("FRAGMENTFORM_TASK")
                        .build();
                workManager.enqueue(workStatus);
                requireActivity().getOnBackPressedDispatcher().onBackPressed();
            }else{
                Snackbar.make(view, "ERROR: INVALID DATA", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        btn_clear.setOnClickListener(v -> {
            presenterForm.dosage.setText("");
            presenterForm.quantity.setText("");
            presenterForm.days.setText("");
            presenterForm.maxWorking.setText("");
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