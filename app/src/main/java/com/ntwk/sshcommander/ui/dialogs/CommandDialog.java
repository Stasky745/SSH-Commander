package com.ntwk.sshcommander.ui.dialogs;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.ntwk.sshcommander.R;
import com.ntwk.sshcommander.ui.adapters.ArgsAdapterRV;
import com.ntwk.sshcommander.ui.entities.CommandEntity;
import com.ntwk.sshcommander.ui.view_models.CommandViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CommandDialog extends DialogFragment {
    private final static String ARG_COMMAND = "ARG_COMMAND";
    private final static String ARG_NAMELIST = "ARG_NAMELIST";

    private CommandEntity commandEntity;
    private List<String> commandNames;

    private TextInputEditText title;
    private TextInputEditText command;
    private TextInputEditText args;
    private TextInputEditText username;
    private TextInputEditText password;
    private TextInputEditText server;
    private TextInputEditText port;

    private TextInputLayout argsLayout;

    private ArgsAdapterRV adapter;

    private RecyclerView argsRV;
    private TextView argsTV;
    private ImageView argsIV;
    private ImageView addArgIV;

    private CommandViewModel commandViewModel;

    public CommandDialog() { }

    public static CommandDialog newInstance(CommandEntity command, ArrayList<String> list){
        CommandDialog frag = new CommandDialog();
        Bundle args = new Bundle();
        args.putParcelable(ARG_COMMAND, command);
        args.putStringArrayList(ARG_NAMELIST, list);
        frag.setArguments(args);
        return frag;
    }

    private boolean allDataEntered() {
        return  !Objects.requireNonNull(title.getText()).toString().equals("") &&
                !Objects.requireNonNull(command.getText()).toString().equals("") &&
                !Objects.requireNonNull(server.getText()).toString().equals("") &&
                !Objects.requireNonNull(port.getText()).toString().equals("");
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        assert getArguments() != null;
        commandEntity = getArguments().getParcelable(ARG_COMMAND);
        commandNames = getArguments().getStringArrayList(ARG_NAMELIST);
        commandViewModel = new ViewModelProvider(requireActivity()).get(CommandViewModel.class);

        String dialogTitle = commandEntity == null ? getString(R.string.new_command) : getString(R.string.edit_command);

        @SuppressLint("InflateParams") View view = requireActivity().getLayoutInflater().inflate(R.layout.commands_dialog, null);

        AlertDialog dialog = new AlertDialog.Builder(requireActivity())
                .setView(setView(view))
                .setTitle(dialogTitle)
                .setPositiveButton(R.string.accept, null)
                .setNegativeButton(R.string.cancel, (dialogInterface, i) -> dialogInterface.cancel())
                .create();

        dialog.setOnShowListener(dialogInterface -> {
            // Set positive button
            Button bp = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            bp.setText(R.string.accept);
            bp.setOnClickListener(view2 -> {
                if (!allDataEntered())
                    Toast.makeText(requireContext(), R.string.error_new_command, Toast.LENGTH_SHORT).show();
                else {
                    CommandEntity newCommand = new CommandEntity();
                    newCommand.name = Objects.requireNonNull(title.getText()).toString();

                    if (commandNames.contains(newCommand.name)){
                        new AlertDialog.Builder(requireActivity())
                                .setTitle(R.string.command_exists)
                                .setMessage(R.string.command_exists_body)
                                .setPositiveButton(R.string.accept, (DialogInterface.OnClickListener) (dialogInterface1, i) -> dialogInterface1.dismiss())
                                .show();
                        return;
                    }

                    newCommand.command = Objects.requireNonNull(command.getText()).toString();
                    newCommand.server = Objects.requireNonNull(server.getText()).toString();
                    newCommand.port = Integer.parseInt(Objects.requireNonNull(port.getText()).toString());

                    // If username is set, we store it, and check if password is set too
                    if (username.getText() != null) {
                        newCommand.username = username.getText().toString();

                        if(password.getText() != null)
                            newCommand.password = password.getText().toString();
                    }

                    newCommand.args = adapter.getArgsMap();

                    // If we are editing, we keep the same info from the older object
                    if (commandEntity != null) {
                        newCommand.lastTimeUsed = commandEntity.lastTimeUsed;
                        newCommand.log = commandEntity.log;

                        commandViewModel.deleteCommand(commandEntity);
                    }

                    commandViewModel.insertCommand(newCommand);
                    dismiss();
                }
            });
        });

        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(getShowsDialog())
            return super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.commands_dialog, container);
        return setView(view);
    }

    private View setView(View view) {
        title = view.findViewById(R.id.TIET_comDialog_title);
        command = view.findViewById(R.id.TIET_comDialog_command);
        argsLayout = view.findViewById(R.id.TIL_comDialog_args);
        args = view.findViewById(R.id.TIET_comDialog_args);
        username = view.findViewById(R.id.TIET_comDialog_username);
        password = view.findViewById(R.id.TIET_comDialog_password);
        server = view.findViewById(R.id.TIET_comDialog_server);
        port = view.findViewById(R.id.TIET_comDialog_port);

        argsRV = view.findViewById(R.id.RV_comDialog_args);
        argsIV = view.findViewById(R.id.IV_comDialog_args);
        argsTV = view.findViewById(R.id.TV_comDialog_args);
        addArgIV = view.findViewById(R.id.IV_comDialog_addArg);

        setVisibilities();
        if(commandEntity != null)
            fillInformation();

        setRV();

        return view;
    }

    private void setRV() {
        Map<String, String> map = commandEntity != null && commandEntity.args != null ? commandEntity.args : new HashMap<>();
        adapter = new ArgsAdapterRV(getActivity(), map);
        argsRV.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        argsRV.setAdapter(adapter);
    }

    private void setVisibilities() {
        argsLayout.setVisibility(View.GONE);

        argsRV.setVisibility(View.VISIBLE);
        argsIV.setVisibility(View.VISIBLE);
        argsTV.setVisibility(View.VISIBLE);

        argsIV.setOnClickListener(view -> {
            if (argsLayout.getVisibility() == View.GONE){
                argsLayout.setVisibility(View.VISIBLE);

                argsRV.setVisibility(View.GONE);
                argsTV.setVisibility(View.GONE);
                addArgIV.setVisibility(View.GONE);

                argsIV.setImageResource(R.drawable.table);
            }
            else {
                argsLayout.setVisibility(View.GONE);

                argsRV.setVisibility(View.VISIBLE);
                argsTV.setVisibility(View.VISIBLE);
                addArgIV.setVisibility(View.VISIBLE);

                argsIV.setImageResource(R.drawable.text_box_edit);
            }
        });

        addArgIV.setOnClickListener(view -> {
            Map<String, String> map = new LinkedHashMap<>();
            for(int i = 0; i < argsRV.getChildCount(); i++){
                View rvView = argsRV.getChildAt(i);
                TextInputEditText TIET_flag = rvView.findViewById(R.id.TIET_args_flag);
                TextInputEditText TIET_value = rvView.findViewById(R.id.TIET_args_value);

                if (TIET_flag.getText() != null && !TIET_flag.getText().toString().equals("")) {
                    map.put(
                            TIET_flag.getText().toString(),
                            TIET_value.getText() != null ? TIET_value.getText().toString() : ""
                    );
                }
            }
            adapter.addItem(map);
        });
    }

    private void fillInformation() {
        title.setText(commandEntity.name);
        command.setText(commandEntity.command);
        username.setText(commandEntity.username);
        password.setText(commandEntity.password);
        server.setText(commandEntity.server);
        port.setText(String.valueOf(commandEntity.port));
    }
}
