package com.lxc.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Slf4j
public class FileUtils {

    public static Boolean writeSuccess(MultipartFile file, File destination) throws IOException {

        try {
            file.transferTo(destination);
            return true;
        } catch (IOException e) {
            log.error("{} failed writing into directory.", file.getOriginalFilename());
            return false;
        }
    }
}
