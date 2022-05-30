package com.ntwk.sshcommander.ui.entities;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.ntwk.sshcommander.ui.utilities.Methods;

import java.util.HashMap;
import java.util.Map;

@Entity
public class CommandEntity implements Parcelable {
    @NonNull
    @PrimaryKey
    public String name;
    public String command;
    public Map<String, String> args = new HashMap<>();
    public Long lastTimeUsed; // epoch millis

    public String server;
    public int port;
    public String username;
    public String password;

    public String log;

    public CommandEntity() {
        name = Methods.generateRandomString(10);
    }


    protected CommandEntity(Parcel in) {
        name = in.readString();
        command = in.readString();
        in.readMap(args, String.class.getClassLoader());

        if (in.readByte() == 0) {
            lastTimeUsed = null;
        } else {
            lastTimeUsed = in.readLong();
        }
        server = in.readString();
        port = in.readInt();
        username = in.readString();
        password = in.readString();
        log = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(command);
        dest.writeMap(args);
        if (lastTimeUsed == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(lastTimeUsed);
        }
        dest.writeString(server);
        dest.writeInt(port);
        dest.writeString(username);
        dest.writeString(password);
        dest.writeString(log);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CommandEntity> CREATOR = new Creator<CommandEntity>() {
        @Override
        public CommandEntity createFromParcel(Parcel in) {
            return new CommandEntity(in);
        }

        @Override
        public CommandEntity[] newArray(int size) {
            return new CommandEntity[size];
        }
    };
}
