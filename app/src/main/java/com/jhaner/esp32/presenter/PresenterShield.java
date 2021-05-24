package com.jhaner.esp32.presenter;

import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jhaner.esp32.R;
import com.jhaner.esp32.helper.AdapterShield;
import com.jhaner.esp32.model.ModelShield;

import java.util.ArrayList;

import static com.jhaner.esp32.helper.Constant.METHOD_SHOWSHIELDS;
import static com.jhaner.esp32.helper.Constant.SERVER_URL;

public class PresenterShield {

    private View viewShield;
    private ModelShield modelShield;
    private ArrayList<ModelShield> myDataset = new ArrayList<>();
    private RecyclerView recyclerView;

    public PresenterShield(View view) {
        viewShield = view;
        modelShield = new ModelShield();
        this.initRecycler();
        //String data = this.sendGetRequestParam(SERVER_URL+METHOD_SHOWSHIELDS);

    }

    private void initRecycler()
    {
        ModelShield tests = new ModelShield();
        tests.setId("12300000");
        tests.setName("aaaaaaaa");
        tests.setModel("model test");
        tests.setMac("aa:bb:cc:dd:ee");
        myDataset.add(tests);
        ModelShield tests2 = new ModelShield();
        tests2.setId("12300001");
        tests2.setName("aaaaaaab");
        tests2.setModel("model test2");
        tests2.setMac("aa:bb:cc:dd:rr");
        myDataset.add(tests2);
        recyclerView = viewShield.findViewById(R.id.f_s_shield);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(viewShield.getContext()));
        AdapterShield adapterShield = new AdapterShield(myDataset);
        recyclerView.setAdapter(adapterShield);
    }

}
