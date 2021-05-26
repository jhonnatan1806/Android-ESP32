package com.jhaner.esp32.presenter;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import com.jhaner.esp32.R;
import com.jhaner.esp32.model.AdapterShield;
import com.jhaner.esp32.model.ModelShield;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PresenterShield {

    private View view;
    private ModelShield modelShield;
    private WorkManager mWorkManager;
    private LiveData<List<WorkInfo>> mSavedWorkInfo;
    private ArrayList<ModelShield> dataset = new ArrayList<>();
    private RecyclerView recyclerView;

    public PresenterShield(View view) {
        this.view = view;
        this.initRecycler();
    }

    private void initRecycler()
    {
        recyclerView = view.findViewById(R.id.f_s_shield);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(new AdapterShield(dataset));
    }

    public void fillRecycler(String data)
    {
        dataset.clear();
        int start = data.indexOf("[");
        int end = data.indexOf("]")+1;
        try {
            JSONArray list = new JSONArray(data.substring(start,end));
            if(list.length()>0)
            {
                for(int i = 0 ; i< list.length(); i++)
                {
                    JSONObject dataJSON = new JSONObject(list.get(i).toString());
                    this.modelShield = new ModelShield();
                    this.modelShield.setId(dataJSON.getString("shield_id"));
                    this.modelShield.setName(dataJSON.getString("name"));
                    this.modelShield.setModel(dataJSON.getString("model"));
                    this.modelShield.setMac(dataJSON.getString("mac"));
                    this.dataset.add(modelShield);
                }
                recyclerView.setAdapter(new AdapterShield(dataset));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public LiveData<List<WorkInfo>> getOutputWorkInfo()
    {
        return mSavedWorkInfo;
    }

}
