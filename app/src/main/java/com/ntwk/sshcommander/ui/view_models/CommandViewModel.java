package com.ntwk.sshcommander.ui.view_models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.room.OnConflictStrategy;

import com.ntwk.sshcommander.Repository;
import com.ntwk.sshcommander.ui.entities.CommandEntity;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class CommandViewModel extends ViewModel {
    private final Repository repository;
    public LiveData<List<CommandEntity>> commandList;

    @Inject
    public CommandViewModel(Repository repository){
        this.repository = repository;
    }

    public void loadCommands() {
        this.commandList = repository.getAllCommands();
    }

    public void insertCommand(CommandEntity command) {
        repository.insertCommand(command);
    }

    public void deleteCommand(CommandEntity command) {
        repository.deleteCommand(command);
    }
}
