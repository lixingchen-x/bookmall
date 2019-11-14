package com.lxc.utils;

import java.util.UUID;

public class UUIDGenerator {

    public static String getUUID(String filename) {

        int index = filename.lastIndexOf(".");
        String suffix = filename.substring(index);
        String uniqueId = UUID.randomUUID().toString().replace("-", "");
        return uniqueId+suffix;
    }
}
