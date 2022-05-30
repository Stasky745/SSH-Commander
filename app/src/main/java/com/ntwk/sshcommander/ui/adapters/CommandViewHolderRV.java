package com.ntwk.sshcommander.ui.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.ntwk.sshcommander.R;

public class CommandViewHolderRV extends RecyclerView.ViewHolder {
    TextView title;
    TextView command;
    TextView args;

    TextView username;
    TextView password;
    TextView server;
    TextView port;
    TextView lastTimeUsed;
    TextView log;

    ImageView play;
    ImageView edit;
    ImageView open;
    ImageView delete;

    ConstraintLayout info;

    public CommandViewHolderRV(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.TV_command_name);
        command = itemView.findViewById(R.id.TV_command_description);
        args = itemView.findViewById(R.id.TV_command_args);

        username = itemView.findViewById(R.id.TV_command_username);
        password = itemView.findViewById(R.id.TV_command_password);
        server = itemView.findViewById(R.id.TV_command_server);
        port = itemView.findViewById(R.id.TV_command_port);
        lastTimeUsed = itemView.findViewById(R.id.TV_command_lastTimeUsed);
        log = itemView.findViewById(R.id.TV_command_logScroll);

        play = itemView.findViewById(R.id.IV_command_start);
        edit = itemView.findViewById(R.id.IV_command_edit);
        open = itemView.findViewById(R.id.IV_command_open);
        delete = itemView.findViewById(R.id.IV_command_delete);

        info = itemView.findViewById(R.id.CL_command_moreInfo);
    }
}
