package com.lxc.controller;

import com.lxc.constants.AddResultEnum;
import com.lxc.entity.User;
import com.lxc.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setSuffix(".html");
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .setViewResolvers(resolver).build();
    }

    @Test
    public void unauthorized_happyPath() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/user/403"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("403"))
                .andReturn();
    }

    @Test
    public void findAll_happyPath() throws Exception {

        List mockedList = mock(List.class);
        when(userService.findAll()).thenReturn(mockedList);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/user/users"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attribute("users", mockedList))
                .andExpect(MockMvcResultMatchers.view().name("userManagement/users.html"))
                .andReturn();
        verify(userService).findAll();
    }

    @Test
    public void toAddUser_happyPath() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/user/add"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("userManagement/addUser.html"))
                .andReturn();
    }

    @Test
    public void addUser_happyPath() throws Exception {

        when(userService.addUser(any())).thenReturn(AddResultEnum.SUCCESS);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/user/add"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/user/users"))
                .andReturn();
    }

    @Test
    public void addUser_shouldFail_ifUsernameExists() throws Exception {

        when(userService.addUser(any())).thenReturn(AddResultEnum.FAIL);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/user/add"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("userManagement/addUser.html"))
                .andExpect(MockMvcResultMatchers.request().attribute("addUser", "新增用户失败，该用户名已存在！"))
                .andReturn();
    }

    @Test
    public void toUpdateProfile_happyPath() throws Exception {

        User mockedUser = mock(User.class);
        when(userService.findById(1)).thenReturn(mockedUser);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/user/updateProfile/{userId}", 1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attribute("user", mockedUser))
                .andExpect(MockMvcResultMatchers.view().name("user/profile.html"))
                .andReturn();
        verify(userService).findById(1);
    }

    @Test
    public void updateProfile_happyPath() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.put("/user/updateProfile"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("index"))
                .andReturn();
    }

    @Test
    public void delete_happyPath() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/user/delete/{userId}", 1))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/user/users"))
                .andReturn();
        verify(userService).deleteById(1);
    }

    @Test
    public void changeRoleToAdmin_happyPath() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/user/changeRoleToAdmin/{userId}", 1))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/user/users"))
                .andReturn();
        verify(userService).changeRoleToAdmin(1);
    }

    @Test
    public void changeRoleToCustomer_happyPath() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/user/changeRoleToCustomer/{userId}", 1))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/user/users"))
                .andReturn();
        verify(userService).changeRoleToCustomer(1);
    }
}
