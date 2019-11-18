package com.lxc.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Slf4j
@Component
public class FileUtils {

    public String filenameGenerator(String filename) {

        return UUIDGenerator.generate()+getSuffix(filename);
    }

    public void write(MultipartFile file, File destination) {

        try {
            file.transferTo(destination);
        } catch (IOException e) {
            log.error("{} failed writing into directory.", file.getOriginalFilename());
        }
    }

    private String getSuffix(String filename) {

        int index = filename.lastIndexOf(".");
        return filename.substring(index);
    }
}
