package com.ntwk.sshcommander.ui.entities;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class CommandEntity implements Parcelable {
    @PrimaryKey
    public String name;
    public String description;
    public Long lastTimeUsed; // epoch millis

    public String server;
    public String username;
    public String password;

    public String log;

    protected CommandEntity(Parcel in) {
        name = in.readString();
        description = in.readString();
        if (in.readByte() == 0) {
            lastTimeUsed = null;
        } else {
            lastTimeUsed = in.readLong();
        }
        server = in.readString();
        username = in.readString();
        password = in.readString();
        log = in.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(description);
        if (lastTimeUsed == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(lastTimeUsed);
        }
        parcel.writeString(server);
        parcel.writeString(username);
        parcel.writeString(password);
        parcel.writeString(log);
    }
}
