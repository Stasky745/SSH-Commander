package com.ntwk.sshcommander.ui.modules;

import android.app.Application;

import androidx.room.Room;

import com.ntwk.sshcommander.AppDatabase;
import com.ntwk.sshcommander.ui.daos.CommandDAO;
import com.ntwk.sshcommander.ui.utilities.Constants;

import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SupportFactory;

import java.util.Objects;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class DBModule {
    @Provides
    @Singleton
    public static AppDatabase getAppDB(Application application){
        // Encrypting ROOM DB
        // https://github.com/sqlcipher/android-database-sqlcipher#using-sqlcipher-for-android-with-room
        String userEnteredPassphrase = Objects.requireNonNull(GeneralModule.getSharedPrefs(application)).getString(Constants.SHARED_PREFS_DB_PASSPHRASE, "");
        final byte[] passphrase = SQLiteDatabase.getBytes(userEnteredPassphrase.toCharArray());
        final SupportFactory factory = new SupportFactory(passphrase);
        return Room.databaseBuilder(application, AppDatabase.class, Constants.APP_DATABASE)
                .fallbackToDestructiveMigration()
                .openHelperFactory(factory)
                .build();
    }

    @Provides
    @Singleton
    public static CommandDAO getCommandDao(AppDatabase appDatabase) {
        return appDatabase.commandDAO();
    }
}
