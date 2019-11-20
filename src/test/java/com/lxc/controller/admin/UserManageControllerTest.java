package com.lxc.controller.admin;

import com.lxc.constants.ResultEnum;
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
public class UserManageControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private UserManageController userManageController;

    @Mock
    private UserService userService;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setSuffix(".html");
        this.mockMvc = MockMvcBuilders.standaloneSetup(userManageController)
                .setViewResolvers(resolver).build();
    }

    @Test
    public void findAll_happyPath() throws Exception {

        List mockedList = mock(List.class);
        when(userService.findAll()).thenReturn(mockedList);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/admin/user/users"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attribute("users", mockedList))
                .andExpect(MockMvcResultMatchers.view().name("userManagement/users.html"))
                .andReturn();
        verify(userService).findAll();
    }

    @Test
    public void toAddUser_happyPath() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/admin/user/add"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("userManagement/addUser.html"))
                .andReturn();
    }

    @Test
    public void addUser_happyPath() throws Exception {

        when(userService.addUser(any())).thenReturn(ResultEnum.SUCCESS);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/admin/user/add"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/admin/user/users"))
                .andReturn();
    }

    @Test
    public void addUser_shouldFail_ifUsernameExists() throws Exception {

        when(userService.addUser(any())).thenReturn(ResultEnum.FAIL);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/admin/user/add"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("userManagement/addUser.html"))
                .andExpect(MockMvcResultMatchers.request().attribute("addUser", "新增用户失败，该用户名已存在！"))
                .andReturn();
    }

    @Test
    public void delete_happyPath() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/admin/user/delete/{userId}", 1))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/admin/user/users"))
                .andReturn();
        verify(userService).deleteById(1);
    }

    @Test
    public void changeRoleToAdmin_happyPath() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/admin/user/changeRoleToAdmin/{userId}", 1))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/admin/user/users"))
                .andReturn();
        verify(userService).changeRoleToAdmin(1);
    }

    @Test
    public void changeRoleToCustomer_happyPath() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/admin/user/changeRoleToCustomer/{userId}", 1))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/admin/user/users"))
                .andReturn();
        verify(userService).changeRoleToCustomer(1);
    }
}
