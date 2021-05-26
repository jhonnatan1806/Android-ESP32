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

public class WorkerShield extends Worker {

    public WorkerShield(@NonNull Context context,
                        @NonNull WorkerParameters params) {
        super(context, params);
    }

    @Override
    public Result doWork()
    {
        StringBuilder stringBuilder = new StringBuilder();
        try
        {
            URL url = new URL("http://192.241.140.103/script.php?method=showshields");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
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
