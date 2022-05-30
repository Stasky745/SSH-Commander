package com.ntwk.sshcommander.ui.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ntwk.sshcommander.ui.entities.CommandEntity;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface CommandDAO {
    @Query("SELECT * FROM CommandEntity")
    LiveData<List<CommandEntity>> getAll();

    @Insert
    Completable insert(CommandEntity command);

    @Delete
    Completable delete(CommandEntity command);

    @Query("SELECT * FROM CommandEntity WHERE name LIKE :name")
    CommandEntity getCommandByName(String name);
}
