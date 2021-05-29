package com.jhaner.esp32.helper;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.jhaner.esp32.helper.Constant.ARG_SHIELDID;
import static com.jhaner.esp32.helper.Constant.METHOD_SHOWMODULES;
import static com.jhaner.esp32.helper.Constant.SERVER_URL;

public class WorkerModule extends Worker {

    public WorkerModule(@NonNull Context context,
                        @NonNull WorkerParameters params) {
        super(context, params);
    }

    @Override
    public Result doWork()
    {
        String shield_id = getInputData().getString("SHIELD_ID");
        StringBuilder stringBuilder = new StringBuilder();
        try
        {
            URL url = new URL(SERVER_URL + METHOD_SHOWMODULES + ARG_SHIELDID + shield_id);
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
            Data outputData = new Data.Builder()
                    .putString("HTML", stringBuilder.toString())
                    .build();
            return Worker.Result.success(outputData);
        } catch (Exception exception) {
            Log.e("Error", "Error cleaning up", exception);
            return Worker.Result.failure();
        }
    }
}
