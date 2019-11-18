package com.lxc.service.impl;

import com.lxc.utils.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class FileServiceImplTest {

    @InjectMocks
    private FileServiceImpl fileService;

    @Mock
    private FileUtils fileUtils;

    @Before
    public void setUp() {

        ReflectionTestUtils.setField(fileService, "filePath", "E:\\images\\");
        ReflectionTestUtils.setField(fileService, "fileRelativePath", "\\images\\");
    }

    @Test
    public void upload_happyPath() throws IOException {

        MultipartFile file = mockMultipartFile();
        when(fileUtils.filenameGenerator("a.jpg")).thenReturn("123.jpg");

        assertThat(fileService.upload(file)).isEqualTo("\\images\\"+"123.jpg");
    }

    private MultipartFile mockMultipartFile() throws IOException {

        FileInputStream fileInputStream = new FileInputStream("E:\\images\\"+"a.jpg");
        return new MockMultipartFile("a.jpg", "a.jpg", "multipart/form-data", fileInputStream);
    }
}
