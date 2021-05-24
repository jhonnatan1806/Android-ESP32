package com.jhaner.esp32.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jhaner.esp32.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class FragmentShield extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentShield() {

    }

    public static FragmentShield newInstance(String param1, String param2) {
        FragmentShield fragment = new FragmentShield();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        URL google = null;
        try {
            google = new URL("http://192.241.140.103/script.php?method=showshields");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(google.openStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String input = null;
        StringBuffer stringBuffer = new StringBuffer();
        while (true)
        {
            try {
                if (!((input = in.readLine()) != null)) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            stringBuffer.append(input);
        }
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String htmlData = stringBuffer.toString();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shield, container, false);
    }

}