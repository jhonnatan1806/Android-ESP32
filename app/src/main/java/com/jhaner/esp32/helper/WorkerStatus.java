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

import static com.jhaner.esp32.helper.Constants.ARG_CREATIONDATE;
import static com.jhaner.esp32.helper.Constants.ARG_CYCLES;
import static com.jhaner.esp32.helper.Constants.ARG_CYCLESCOMPLETED;
import static com.jhaner.esp32.helper.Constants.ARG_MODULEID;
import static com.jhaner.esp32.helper.Constants.ARG_SHIELDID;
import static com.jhaner.esp32.helper.Constants.ARG_STATUS;
import static com.jhaner.esp32.helper.Constants.ARG_TIMEOFF;
import static com.jhaner.esp32.helper.Constants.ARG_TIMEON;
import static com.jhaner.esp32.helper.Constants.METHOD_MODIFYDATA;
import static com.jhaner.esp32.helper.Constants.SERVER_URL;

public class WorkerStatus extends Worker {


    public WorkerStatus(@NonNull Context context,
                           @NonNull WorkerParameters params) {
        super(context, params);
    }

    @Override
    public Result doWork()
    {
        String shield_id = getInputData().getString("SHIELD_ID");
        String module_id = getInputData().getString("MODULE_ID");
        String status = getInputData().getString("STATUS");
        String[] additional = {"2021-01-01+00:00:00","0"};
        String path = SERVER_URL    + METHOD_MODIFYDATA +
                ARG_SHIELDID        + shield_id +
                ARG_MODULEID        + module_id +
                ARG_CREATIONDATE    + additional[0] +
                ARG_STATUS          + status +
                ARG_CYCLES          + additional[1] +
                ARG_CYCLESCOMPLETED + additional[1] +
                ARG_TIMEON          + additional[1] +
                ARG_TIMEOFF         + additional[1];

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
            return Worker.Result.success();
        } catch (Exception exception) {
            Log.e("Error", "Error cleaning up", exception);
            return Worker.Result.failure();
        }
    }
}
