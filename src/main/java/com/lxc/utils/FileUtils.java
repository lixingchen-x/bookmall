package com.lxc.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Slf4j
@Component
public class FileUtils {

    @Autowired
    private UUIDGenerator uuidGenerator;

    public String generateRandomFilename(String filename) {

        return uuidGenerator.generate()+getSuffix(filename);
    }

    public void write(MultipartFile file, File destination) throws IOException {

        file.transferTo(destination);
    }

    private String getSuffix(String filename) {

        int index = filename.lastIndexOf(".");
        return filename.substring(index);
    }
}
