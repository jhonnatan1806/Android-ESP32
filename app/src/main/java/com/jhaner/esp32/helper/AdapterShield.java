package com.jhaner.esp32.helper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jhaner.esp32.R;
import com.jhaner.esp32.helper.ViewHolderShield;
import com.jhaner.esp32.model.ModelShield;

import java.util.ArrayList;

public class AdapterShield extends RecyclerView.Adapter<ViewHolderShield> {

    private ArrayList<ModelShield> mDataSet;

    public AdapterShield(ArrayList<ModelShield> myDataSet) { this.mDataSet = myDataSet; }

    @Override
    public int getItemViewType(final int position) {
        return R.layout.cardview_shield;
    }

    @NonNull
    @Override
    public ViewHolderShield onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new ViewHolderShield(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderShield holder, int position) {
        holder.getId().setText(mDataSet.get(position).getId());
        holder.getName().setText(mDataSet.get(position).getName());
        holder.getModel().setText(mDataSet.get(position).getModel());
        holder.getMac().setText(mDataSet.get(position).getMac());
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}

