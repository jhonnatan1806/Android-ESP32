package com.jhaner.esp32.helper;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.jhaner.esp32.helper.Constant.ARG_CREATIONDATE;
import static com.jhaner.esp32.helper.Constant.ARG_CYCLES;
import static com.jhaner.esp32.helper.Constant.ARG_CYCLESCOMPLETED;
import static com.jhaner.esp32.helper.Constant.ARG_MODULEID;
import static com.jhaner.esp32.helper.Constant.ARG_SHIELDID;
import static com.jhaner.esp32.helper.Constant.ARG_STATUS;
import static com.jhaner.esp32.helper.Constant.ARG_TIMEOFF;
import static com.jhaner.esp32.helper.Constant.ARG_TIMEON;
import static com.jhaner.esp32.helper.Constant.METHOD_MODIFYDATA;
import static com.jhaner.esp32.helper.Constant.SERVER_URL;

public class WorkerTask extends Worker {

    public WorkerTask(@NonNull Context context,
                        @NonNull WorkerParameters params) {
        super(context, params);
    }

    @Override
    public Result doWork()
    {
        String shield_id = getInputData().getString("SHIELD_ID");
        String module_id = getInputData().getString("MODULE_ID");
        String status = getInputData().getString("STATUS");
        String creation_date = getInputData().getString("CREATION_DATE");
        String cycles = getInputData().getString("CYCLES");
        String cycles_completed = getInputData().getString("CYCLES_COMPLETED");
        String time_on = getInputData().getString("TIME_ON");
        String time_off = getInputData().getString("TIME_OFF");
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
            connection.setRequestMethod("GET");
            connection.addRequestProperty("Cache-Control", "no-cache");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String string;
            while((string=bufferedReader.readLine())!=null)
            {
                stringBuilder.append(string+"\n");
            }
            connection.disconnect();
            Log.i("a",path);
            return Worker.Result.success();
        } catch (Exception exception) {
            Log.e("Error", "Error cleaning up", exception);
            return Worker.Result.failure();
        }
    }
}
