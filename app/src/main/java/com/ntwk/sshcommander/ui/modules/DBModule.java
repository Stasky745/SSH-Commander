package com.ntwk.sshcommander.ui.modules;

import android.app.Application;
import android.content.SharedPreferences;

import androidx.room.Room;

import com.ntwk.sshcommander.AppDatabase;
import com.ntwk.sshcommander.HiltApplication;
import com.ntwk.sshcommander.ui.daos.CommandDAO;
import com.ntwk.sshcommander.ui.utilities.Constants;

import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SupportFactory;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;

@Module
@InstallIn(ActivityComponent.class)
public class DBModule {
    @Inject
    SharedPreferences sharedPreferences;

    @Provides
    @Singleton
    public static AppDatabase getAppDB(Application application, String userEnteredPassphrase){
        // Encrypting ROOM DB
        // https://github.com/sqlcipher/android-database-sqlcipher#using-sqlcipher-for-android-with-room
        final byte[] passphrase = SQLiteDatabase.getBytes(userEnteredPassphrase.toCharArray());
        final SupportFactory factory = new SupportFactory(passphrase);
        return Room.databaseBuilder(application, AppDatabase.class, Constants.APP_DATABASE)
                .openHelperFactory(factory)
                .build();
    }

    @Provides
    @Singleton
    public static CommandDAO getCommandDao(AppDatabase appDatabase) {
        return appDatabase.commandDAO();
    }
}
