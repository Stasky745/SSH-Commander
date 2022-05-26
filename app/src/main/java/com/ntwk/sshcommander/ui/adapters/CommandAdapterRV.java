package com.ntwk.sshcommander.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ntwk.sshcommander.R;
import com.ntwk.sshcommander.ui.entities.CommandEntity;
import com.ntwk.sshcommander.ui.utilities.Methods;

import java.util.List;

public class CommandAdapterRV extends RecyclerView.Adapter<CommandViewHolderRV> {

    List<CommandEntity> commandList;
    Context ctx;

    public CommandAdapterRV(Context context, List<CommandEntity> list){
        this.ctx = context;
        this.commandList = list;
    }

    @NonNull
    @Override
    public CommandViewHolderRV onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View commandView = inflater.inflate(R.layout.commands_fragment_item, parent, false);

        return new CommandViewHolderRV(commandView);
    }

    @Override
    public void onBindViewHolder(@NonNull CommandViewHolderRV holder, int position) {
        CommandEntity command = commandList.get(position);
        holder.username.setText(command.username);

        if (command.password != null && !command.password.equals(""))
            holder.password.setText(ctx.getString(R.string.password_not_set));
        else
            holder.password.setText("************");

        holder.server.setText(command.server);
        holder.lastTimeUsed.setText(Methods.millisToDate(command.lastTimeUsed));
        holder.log.setText(command.log);

        holder.info.setVisibility(View.GONE);

        holder.open.setOnClickListener(view -> {
            if (holder.info.getVisibility() == View.GONE)
                holder.info.setVisibility(View.VISIBLE);
            else
                holder.info.setVisibility(View.GONE);
        });

        holder.play.setOnClickListener(view -> {
            // TODO: Run SSH command
        });

        holder.edit.setOnClickListener(view -> {
            // TODO: Edit command
        });
    }

    @Override
    public int getItemCount() {
        return commandList.size();
    }
}
