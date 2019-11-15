package com.lxc.service.impl;

import com.lxc.utils.FileUtils;
import com.lxc.utils.FilenameGenerator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({FileUtils.class, FilenameGenerator.class})
@PowerMockIgnore("javax.management.*")
public class FileServiceImplTest {

    @InjectMocks
    private FileServiceImpl fileService;

    @Before
    public void setUp() {

        PowerMockito.mockStatic(FileUtils.class);
        PowerMockito.mockStatic(FilenameGenerator.class);
        ReflectionTestUtils.setField(fileService, "filePath", "E:\\images\\");
    }

    @Test
    public void uploadImg_happyPath() throws Exception {

        MultipartFile file = PowerMockito.mock(MultipartFile.class);
        PowerMockito.when(file.getOriginalFilename()).thenReturn("a.jpg");
        PowerMockito.when(FilenameGenerator.generate("a.jpg")).thenReturn("123.jpg");
        File destination = mockFile();
        PowerMockito.when(FileUtils.writeSuccess(file, destination)).thenReturn(true);

        assertThat(fileService.uploadImg(file)).isEqualTo("E:\\images\\"+"123.jpg");
    }

    private File mockFile() throws Exception {

        File file = PowerMockito.mock(File.class);
        PowerMockito.whenNew(File.class).withParameterTypes(java.lang.String.class, java.lang.String.class)
                .withArguments("E:\\images\\", "a.jpg").thenReturn(file);
        return file;
    }
}
