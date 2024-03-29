package com.jhaner.esp32.presenter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.jhaner.esp32.R;
import com.jhaner.esp32.model.ModelShield;

import java.util.ArrayList;

import static com.jhaner.esp32.helper.Constants.KEY_SHIELDID;

public class AdapterShield extends RecyclerView.Adapter<AdapterShield.ViewHolder>
{
    private final ArrayList<ModelShield> dataSet;

    public AdapterShield(ArrayList<ModelShield> dataSet) { this.dataSet = dataSet; }

    @Override
    public int getItemViewType(final int position) { return R.layout.cardview_shield; }

    @Override @NonNull
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
        holder.itemView.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putString(KEY_SHIELDID, dataSet.get(position).getShield_id());
            Navigation.findNavController(view).navigate(R.id.action_FragmentShield_to_FragmentModule, bundle);
        });
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView shield_id;
        public TextView name;
        public TextView model;
        public TextView mac;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            shield_id = (TextView) itemView.findViewById(R.id.cs_shield_id);
            name = (TextView) itemView.findViewById(R.id.cs_name);
            model = (TextView) itemView.findViewById(R.id.cs_model);
            mac = (TextView) itemView.findViewById(R.id.cs_mac);
        }
    }
}

