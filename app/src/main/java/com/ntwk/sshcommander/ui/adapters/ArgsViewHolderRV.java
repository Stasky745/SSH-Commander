package com.ntwk.sshcommander.ui.adapters;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.ntwk.sshcommander.R;

public class ArgsViewHolderRV extends RecyclerView.ViewHolder {
    TextInputEditText flag, value;
    ImageView delete;

    public ArgsViewHolderRV(@NonNull View itemView) {
        super(itemView);
        flag = itemView.findViewById(R.id.TIET_args_flag);
        value = itemView.findViewById(R.id.TIET_args_value);
        delete = itemView.findViewById(R.id.IV_args_delete);
    }
}
