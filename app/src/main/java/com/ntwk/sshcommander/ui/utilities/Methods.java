package com.ntwk.sshcommander.ui.utilities;

import com.ntwk.sshcommander.R;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class Methods {
    public static String millisToDate(long millis){
        if (millis < 0)
            return "-";

        LocalDateTime date = LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneId.systemDefault());
        return date.format(DateTimeFormatter.ofPattern("uuuu MMM d"));
    }

    public static String generateRandomString(int length) {
        int leftLimit = 48; // numeral 0
        int rightLimit = 122; // letter 'z'

        Random random = new Random();
        return random.ints(leftLimit, rightLimit + 1)
                // filter to leave out Unicode chars between 65 and 90 to avoid out of range characters
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
