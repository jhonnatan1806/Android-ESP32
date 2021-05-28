package com.jhaner.esp32.presenter;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jhaner.esp32.R;
import com.jhaner.esp32.model.ModelShield;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PresenterShield {

    private View view;
    private ModelShield modelShield;
    private ArrayList<ModelShield> dataset = new ArrayList<>();
    private RecyclerView recyclerView;

    public PresenterShield(View view)
    {
        this.view = view;
        this.initRecycler();
    }

    private void initRecycler()
    {
        recyclerView = view.findViewById(R.id.f_s_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(new AdapterShield(dataset));
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
                    this.modelShield = new ModelShield();
                    this.modelShield.setShield_id(dataJSON.getString("shield_id"));
                    this.modelShield.setName(dataJSON.getString("name"));
                    this.modelShield.setModel(dataJSON.getString("model"));
                    this.modelShield.setMac(dataJSON.getString("mac"));
                    this.dataset.add(modelShield);
                }
                recyclerView.getAdapter().notifyDataSetChanged();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


}
