package com.ntwk.sshcommander.ui.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ntwk.sshcommander.R;
import com.ntwk.sshcommander.ui.adapters.CommandAdapterRV;
import com.ntwk.sshcommander.ui.dialogs.CommandDialog;
import com.ntwk.sshcommander.ui.entities.CommandEntity;
import com.ntwk.sshcommander.ui.view_models.CommandViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CommandsFragment extends Fragment {
    private static final String ARG_COMMANDS = "ARG_COMMANDS";

    private ArrayList<CommandEntity> commandsList = new ArrayList<>();
    private CommandAdapterRV adapter;
    private RecyclerView rv;
    private FloatingActionButton fab;
    private TextView emptyMsg;

    CommandViewModel commandViewModel;

    public static CommandsFragment newInstance(){
        return new CommandsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        commandViewModel = new ViewModelProvider(this).get(CommandViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_view_fragment, container, false);

        rv = view.findViewById(R.id.RV_list);
        emptyMsg = view.findViewById(R.id.TV_empty);
        adapter = new CommandAdapterRV(getActivity());

        rv.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        rv.setAdapter(adapter);

        commandViewModel.loadCommands();
        getCommands();

        fab = view.findViewById(R.id.FAB_actionButton);
        fab.setOnClickListener(view1 -> setAddDialog());

        return view;
    }

    private void setVisibilities() {
        if (commandsList.isEmpty()) {
            emptyMsg.setVisibility(View.VISIBLE);
            rv.setVisibility(View.GONE);
        }
        else {
            emptyMsg.setVisibility(View.GONE);
            rv.setVisibility(View.VISIBLE);
        }
    }

    private void setAddDialog() {
        FragmentManager fm = getChildFragmentManager();

        // We pass null since we are trying to add a new Command
        List<String> commandNames = commandsList.stream()
                .map(commandEntity -> commandEntity.name)
                .collect(Collectors.toList());
        CommandDialog commandDialog = CommandDialog.newInstance(null, new ArrayList<>(commandNames));
        commandDialog.show(fm, "fragment_create_command");
    }

    private void getCommands() {
        commandViewModel.commandList.observe(getViewLifecycleOwner(), commands -> {
            commandsList = new ArrayList<>(commands);
            adapter.updateData(commandsList);
            setVisibilities();
        });
    }
}
