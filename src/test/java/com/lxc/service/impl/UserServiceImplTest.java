package com.lxc.service.impl;

import com.lxc.constants.AddResultEnum;
import com.lxc.entity.Role;
import com.lxc.entity.User;
import com.lxc.helper.CartManager;
import com.lxc.helper.UserManager;
import com.lxc.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private CartManager cartManager;

    @Mock
    private UserManager userManager;

    @Test
    public void update_happyPath() {

        User newUser = User.builder().id(1).password("123456").email("abc@abc").build();
        User oldUser = new User();
        when(userService.findById(1)).thenReturn(oldUser);

        userService.update(newUser);

        verify(userRepository).saveAndFlush(oldUser);
        assertThat(oldUser.getPassword()).isEqualTo("123456");
        assertThat(oldUser.getEmail()).isEqualTo("abc@abc");
    }

    @Test
    public void update_shouldDoNothing_ifUserDoesNotExist() {

        User user = User.builder().id(1).build();
        when(userService.findById(1)).thenReturn(null);

        userService.update(user);

        verify(userRepository, never()).saveAndFlush(null);
    }

    @Test
    public void deleteById_happyPath() {

        when(userService.findById(1)).thenReturn(mock(User.class));

        userService.deleteById(1);

        verify(userRepository).deleteById(1);
    }

    @Test
    public void deleteById_shouldDoNothing_ifUserDoesNotExist() {

        when(userService.findById(1)).thenReturn(null);

        userService.deleteById(1);

        verify(userRepository, never()).deleteById(1);
    }

    @Test
    public void findById_happyPath() {

        User expected = new User();
        when(userService.findById(1)).thenReturn(expected);

        assertThat(userService.findById(1)).isEqualTo(expected);
    }

    @Test
    public void findById_shouldReturnNull_ifUserDoesNotExist() {

        when(userRepository.getOne(1)).thenThrow(EntityNotFoundException.class);

        assertThat(userService.findById(1)).isNull();
    }

    @Test
    public void saveAsCustomer_happyPath() {

        User user = new User();

        userService.saveAsCustomer(user);

        verify(userRepository).saveAndFlush(user);
        assertThat(user.getRoleName()).isEqualTo(Role.CUSTOMER_ROLE_CODE);
    }

    @Test
    public void addUser_happyPath() {

        User user = User.builder().username("a").build();
        when(userRepository.findByUsername("a")).thenReturn(null);

        assertThat(userService.addUser(user)).isEqualTo(AddResultEnum.SUCCESS);
        verify(userRepository).saveAndFlush(user);
    }

    @Test
    public void addUser_shouldFail_ifUsernameExists() {

        User user = User.builder().username("a").build();
        when(userRepository.findByUsername("a")).thenReturn(user);

        assertThat(userService.addUser(user)).isEqualTo(AddResultEnum.FAIL);
    }

    @Test
    public void findByUsername_happyPath() {

        User user = User.builder().username("abc").build();
        when(userRepository.findByUsername("abc")).thenReturn(user);

        assertThat(userService.findByUsername("abc")).isEqualTo(user);
    }

    @Test
    public void findByUsername_shouldReturnNull_ifUsernameDoesNotExist() {

        when(userRepository.findByUsername("abc")).thenReturn(null);

        assertThat(userService.findByUsername("abc")).isNull();
    }

    @Test
    public void findAll_happyPath() {

        List<User> users = new ArrayList<>();
        when(userRepository.findAll()).thenReturn(users);

        assertThat(userService.findAll()).isEqualTo(users);
    }

    @Test
    public void changeRoleToAdmin_happyPath() {

        User user = new User();
        when(userService.findById(1)).thenReturn(user);

        userService.changeRoleToAdmin(1);

        assertThat(userRepository.getOne(1).getRoleName()).isEqualTo("ADMIN");
        verify(userRepository).saveAndFlush(user);
    }

    @Test
    public void changeRoleToAdmin_shouldDoNothing_ifUserDoesNotExist() {

        when(userService.findById(1)).thenReturn(null);

        userService.changeRoleToAdmin(1);

        verify(userRepository, never()).saveAndFlush(null);
    }

    @Test
    public void changeRoleToCustomer_happyPath() {

        User user = new User();
        when(userService.findById(1)).thenReturn(user);

        userService.changeRoleToCustomer(1);

        assertThat(userRepository.getOne(1).getRoleName()).isEqualTo("CUSTOMER");
        verify(userRepository).saveAndFlush(user);
    }

    @Test
    public void changeRoleToCustomer_shouldDoNothing_ifUserDoesNotExist() {

        when(userService.findById(1)).thenReturn(null);

        userService.changeRoleToCustomer(1);

        verify(userRepository, never()).saveAndFlush(null);
    }

    @Test
    public void getCompleteUser_happyPath() {

        User user = User.builder().id(1).username("a").build();
        when(userRepository.findByUsername("a")).thenReturn(user);

        assertThat(userService.getCompleteUser(user)).isEqualTo(user);

        verify(userManager).login(user);
        verify(cartManager).initCart();
    }

    @Test
    public void getCompleteUser_shouldReturnNull_ifUserDoesNotExist() {

        User user = User.builder().id(1).username("a").build();
        when(userRepository.findByUsername("a")).thenReturn(null);

        assertThat(userService.getCompleteUser(user)).isNull();
    }
}
