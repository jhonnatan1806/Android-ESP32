package com.jhaner.esp32.presenter;

import android.view.View;
import android.widget.TextView;

import com.jhaner.esp32.R;
import com.jhaner.esp32.model.ModelOperation;

import org.json.JSONException;
import org.json.JSONObject;

public class PresenterForm {

    private View view;
    private ModelOperation modelOperation;
    private TextView shield_id;
    private TextView module_id;
    private TextView status;
    private TextView creation_date;
    private TextView cycles;
    private TextView cycles_completed;
    private TextView time_on;
    private TextView time_off;


    public PresenterForm(View view) {
        this.view = view;
        this.initCardview();
    }

    private void initCardview()
    {
        shield_id = view.findViewById(R.id.co_shield_id);
        module_id = view.findViewById(R.id.co_module_id);
        status = view.findViewById(R.id.co_status);
        creation_date = view.findViewById(R.id.co_creation_date);
        cycles = view.findViewById(R.id.co_cycles);
        cycles_completed = view.findViewById(R.id.co_cycles_completed);
        time_on = view.findViewById(R.id.co_time_on);
        time_off = view.findViewById(R.id.co_time_off);
    }

    public void updateCardView(String data)
    {
        modelOperation = null;
        int start = data.indexOf("{");
        int end = data.indexOf("}")+1;
        try {
            JSONObject dataJSON = new JSONObject(data.substring(start,end));
            modelOperation = new ModelOperation();
            modelOperation.setShield_id(dataJSON.getString("shield_id"));
            modelOperation.setModule_id(dataJSON.getString("module_id"));
            modelOperation.setStatus(dataJSON.getString("status"));
            modelOperation.setCreation_date(dataJSON.getString("creation_date"));
            modelOperation.setCycles(dataJSON.getString("cycles"));
            modelOperation.setCycles_completed(dataJSON.getString("cycles_completed"));
            modelOperation.setTime_on(dataJSON.getString("time_on"));
            modelOperation.setTime_off(dataJSON.getString("time_off"));

            shield_id.setText(modelOperation.getShield_id());
            module_id.setText(modelOperation.getModule_id());
            if(modelOperation.getStatus().equals("0")) { status.setText("OFF"); }
            else if(modelOperation.getStatus().equals("1")) { status.setText("ON"); }
            if (!modelOperation.getCycles().equals("0"))
            {
                creation_date.setText(modelOperation.getCreation_date());
                cycles.setText(modelOperation.getCycles());
                cycles_completed.setText(modelOperation.getCycles_completed());
                time_on.setText(modelOperation.getTime_on()+" sec.");
                time_off.setText(modelOperation.getTime_off()+" sec.");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
