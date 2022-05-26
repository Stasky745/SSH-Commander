package com.ntwk.sshcommander.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.ntwk.sshcommander.R;
import com.ntwk.sshcommander.ui.adapters.CommandAdapterRV;
import com.ntwk.sshcommander.ui.entities.CommandEntity;

import java.util.ArrayList;

public class CommandsFragment extends Fragment {
    private static final String ARG_COMMANDS = "ARG_COMMANDS";

    private ArrayList<CommandEntity> commandsList;

    public static CommandsFragment newInstance(ArrayList<CommandEntity> commands){
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_COMMANDS, commands);
        CommandsFragment fragment = new CommandsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getArguments() != null;
        commandsList = getArguments().getParcelableArrayList(ARG_COMMANDS);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_view_fragment, container, false);

        RecyclerView rv = view.findViewById(R.id.RV_list);
        TextView emptyMsg = view.findViewById(R.id.TV_empty);

        if (commandsList.isEmpty()) {
            emptyMsg.setVisibility(View.VISIBLE);
            emptyMsg.setText(getString(R.string.empty_commands));
            rv.setVisibility(View.GONE);
        }
        else {
            emptyMsg.setVisibility(View.GONE);
            rv.setVisibility(View.VISIBLE);
            CommandAdapterRV adapter = new CommandAdapterRV(getContext(), commandsList);
            rv.setAdapter(adapter);
        }

        return view;
    }
}
