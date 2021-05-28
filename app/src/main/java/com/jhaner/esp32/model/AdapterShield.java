package com.jhaner.esp32.model;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.jhaner.esp32.R;

import java.util.ArrayList;

public class AdapterShield extends RecyclerView.Adapter<AdapterShield.ViewHolder>
{
    private ArrayList<ModelShield> dataSet;

    public AdapterShield(ArrayList<ModelShield> dataSet) { this.dataSet = dataSet; }

    @Override
    public int getItemViewType(final int position) {
        return R.layout.cardview_shield;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        holder.shield_id.setText(dataSet.get(position).getShield_id());
        holder.name.setText(dataSet.get(position).getName());
        holder.model.setText(dataSet.get(position).getModel());
        holder.mac.setText(dataSet.get(position).getMac());
        holder.btn_access.setOnClickListener(view -> {
            String shield_id = dataSet.get(position).getShield_id();
            Bundle bundle = new Bundle();
            bundle.putString("shield_id", shield_id);
            Navigation.findNavController(view).navigate(R.id.action_FragmentShield_to_FragmentModule, bundle);
        });
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {

        public TextView shield_id;
        public TextView name;
        public TextView model;
        public TextView mac;
        public ImageButton btn_access;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            shield_id = (TextView) itemView.findViewById(R.id.c_s_shield_id);
            name = (TextView) itemView.findViewById(R.id.c_s_name);
            model = (TextView) itemView.findViewById(R.id.c_s_model);
            mac = (TextView) itemView.findViewById(R.id.c_s_mac);
            btn_access = (ImageButton) itemView.findViewById(R.id.c_s_btn_access);
        }

    }
}

