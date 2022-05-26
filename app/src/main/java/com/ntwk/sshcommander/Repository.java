package com.ntwk.sshcommander;

import android.content.SharedPreferences;

import com.ntwk.sshcommander.ui.daos.CommandDAO;
import com.ntwk.sshcommander.ui.entities.CommandEntity;

import java.util.List;

import javax.inject.Inject;

public class Repository {
    @Inject
    SharedPreferences sharedPreferences;

    @Inject
    CommandDAO commandDao;

    @Inject
    public Repository(SharedPreferences sharedPreferences, CommandDAO commandDao) {
        this.sharedPreferences = sharedPreferences;
        this.commandDao = commandDao;
    }

    //region CommandDAO methods
    public List<CommandEntity> getAllCommands() {
        return commandDao.getAll();
    }

    public void insertCommand(CommandEntity commandEntity) {
        commandDao.insert(commandEntity);
    }

    public void deleteCommand(CommandEntity commandEntity) {
        commandDao.delete(commandEntity);
    }
    //endregion
}
