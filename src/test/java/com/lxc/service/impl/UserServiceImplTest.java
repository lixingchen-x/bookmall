package com.lxc.service.impl;

import com.lxc.entity.User;
import com.lxc.repository.UserRepository;
import com.lxc.service.impl.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void update_happyPath() {

        User newUser = User.builder().id(1).build();
        User oldUser = mock(User.class);
        when(userRepository.getOne(1)).thenReturn(oldUser);
        userService.update(newUser);
        verify(userRepository).saveAndFlush(oldUser);
    }

    @Test
    public void update_shouldDoNothing_ifUserDoesNotExist() {

        User user = User.builder().id(1).build();
        when(userRepository.getOne(1)).thenThrow(EntityNotFoundException.class);
        userService.update(user);
        verify(userRepository, never()).saveAndFlush(any());
    }

    @Test
    public void deleteById_happyPath() {

        userService.deleteById(1);
        verify(userRepository).deleteById(1);
    }

    @Test
    public void deleteById_shouldDoNothing_ifUserDoesNotExist() {

        when(userRepository.getOne(1)).thenThrow(EntityNotFoundException.class);
        userService.deleteById(1);
        verify(userRepository, never()).deleteById(1);
    }

    @Test
    public void findById_happyPath() {

        User expected = new User();
        when(userRepository.getOne(1)).thenReturn(expected);
        User actual = userService.findById(1);
        assertThat(actual, is(expected));
    }

    @Test
    public void findById_shouldReturnNull_ifUserDoesNotExist() {

        when(userRepository.getOne(1)).thenThrow(EntityNotFoundException.class);
        assertThat(userService.findById(1), is(nullValue()));
    }

    @Test
    public void save_happyPath() {

        User user = new User();
        userService.save(user);
        verify(userRepository).saveAndFlush(user);
    }

    @Test
    public void findByUsername_happyPath() {

        User user = User.builder().username("abc").build();
        when(userRepository.findByUsername("abc")).thenReturn(user);
        assertThat(userService.findByUsername("abc"), is(user));
    }

    @Test
    public void findByUsername_shouldReturnNull_ifUsernameDoesNotExist() {

        when(userRepository.findByUsername("abc")).thenReturn(null);
        assertThat(userService.findByUsername("abc"), is(nullValue()));
    }

    @Test
    public void findAll_happyPath() {

        List<User> users = new ArrayList<>();
        when(userRepository.findAll()).thenReturn(users);
        assertThat(userService.findAll(), is(users));
    }

    @Test
    public void setAdmin_happyPath() {

        User user = new User();
        when(userRepository.getOne(1)).thenReturn(user);
        userService.setAdmin(1);
        assertThat(userRepository.getOne(1).getRole().getName(), equalTo("ADMIN"));
    }

    @Test
    public void setAdmin_shouldDoNothing_ifUserDoesNotExist() {

        when(userRepository.getOne(1)).thenThrow(EntityNotFoundException.class);
        userService.setAdmin(1);
        verify(userRepository, never()).saveAndFlush(any());
    }

    @Test
    public void setCustomer_happyPath() {

        User user = new User();
        when(userRepository.getOne(1)).thenReturn(user);
        userService.setCustomer(1);
        assertThat(userRepository.getOne(1).getRole().getName(), equalTo("CUSTOMER"));
    }

    @Test
    public void setCustomer_shouldDoNothing_ifUserDoesNotExist() {

        when(userRepository.getOne(1)).thenThrow(EntityNotFoundException.class);
        userService.setCustomer(1);
        verify(userRepository, never()).saveAndFlush(any());
    }
}
