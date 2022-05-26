package com.ntwk.sshcommander.ui.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.ntwk.sshcommander.ui.entities.CommandEntity;
import com.ntwk.sshcommander.ui.fragments.CommandsFragment;

import java.util.ArrayList;
import java.util.List;

public class TabAdapter extends FragmentStateAdapter {
    ArrayList<CommandEntity> commandList;
    public TabAdapter(@NonNull FragmentActivity fragmentActivity, List<CommandEntity> commandList) {
        super(fragmentActivity);
        this.commandList = new ArrayList<>(commandList);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return CommandsFragment.newInstance(commandList);
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
