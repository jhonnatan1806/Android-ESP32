package com.jhaner.esp32.presenter;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jhaner.esp32.R;
import com.jhaner.esp32.model.ModelModule;
import com.jhaner.esp32.model.ModelShield;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PresenterModule {

    private View view;
    private ModelModule modelModule;
    private ArrayList<ModelModule> dataset = new ArrayList<>();
    private RecyclerView recyclerView;

    public PresenterModule(View view)
    {
        this.view = view;
        this.initRecycler();
        this.fillRecycler();
    }

    private void initRecycler()
    {
        recyclerView = view.findViewById(R.id.f_m_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(new AdapterModule(dataset));
    }

    public void fillRecycler()
    {
        dataset.clear();
        modelModule = new ModelModule();
        modelModule.setModule_id("11111");
        modelModule.setStatus("test");
        modelModule.setCycles("10");
        dataset.add(modelModule);
        recyclerView.getAdapter().notifyDataSetChanged();
    }
}
