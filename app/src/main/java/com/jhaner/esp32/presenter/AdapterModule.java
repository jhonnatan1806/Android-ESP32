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
import com.jhaner.esp32.model.ModelModule;

import java.util.ArrayList;

import static com.jhaner.esp32.helper.Constants.KEY_MODULEID;
import static com.jhaner.esp32.helper.Constants.KEY_SHIELDID;

public class AdapterModule extends RecyclerView.Adapter<AdapterModule.ViewHolder> {

    private final ArrayList<ModelModule> dataSet;

    public AdapterModule(ArrayList<ModelModule> dataSet) { this.dataSet = dataSet; }

    @Override
    public int getItemViewType(final int position) {
        return R.layout.cardview_module;
    }

    @Override @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        holder.module_id.setText(dataSet.get(position).getModule_id());
        holder.name.setText(dataSet.get(position).getName());
        holder.type.setText(dataSet.get(position).getType());
        holder.description.setText(dataSet.get(position).getDescription());
        holder.itemView.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putString(KEY_SHIELDID, dataSet.get(position).getShield_id());
            bundle.putString(KEY_MODULEID, dataSet.get(position).getModule_id());
            Navigation.findNavController(view).navigate(R.id.action_FragmentModule_to_FragmentForm, bundle);
        });
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView module_id;
        public TextView name;
        public TextView type;
        public TextView description;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            module_id = itemView.findViewById(R.id.cm_module_id);
            name = itemView.findViewById(R.id.cm_name);
            type = itemView.findViewById(R.id.cm_type);
            description = itemView.findViewById(R.id.cm_description);
        }
    }
}
