package com.jhaner.esp32.helper;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jhaner.esp32.R;

public class ViewHolderShield extends RecyclerView.ViewHolder {

    private TextView id;
    private TextView name;
    private TextView model;
    private TextView mac;

    public ViewHolderShield(@NonNull View itemView) {
        super(itemView);
        id = (TextView) itemView.findViewById(R.id.c_s_shield_id);
        name = (TextView) itemView.findViewById(R.id.c_s_name);
        model = (TextView) itemView.findViewById(R.id.c_s_model);
        mac = (TextView) itemView.findViewById(R.id.c_s_mac);
    }

    public TextView getId() { return id; }

    public TextView getName() { return name; }

    public TextView getModel() { return model; }

    public TextView getMac() { return mac; }
}