package com.lxc.service.impl;

import com.lxc.constants.AddResults;
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

        User newUser = User.builder().id(1).build();
        User oldUser = mock(User.class);
        when(userService.findById(1)).thenReturn(oldUser);

        userService.update(newUser);

        verify(userRepository).saveAndFlush(oldUser);
    }

    @Test
    public void update_shouldThrowException_ifUserDoesNotExist() {

        User user = User.builder().id(1).build();
        when(userService.findById(1)).thenThrow(EntityNotFoundException.class);

        try {
            userService.update(user);
        } catch (EntityNotFoundException e) {
            assertThat(e.getMessage()).isEqualTo("USER_DOES_NOT_EXIST");
        }
    }

    @Test
    public void deleteById_happyPath() {

        userService.deleteById(1);

        verify(userRepository).deleteById(1);
    }

    @Test
    public void deleteById_shouldThrowException_ifUserDoesNotExist() {

        when(userService.findById(1)).thenThrow(EntityNotFoundException.class);

        try {
            userService.deleteById(1);
        } catch (EntityNotFoundException e) {
            assertThat(e.getMessage()).isEqualTo("USER_DOES_NOT_EXIST");
        }
    }

    @Test
    public void findById_happyPath() {

        User expected = new User();
        when(userService.findById(1)).thenReturn(expected);

        User actual = userService.findById(1);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void findById_shouldThrowException_ifUserDoesNotExist() {

        when(userRepository.getOne(1)).thenThrow(EntityNotFoundException.class);

        try {
            userService.findById(1);
        } catch (EntityNotFoundException e) {
            assertThat(e.getMessage()).isEqualTo("USER_DOES_NOT_EXIST");
        }
    }

    @Test
    public void saveAsCustomer_happyPath() {

        User user = new User();

        userService.saveAsCustomer(user);

        verify(userRepository).saveAndFlush(user);
        assertThat(user.getRole().getName()).isEqualTo(Role.CUSTOMER_ROLE_CODE);
    }

    @Test
    public void addUser_happyPath() {

        User user = User.builder().username("a").build();
        when(userRepository.findByUsername("a")).thenReturn(null);

        assertThat(userService.addUser(user)).isEqualTo(AddResults.SUCCESS);
        verify(userRepository).saveAndFlush(user);
    }

    @Test
    public void addUser_shouldFail_ifUsernameExists() {

        User user = User.builder().username("a").build();
        when(userRepository.findByUsername("a")).thenReturn(user);

        assertThat(userService.addUser(user)).isEqualTo(AddResults.FAIL);
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

        assertThat(userRepository.getOne(1).getRole().getName()).isEqualTo("ADMIN");
    }

    @Test
    public void changeRoleToAdmin_shouldThrowException_ifUserDoesNotExist() {

        when(userService.findById(1)).thenThrow(EntityNotFoundException.class);

        try {
            userService.changeRoleToAdmin(1);
        } catch (EntityNotFoundException e) {
            assertThat(e.getMessage()).isEqualTo("USER_DOES_NOT_EXIST");
        }
    }

    @Test
    public void changeRoleToCustomer_happyPath() {

        User user = new User();
        when(userService.findById(1)).thenReturn(user);

        userService.changeRoleToCustomer(1);

        assertThat(userRepository.getOne(1).getRole().getName()).isEqualTo("CUSTOMER");
    }

    @Test
    public void changeRoleToCustomer_shouldThrowException_ifUserDoesNotExist() {

        when(userService.findById(1)).thenThrow(EntityNotFoundException.class);

        try {
            userService.changeRoleToCustomer(1);
        } catch (EntityNotFoundException e) {
            assertThat(e.getMessage()).isEqualTo("USER_DOES_NOT_EXIST");
        }
    }

    @Test
    public void getCompleteUser_happyPath() {

        User user = User.builder().id(1).username("a").build();
        when(userRepository.findByUsername("a")).thenReturn(user);

        assertThat(userService.getCompleteUser(user)).isEqualTo(user);

        verify(userManager).login(user);
        verify(cartManager).initCart();
    }
}
