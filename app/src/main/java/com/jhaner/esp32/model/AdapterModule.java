package com.jhaner.esp32.model;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jhaner.esp32.R;

public class AdapterModule {

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
