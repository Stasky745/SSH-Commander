package com.ntwk.sshcommander.ui.utilities;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Methods {
    public static String millisToDate(long millis){
        if (millis < 0)
            return "-";

        LocalDateTime date = LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneId.systemDefault());
        return date.format(DateTimeFormatter.ofPattern("uuuu MMM d"));
    }
}
