package com.jhaner.esp32.model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.jhaner.esp32.R;

import java.util.ArrayList;

public class AdapterModule extends RecyclerView.Adapter<AdapterModule.ViewHolder> {

    private ArrayList<ModelModule> dataSet;

    public AdapterModule(ArrayList<ModelModule> dataSet) { this.dataSet = dataSet; }

    @Override
    public int getItemViewType(final int position) {
        return R.layout.cardview_module;
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

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {

        public TextView module_id;
        public TextView status;
        public TextView cycles;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            module_id = (TextView) itemView.findViewById(R.id.c_m_module_id);
            status = (TextView) itemView.findViewById(R.id.c_m_status);
            cycles = (TextView) itemView.findViewById(R.id.c_m_cycles);
        }

    }
}
