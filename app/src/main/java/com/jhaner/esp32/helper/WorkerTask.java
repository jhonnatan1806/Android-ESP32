package com.jhaner.esp32.helper;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.jhaner.esp32.helper.Constants.ARG_CREATIONDATE;
import static com.jhaner.esp32.helper.Constants.ARG_CYCLES;
import static com.jhaner.esp32.helper.Constants.ARG_CYCLESCOMPLETED;
import static com.jhaner.esp32.helper.Constants.ARG_MODULEID;
import static com.jhaner.esp32.helper.Constants.ARG_SHIELDID;
import static com.jhaner.esp32.helper.Constants.ARG_STATUS;
import static com.jhaner.esp32.helper.Constants.ARG_TIMEOFF;
import static com.jhaner.esp32.helper.Constants.ARG_TIMEON;
import static com.jhaner.esp32.helper.Constants.HTML_CACHECONTROL;
import static com.jhaner.esp32.helper.Constants.HTML_METHOD;
import static com.jhaner.esp32.helper.Constants.HTML_NOCACHE;
import static com.jhaner.esp32.helper.Constants.KEY_CREATIONDATE;
import static com.jhaner.esp32.helper.Constants.KEY_CYCLES;
import static com.jhaner.esp32.helper.Constants.KEY_CYCLESCOMPLETED;
import static com.jhaner.esp32.helper.Constants.KEY_MODULEID;
import static com.jhaner.esp32.helper.Constants.KEY_SHIELDID;
import static com.jhaner.esp32.helper.Constants.KEY_STATUS;
import static com.jhaner.esp32.helper.Constants.KEY_TIMEOFF;
import static com.jhaner.esp32.helper.Constants.KEY_TIMEON;
import static com.jhaner.esp32.helper.Constants.METHOD_MODIFYDATA;
import static com.jhaner.esp32.helper.Constants.SERVER_URL;

public class WorkerTask extends Worker {

    public WorkerTask(@NonNull Context context, @NonNull WorkerParameters params)
    {
        super(context, params);
    }

    @Override @NonNull
    public Result doWork()
    {
        String shield_id = getInputData().getString(KEY_SHIELDID);
        String module_id = getInputData().getString(KEY_MODULEID );
        String status = getInputData().getString(KEY_STATUS);
        String creation_date = getInputData().getString(KEY_CREATIONDATE);
        String cycles = getInputData().getString(KEY_CYCLES);
        String cycles_completed = getInputData().getString(KEY_CYCLESCOMPLETED);
        String time_on = getInputData().getString(KEY_TIMEON);
        String time_off = getInputData().getString(KEY_TIMEOFF);
        String path = SERVER_URL    + METHOD_MODIFYDATA +
                ARG_SHIELDID        + shield_id +
                ARG_MODULEID        + module_id +
                ARG_CREATIONDATE    + creation_date +
                ARG_STATUS          + status +
                ARG_CYCLES          + cycles +
                ARG_CYCLESCOMPLETED + cycles_completed +
                ARG_TIMEON          + time_on +
                ARG_TIMEOFF         + time_off ;

        StringBuilder stringBuilder = new StringBuilder();
        try
        {
            URL url = new URL(path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(HTML_METHOD);
            connection.addRequestProperty(HTML_CACHECONTROL, HTML_NOCACHE);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String string;
            while((string=bufferedReader.readLine())!=null)
            {
                stringBuilder.append(string);
            }
            connection.disconnect();
            return Worker.Result.success();
        } catch (Exception exception)
        {
            return Worker.Result.failure();
        }
    }
}
