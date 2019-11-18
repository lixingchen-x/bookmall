package com.lxc.service.impl;

import com.lxc.service.FileService;
import com.lxc.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Component
public class FileServiceImpl implements FileService {

    @Autowired
    private FileUtils fileUtils;

    @Value("${file.upload.path}")
    private String filePath;

    @Value("${file.upload.path.relative}")
    private String fileRelativePath;

    @Override
    public String upload(MultipartFile file) {

        String filename = file.getOriginalFilename();
        File destination = new File(filePath, filename);
        fileUtils.write(file, destination);
        return fileRelativePath + fileUtils.filenameGenerator(filename);
    }
}
