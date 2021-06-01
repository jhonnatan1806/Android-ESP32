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
import java.util.Objects;

public class PresenterModule {

    private final View view;
    private final ArrayList<ModelModule> dataset = new ArrayList<>();
    private RecyclerView recyclerView;

    public PresenterModule(View view)
    {
        this.view = view;
        this.initRecycler();
    }

    private void initRecycler()
    {
        recyclerView = view.findViewById(R.id.recyclerview_modules);
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
                    ModelModule modelModule = new ModelModule();
                    modelModule.setModule_id(dataJSON.getString("module_id"));
                    modelModule.setShield_id(dataJSON.getString("shield_id"));
                    modelModule.setName(dataJSON.getString("name"));
                    modelModule.setType(dataJSON.getString("type"));
                    modelModule.setDescription(dataJSON.getString("description"));
                    this.dataset.add(modelModule);
                }
                Objects.requireNonNull(recyclerView.getAdapter()).notifyDataSetChanged();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
