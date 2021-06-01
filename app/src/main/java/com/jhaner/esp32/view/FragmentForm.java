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

import java.util.Objects;

import static com.jhaner.esp32.helper.Constants.DEFAULT_CREATIONDATE;
import static com.jhaner.esp32.helper.Constants.DEFAULT_CYCLES;
import static com.jhaner.esp32.helper.Constants.DEFAULT_CYCLESCOMPLETED;
import static com.jhaner.esp32.helper.Constants.DEFAULT_TIMEOFF;
import static com.jhaner.esp32.helper.Constants.DEFAULT_TIMEON;
import static com.jhaner.esp32.helper.Constants.MSG_INVALID_ERROR;
import static com.jhaner.esp32.helper.Constants.HTML_KEY;
import static com.jhaner.esp32.helper.Constants.KEY_CREATIONDATE;
import static com.jhaner.esp32.helper.Constants.KEY_CYCLES;
import static com.jhaner.esp32.helper.Constants.KEY_CYCLESCOMPLETED;
import static com.jhaner.esp32.helper.Constants.KEY_MODULEID;
import static com.jhaner.esp32.helper.Constants.KEY_SHIELDID;
import static com.jhaner.esp32.helper.Constants.KEY_STATUS;
import static com.jhaner.esp32.helper.Constants.KEY_TIMEOFF;
import static com.jhaner.esp32.helper.Constants.KEY_TIMEON;
import static com.jhaner.esp32.helper.Constants.MSG_MODULEOFF;
import static com.jhaner.esp32.helper.Constants.MSG_MODULEON;
import static com.jhaner.esp32.helper.Constants.MSG_SENDTASK;
import static com.jhaner.esp32.helper.Constants.TAG_FRAGMENTFORM;

public class FragmentForm extends Fragment {

    private FragmentFormBinding binding;
    private Data outputData;
    private PresenterForm presenterForm;
    private WorkManager workManager;
    private LiveData<WorkInfo> workInfoLiveData;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            this.outputData = new Data.Builder()
                    .putString(KEY_SHIELDID, getArguments().getString(KEY_SHIELDID))
                    .putString(KEY_MODULEID, getArguments().getString(KEY_MODULEID))
                    .build();
            WorkRequest workRequest  = new OneTimeWorkRequest.Builder(WorkerOperation.class)
                    .setInputData(outputData)
                    .addTag(MSG_INVALID_ERROR)
                    .build();
            workManager = WorkManager.getInstance(requireContext());
            workManager.enqueue(workRequest);
            workInfoLiveData = workManager.getWorkInfoByIdLiveData(workRequest.getId());
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentFormBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        Button btn_on = view.findViewById(R.id.cs_btn_on);
        Button btn_off = view.findViewById(R.id.cs_btn_off);
        Button btn_send = view.findViewById(R.id.ct_btn_send);
        Button btn_clear = view.findViewById(R.id.ct_btn_clear);
        presenterForm = new PresenterForm(view);
        this.getOutputWorkInfo().observe(getViewLifecycleOwner(), workInfo -> {
            boolean finished = workInfo.getState().isFinished();
            if (finished)
            {
                Data outputData = workInfo.getOutputData();
                presenterForm.updateCardView(Objects.requireNonNull(outputData.getString(HTML_KEY)));
            }
        });
        //BUTTON MODULE ON + CLEAR DATA
        btn_on.setOnClickListener(v -> {
            Data data = new Data.Builder()
                    .putString(KEY_SHIELDID, outputData.getString(KEY_SHIELDID))
                    .putString(KEY_MODULEID, outputData.getString(KEY_MODULEID))
                    .putString(KEY_STATUS, "1")
                    .putString(KEY_CREATIONDATE, DEFAULT_CREATIONDATE)
                    .putString(KEY_CYCLES, DEFAULT_CYCLES)
                    .putString(KEY_CYCLESCOMPLETED, DEFAULT_CYCLESCOMPLETED)
                    .putString(KEY_TIMEON, DEFAULT_TIMEON)
                    .putString(KEY_TIMEOFF, DEFAULT_TIMEOFF)
                    .build();
            WorkRequest workStatus  = new OneTimeWorkRequest.Builder(WorkerTask.class)
                    .setInputData(data)
                    .addTag(TAG_FRAGMENTFORM)
                    .build();
            workManager.enqueue(workStatus);
            Snackbar.make(view, MSG_MODULEON, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            requireActivity().getOnBackPressedDispatcher().onBackPressed();
        });
        //BUTTON MODULE OFF + CLEAR TASK
        btn_off.setOnClickListener(v -> {
            Data data = new Data.Builder()
                    .putString(KEY_SHIELDID, outputData.getString(KEY_SHIELDID))
                    .putString(KEY_MODULEID, outputData.getString(KEY_MODULEID))
                    .putString(KEY_STATUS, "0")
                    .putString(KEY_CREATIONDATE, DEFAULT_CREATIONDATE)
                    .putString(KEY_CYCLES, DEFAULT_CYCLES)
                    .putString(KEY_CYCLESCOMPLETED, DEFAULT_CYCLESCOMPLETED)
                    .putString(KEY_TIMEON, DEFAULT_TIMEON)
                    .putString(KEY_TIMEOFF, DEFAULT_TIMEOFF)
                    .build();
            WorkRequest workStatus  = new OneTimeWorkRequest.Builder(WorkerTask.class)
                    .setInputData(data)
                    .addTag(TAG_FRAGMENTFORM)
                    .build();
            workManager.enqueue(workStatus);
            Snackbar.make(view, MSG_MODULEOFF, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
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
                        .addTag(TAG_FRAGMENTFORM)
                        .build();
                workManager.enqueue(workStatus);
                Snackbar.make(view, MSG_SENDTASK, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                requireActivity().getOnBackPressedDispatcher().onBackPressed();
            }else{
                Snackbar.make(view, MSG_INVALID_ERROR, Snackbar.LENGTH_LONG)
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