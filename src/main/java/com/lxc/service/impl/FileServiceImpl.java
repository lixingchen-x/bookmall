package com.lxc.service.impl;

import com.lxc.service.FileService;
import com.lxc.utils.FileUtils;
import com.lxc.utils.FilenameGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Component
public class FileServiceImpl implements FileService {

    @Value("${file.upload.path}")
    private String filePath;

    @Override
    public String uploadImg(MultipartFile file) throws IOException {

        String filename = file.getOriginalFilename();
        File destination = new File(filePath, filename);
        String uniqueId = FilenameGenerator.generate(filename);
        FileUtils.writeSuccess(file, destination);
        return filePath + uniqueId;
    }
}
