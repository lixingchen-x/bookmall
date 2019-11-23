package com.lxc.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {

    /**
     * upload multipartFile to a certain path
     * @throws IOException
     */
    String upload(MultipartFile file) throws IOException;
}
