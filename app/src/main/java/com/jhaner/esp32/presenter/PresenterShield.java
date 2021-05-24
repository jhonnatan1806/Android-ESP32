package com.jhaner.esp32.presenter;

import android.util.Log;
import android.view.View;

import com.jhaner.esp32.model.ModelShield;

import static com.jhaner.esp32.helper.Constant.METHOD_SHOWSHIELDS;
import static com.jhaner.esp32.helper.Constant.SERVER_URL;

public class PresenterShield {

    private View fragmentShield;
    private ModelShield modelShield;

    public PresenterShield(View view) {
        fragmentShield = view;
        modelShield = new ModelShield();
        //String data = this.sendGetRequestParam(SERVER_URL+METHOD_SHOWSHIELDS);
        String data2 = "prueba";
        Log.i("data","data");
        Log.i("data",data2);
    }

}
