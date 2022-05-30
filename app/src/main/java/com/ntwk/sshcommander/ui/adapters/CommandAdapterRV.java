package com.ntwk.sshcommander.ui.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.UiThread;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.ntwk.sshcommander.R;
import com.ntwk.sshcommander.ui.dialogs.CommandDialog;
import com.ntwk.sshcommander.ui.entities.CommandEntity;
import com.ntwk.sshcommander.ui.utilities.Methods;
import com.ntwk.sshcommander.ui.view_models.CommandViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CommandAdapterRV extends RecyclerView.Adapter<CommandViewHolderRV> {

    List<CommandEntity> commandList = new ArrayList<>();
    Context ctx;

    public CommandAdapterRV(Context context){
        this.ctx = context;
    }

    @NonNull
    @Override
    public CommandViewHolderRV onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View commandView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.commands_fragment_item, parent, false);

        return new CommandViewHolderRV(commandView);
    }

    @SuppressLint("NotifyDataSetChanged")
    @UiThread
    public void updateData(List<CommandEntity> list) {
        commandList = list;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull CommandViewHolderRV holder, int position) {
        CommandEntity command = commandList.get(position);

        holder.title.setText(command.name);
        holder.command.setText(command.command);
        String map2string = command.args.keySet().stream()
                .map(key -> key + " " + command.args.get(key))
                .collect(Collectors.joining(" "));
        holder.args.setText(map2string);

        holder.username.setText(command.username);

        if (command.password == null || command.password.equals(""))
            holder.password.setText(ctx.getString(R.string.password_not_set));
        else
            holder.password.setText("************");

        holder.server.setText(command.server);
        holder.port.setText(String.valueOf(command.port));
        if (command.lastTimeUsed != null)
            holder.lastTimeUsed.setText(Methods.millisToDate(command.lastTimeUsed));
        else
            holder.lastTimeUsed.setText("-");

        if (command.log != null)
            holder.log.setText(command.log);
        else
            holder.log.setText("");

        holder.info.setVisibility(View.GONE);

        holder.open.setOnClickListener(view -> {
            if (holder.info.getVisibility() == View.GONE) {
                holder.info.setVisibility(View.VISIBLE);
                holder.open.setImageResource(R.drawable.chevron_up);
            }
            else {
                holder.info.setVisibility(View.GONE);
                holder.open.setImageResource(R.drawable.chevron_down);
            }
        });

        holder.play.setOnClickListener(view -> {
            // TODO: Run SSH command
        });

        holder.edit.setOnClickListener(view -> {
            FragmentManager fm = ((AppCompatActivity) ctx).getSupportFragmentManager();
            // We filter out the current name
            List<String> commandNames = commandList.stream()
                    .filter(commandEntity -> !commandEntity.name.equals(command.name))
                    .map(commandEntity -> commandEntity.name)
                    .collect(Collectors.toList());
            // We pass the item since we are trying to edit a Command
            CommandDialog commandDialog = CommandDialog.newInstance(command, new ArrayList<>(commandNames));
            commandDialog.show(fm, "fragment_edit_command");
        });

        holder.delete.setOnClickListener(view -> new AlertDialog.Builder(ctx)
                .setTitle(R.string.delete_command)
                .setMessage(R.string.delete_command_body)
                .setPositiveButton(R.string.accept, (dialogInterface, i) -> {
                    CommandViewModel commandViewModel = new ViewModelProvider((AppCompatActivity) ctx).get(CommandViewModel.class);
                    commandViewModel.deleteCommand(command);
                })
                .setNegativeButton(R.string.cancel, (dialogInterface, i) -> dialogInterface.cancel())
                .show()
        );
    }

    @Override
    public int getItemCount() {
        return commandList.size();
    }
}
