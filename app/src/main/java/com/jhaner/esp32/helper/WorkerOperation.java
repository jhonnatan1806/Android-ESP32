package com.jhaner.esp32.helper;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.jhaner.esp32.helper.Constants.ARG_MODULEID;
import static com.jhaner.esp32.helper.Constants.ARG_SHIELDID;
import static com.jhaner.esp32.helper.Constants.HTML_CACHECONTROL;
import static com.jhaner.esp32.helper.Constants.HTML_KEY;
import static com.jhaner.esp32.helper.Constants.HTML_METHOD;
import static com.jhaner.esp32.helper.Constants.HTML_NOCACHE;
import static com.jhaner.esp32.helper.Constants.KEY_MODULEID;
import static com.jhaner.esp32.helper.Constants.KEY_SHIELDID;
import static com.jhaner.esp32.helper.Constants.METHOD_SHOWDATA;
import static com.jhaner.esp32.helper.Constants.SERVER_URL;

public class WorkerOperation extends Worker {

    public WorkerOperation(@NonNull Context context, @NonNull WorkerParameters params)
    {
        super(context, params);
    }

    @Override @NonNull
    public Result doWork()
    {
        String shield_id = getInputData().getString(KEY_SHIELDID);
        String module_id = getInputData().getString(KEY_MODULEID);
        String path = SERVER_URL + METHOD_SHOWDATA +
                ARG_SHIELDID + shield_id +
                ARG_MODULEID + module_id;
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
            Data outputData = new Data.Builder()
                    .putString(HTML_KEY, stringBuilder.toString())
                    .build();
            return Worker.Result.success(outputData);
        } catch (Exception exception)
        {
            return Worker.Result.failure();
        }
    }
}
