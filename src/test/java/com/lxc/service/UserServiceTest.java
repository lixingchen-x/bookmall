package com.lxc.service;

import com.lxc.entity.User;
import com.lxc.repository.UserRepository;
import com.lxc.service.impl.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void update_happyPath() {

        User newUser = new User();
        User oldUser = new User();
        when(userRepository.getOne(newUser.getId())).thenReturn(oldUser);
        userService.update(newUser);
        verify(userRepository).saveAndFlush(oldUser);
    }

    @Test
    public void deleteById_happyPath() {

        userService.deleteById(1);
        verify(userRepository).deleteById(1);
    }

    @Test
    public void findById_happyPath() {

        User expected = new User();
        when(userRepository.getOne(1)).thenReturn(expected);
        User actual = userService.findById(1);
        assertSame(expected, actual);
    }

    @Test
    public void findById_shouldBeNull_ifUserIdDoseNotExist() {

        when(userRepository.getOne(1)).thenReturn(null);
        assertNull(userService.findById(1));
    }

    @Test
    public void save_happyPath() {

        User user = new User();
        userService.save(user);
        verify(userRepository).saveAndFlush(user);
    }

    @Test
    public void findByUsername_happyPath() {

        userService.findByUsername("abc");
        verify(userRepository).findByUsername("abc");
    }

    @Test
    public void findByUsername_shouldBeNull_ifUsernameDoseNotExist() {

        when(userRepository.findByUsername("abc")).thenReturn(null);
        assertNull(userService.findByUsername("abc"));
    }

    @Test
    public void findAll_happyPath() {

        userService.findAll();
        verify(userRepository).findAll();
    }

    @Test
    public void setRole_happyPath() {

        User user = new User();
        when(userRepository.getOne(1)).thenReturn(user);
        userService.setRole(1, "anyRole");
        assertEquals("anyRole", userRepository.getOne(1).getRole().getName());
    }
}
