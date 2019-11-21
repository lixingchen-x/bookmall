package com.lxc.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class FileUtilsTest {

    @InjectMocks
    private FileUtils fileUtils;

    @Mock
    private UUIDGenerator uuidGenerator;

    @Test
    public void generateRandomFilename_happyPath() {

        when(uuidGenerator.generate()).thenReturn("123");

        assertThat(fileUtils.generateRandomFilename("abc.jpg")).isEqualTo("123.jpg");
    }

    @Test
    public void write_happyPath() throws IOException {

        MultipartFile file = mock(MultipartFile.class);
        File destination = mock(File.class);

        fileUtils.write(file, destination);

        verify(file).transferTo(destination);
    }

    @Test(expected = IOException.class)
    public void write_shouldThrowException_ifIOFailed() throws IOException {

        MultipartFile file = mock(MultipartFile.class);
        File destination = mock(File.class);
        doThrow(IOException.class).when(file).transferTo(destination);

        fileUtils.write(file, destination);
    }
}
