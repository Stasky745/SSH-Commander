package com.ntwk.sshcommander.ui.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.ntwk.sshcommander.R;

public class CommandViewHolderRV extends RecyclerView.ViewHolder {
    TextView username;
    TextView password;
    TextView server;
    TextView lastTimeUsed;
    TextView log;

    ImageView play;
    ImageView edit;
    ImageView open;

    ConstraintLayout info;

    public CommandViewHolderRV(@NonNull View itemView) {
        super(itemView);
        username = itemView.findViewById(R.id.TV_command_username);
        password = itemView.findViewById(R.id.TV_command_password);
        server = itemView.findViewById(R.id.TV_command_server);
        lastTimeUsed = itemView.findViewById(R.id.TV_command_lastTimeUsed);
        log = itemView.findViewById(R.id.TV_command_logScroll);

        play = itemView.findViewById(R.id.IV_command_start);
        edit = itemView.findViewById(R.id.IV_command_edit);
        open = itemView.findViewById(R.id.IV_command_open);

        info = itemView.findViewById(R.id.CL_command_moreInfo);
    }
}
