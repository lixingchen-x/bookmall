package com.lxc.utils;

public class FilenameGenerator {

    public static String generate(String filename) {

        int index = filename.lastIndexOf(".");
        String suffix = filename.substring(index);
        String uniqueId = UUIDGenerator.generate();
        return uniqueId+suffix;
    }
}
