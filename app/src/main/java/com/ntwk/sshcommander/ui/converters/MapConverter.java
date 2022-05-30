package com.ntwk.sshcommander.ui.converters;

import androidx.room.TypeConverter;

import java.util.HashMap;
import java.util.Map;

public class MapConverter {

    @TypeConverter
    public Map<String, String> toMap(String s){
        Map<String, String> map = new HashMap<>();
        for(String arg : s.split("\n")){
            String[] args = arg.split(" ",1);
            if(args.length > 1)
                map.put(args[0], args[1]);
            else
                map.put(args[0], "");
        }
        return map;
    }

    @TypeConverter
    public String fromMap(Map<String, String> map){
        StringBuilder s = new StringBuilder();
        for (Map.Entry<String, String> p : map.entrySet()){
            s.append(p.getKey()).append(" ").append(p.getValue()).append("\n");
        }
        return s.toString();
    }
}
