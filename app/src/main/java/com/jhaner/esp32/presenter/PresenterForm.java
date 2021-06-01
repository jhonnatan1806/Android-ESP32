package com.jhaner.esp32.presenter;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jhaner.esp32.R;
import com.jhaner.esp32.model.ModelOperation;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import static com.jhaner.esp32.helper.Constants.DATE_FORMAT;
import static com.jhaner.esp32.helper.Constants.DATE_TIMEZONE;
import static com.jhaner.esp32.helper.Constants.DEFAULT_CYCLESCOMPLETED;
import static com.jhaner.esp32.helper.Constants.DEFAULT_MODULEID;
import static com.jhaner.esp32.helper.Constants.DEFAULT_SHIELDID;
import static com.jhaner.esp32.helper.Constants.DEFAULT_STATUS;
import static com.jhaner.esp32.helper.Constants.KEY_CREATIONDATE;
import static com.jhaner.esp32.helper.Constants.KEY_CYCLES;
import static com.jhaner.esp32.helper.Constants.KEY_CYCLESCOMPLETED;
import static com.jhaner.esp32.helper.Constants.KEY_MODULEID;
import static com.jhaner.esp32.helper.Constants.KEY_SHIELDID;
import static com.jhaner.esp32.helper.Constants.KEY_STATUS;
import static com.jhaner.esp32.helper.Constants.KEY_TIMEOFF;
import static com.jhaner.esp32.helper.Constants.KEY_TIMEON;

public class PresenterForm {

    private final View view;
    private TextView shield_id;
    private TextView module_id;
    private TextView status;
    private TextView creation_date;
    private TextView cycles;
    private TextView cycles_completed;
    private TextView time_on;
    private TextView time_off;
    public EditText dosage;
    public EditText quantity;
    public EditText days;
    public EditText maxWorking;

    public PresenterForm(View view) {
        this.view = view;
        this.initTask();
        this.initCardview();

    }

    private void initTask()
    {
        dosage = view.findViewById(R.id.ct_dosage);
        quantity = view.findViewById(R.id.ct_quantity);
        days = view.findViewById(R.id.ct_days);
        maxWorking = view.findViewById(R.id.ct_maxworking);
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

    @SuppressLint("SetTextI18n")
    public void updateCardView(String data)
    {
        ModelOperation modelOperation;
        int start = data.indexOf("{");
        int end = data.indexOf("}")+1;
        try {
            JSONObject dataJSON = new JSONObject(data.substring(start,end));
            modelOperation = new ModelOperation();
            modelOperation.setShield_id(dataJSON.getString(KEY_SHIELDID));
            modelOperation.setModule_id(dataJSON.getString(KEY_MODULEID));
            modelOperation.setStatus(dataJSON.getString(KEY_STATUS));
            modelOperation.setCreation_date(dataJSON.getString(KEY_CREATIONDATE));
            modelOperation.setCycles(dataJSON.getString(KEY_CYCLES));
            modelOperation.setCycles_completed(dataJSON.getString(KEY_CYCLESCOMPLETED));
            modelOperation.setTime_on(dataJSON.getString(KEY_TIMEON));
            modelOperation.setTime_off(dataJSON.getString(KEY_TIMEOFF));

            shield_id.setText(modelOperation.getShield_id());
            module_id.setText(modelOperation.getModule_id());
            if(modelOperation.getStatus().equals("0")) { status.setText("OFF"); }
            else if(modelOperation.getStatus().equals("1")) { status.setText("ON"); }
            if (!modelOperation.getCycles().equals("0"))
            {
                creation_date.setText(modelOperation.getCreation_date());
                cycles.setText(modelOperation.getCycles());
                cycles_completed.setText(modelOperation.getCycles_completed());
                if( isNumeric(modelOperation.getTime_on()) && isNumeric(modelOperation.getTime_off()))
                {
                    int timeon = Integer.parseInt(modelOperation.getTime_on())/60;
                    int timeoff = Integer.parseInt(modelOperation.getTime_off())/60;
                    time_on.setText(timeon +" min.");
                    time_off.setText(timeoff +" min.");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private boolean validation(String data)
    {
        return (this.isNumeric(data) && Integer.parseInt(data) > 0);
    }

    private  boolean isNumeric(String str)
    {
        try { Double.parseDouble(str); return true;}
        catch(NumberFormatException e){return false;}
    }

    private Double intervale(Double workingtime, Double maxworkingtime)
    {
        int count = 1;
        while((workingtime/count)>maxworkingtime){ count ++; }
        return (double) count;
    }

    public ModelOperation makeOperation(String dataDosage, String dataQuantity, String dataDays, String dataMaxWorkingTime)
    {
        ModelOperation mOperation;
        if(validation(dataDosage) && validation(dataQuantity) && validation(dataDays) && validation(dataMaxWorkingTime))
        {
            double dosage = Double.parseDouble(dataDosage); //dosage in minutes
            double quantity = Double.parseDouble(dataQuantity); // quantity in ml
            double days = Double.parseDouble(dataDays);// x days
            double  maxWorkingTime = Double.parseDouble(dataMaxWorkingTime); //max time
            double dailygoal = quantity/days; // x ml per day
            double workingtime =  (dailygoal / dosage); // working time per day in min
            double varintervale;
            int timeon, timeoff, cycles;
            if (workingtime<1440) {
                varintervale =  this.intervale(workingtime, maxWorkingTime);
                if(varintervale>1)
                {
                    timeon = (int)Math.round(workingtime/varintervale)*60;
                    timeoff = (int)Math.round(((24*60) - workingtime)/varintervale)*60;
                    cycles = (int)Math.round(days*varintervale);
                }
                else
                {
                    timeon = (int)Math.round(workingtime)*60;
                    timeoff = (int)Math.round((24*60) - workingtime)*60;
                    cycles = (int)Math.round(days);
                }
                @SuppressLint("SimpleDateFormat")
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
                simpleDateFormat.setTimeZone(TimeZone.getTimeZone(DATE_TIMEZONE));
                String creation_date = simpleDateFormat.format(new Date());

                mOperation = new ModelOperation();
                mOperation.setShield_id(DEFAULT_SHIELDID);
                mOperation.setModule_id(DEFAULT_MODULEID);
                mOperation.setStatus(DEFAULT_STATUS);
                mOperation.setCreation_date(creation_date);
                mOperation.setCycles(String.valueOf(cycles));
                mOperation.setCycles_completed(DEFAULT_CYCLESCOMPLETED);
                mOperation.setTime_on(String.valueOf(timeon));
                mOperation.setTime_off(String.valueOf(timeoff));
                return mOperation;
            }

        }
        return null;
    }


}
