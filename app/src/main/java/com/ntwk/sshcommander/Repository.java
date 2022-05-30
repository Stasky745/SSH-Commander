package com.ntwk.sshcommander;

import android.content.SharedPreferences;
import android.database.Observable;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.OnConflictStrategy;

import com.ntwk.sshcommander.ui.daos.CommandDAO;
import com.ntwk.sshcommander.ui.entities.CommandEntity;
import com.ntwk.sshcommander.ui.utilities.CustomDisposable;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.disposables.Disposable;

public class Repository {
    private static final String TAG = Repository.class.getSimpleName();
    private SharedPreferences sharedPreferences;

    private CommandDAO commandDao;

    private final MutableLiveData<CommandEntity> commandLiveData = new MutableLiveData<>();

    @Inject
    public Repository(SharedPreferences sharedPreferences, CommandDAO commandDao) {
        this.sharedPreferences = sharedPreferences;
        this.commandDao = commandDao;
    }

    //region CommandDAO methods
    public LiveData<List<CommandEntity>> getAllCommands() {
        return commandDao.getAll();
    }

    public void insertCommand(CommandEntity commandEntity) {
        Completable insert = commandDao.insert(commandEntity);
        CustomDisposable.addDisposable(insert, () -> Log.d(TAG, "Inserted command:  " + commandEntity.name));
    }

    public void deleteCommand(CommandEntity commandEntity) {
        Completable delete = commandDao.delete(commandEntity);
        CustomDisposable.addDisposable(delete, () -> Log.d(TAG, "Deleted command:  " + commandEntity.name));
    }

    public CommandEntity getCommandByName(String name){
        return commandDao.getCommandByName(name);
    }
    //endregion
}
