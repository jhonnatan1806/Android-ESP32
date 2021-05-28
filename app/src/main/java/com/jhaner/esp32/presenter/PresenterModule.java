package com.jhaner.esp32.presenter;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jhaner.esp32.R;
import com.jhaner.esp32.model.ModelModule;

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
    }

    private void initRecycler()
    {
        recyclerView = view.findViewById(R.id.f_m_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(new AdapterModule(dataset));
    }

    public void updateRecycler(String data)
    {
        dataset.clear();
        int start = data.indexOf("[");
        int end = data.indexOf("]")+1;
        try {
            JSONArray list = new JSONArray(data.substring(start,end));
            if(list.length()!=0)
            {
                for(int i = 0 ; i< list.length(); i++)
                {
                    JSONObject dataJSON = new JSONObject(list.get(i).toString());
                    this.modelModule= new ModelModule();
                    this.modelModule.setModule_id(dataJSON.getString("module_id"));
                    this.modelModule.setShield_id(dataJSON.getString("shield_id"));
                    this.modelModule.setName(dataJSON.getString("name"));
                    this.modelModule.setDescription(dataJSON.getString("description"));
                    this.dataset.add(modelModule);
                }
                recyclerView.getAdapter().notifyDataSetChanged();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
