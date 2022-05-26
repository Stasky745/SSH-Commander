package com.ntwk.sshcommander.ui.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.ntwk.sshcommander.ui.entities.CommandEntity;

import java.util.List;

@Dao
public interface CommandDAO {
    @Query("SELECT * FROM CommandEntity")
    List<CommandEntity> getAll();

    @Insert
    void insert(CommandEntity command);

    @Delete
    void delete(CommandEntity command);
}
