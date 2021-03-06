package com.ntwk.sshcommander;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.ntwk.sshcommander.ui.converters.MapConverter;
import com.ntwk.sshcommander.ui.daos.CommandDAO;
import com.ntwk.sshcommander.ui.entities.CommandEntity;

@Database(entities = {
        CommandEntity.class
},
version = 3)
@TypeConverters(MapConverter.class)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CommandDAO commandDAO();
}
