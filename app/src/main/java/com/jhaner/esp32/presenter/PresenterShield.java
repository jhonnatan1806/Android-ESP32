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
import java.util.Objects;

import static com.jhaner.esp32.helper.Constants.KEY_MAC;
import static com.jhaner.esp32.helper.Constants.KEY_MODEL;
import static com.jhaner.esp32.helper.Constants.KEY_NAME;
import static com.jhaner.esp32.helper.Constants.KEY_SHIELDID;

public class PresenterShield {

    private final View view;
    private final ArrayList<ModelShield> dataset = new ArrayList<>();
    private RecyclerView recyclerView;

    public PresenterShield(View view)
    {
        this.view = view;
        this.initRecycler();
    }

    private void initRecycler()
    {
        recyclerView = view.findViewById(R.id.recyclerview_shields);
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
                    ModelShield modelShield = new ModelShield();
                    modelShield.setShield_id(dataJSON.getString(KEY_SHIELDID));
                    modelShield.setName(dataJSON.getString(KEY_NAME));
                    modelShield.setModel(dataJSON.getString(KEY_MODEL));
                    modelShield.setMac(dataJSON.getString(KEY_MAC));
                    this.dataset.add(modelShield);
                }
                Objects.requireNonNull(recyclerView.getAdapter()).notifyDataSetChanged();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


}
