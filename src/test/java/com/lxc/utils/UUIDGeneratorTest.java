package com.lxc.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class UUIDGeneratorTest {

    @InjectMocks
    private UUIDGenerator uuidGenerator;

    @Test
    public void generate_happyPath() {

        assertThat(uuidGenerator.generate()).isNotEqualTo(uuidGenerator.generate());
    }
}
