package com.gmail.eugene.shchemelyov.itemshop.web;

import com.gmail.eugene.shchemelyov.itemshop.service.UserService;
import com.gmail.eugene.shchemelyov.itemshop.service.model.UserDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {
    private MockMvc mockMvc;
    private UserController userController;
    @Mock
    private UserService userService;

    private UserDTO userDTO = new UserDTO(1L, "username", "password", "CUSTOMER", false);
    private List<UserDTO> usersDTO = asList(userDTO, userDTO);

    @Before
    public void initialize() {
        userController = new UserController(userService);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    @WithMockUser(roles = "ROLE_ADMINISTRATOR")
    public void shouldReturnListUsers() throws Exception {
        when(userService.getUsers()).thenReturn(usersDTO);
        this.mockMvc.perform(get("/private/users.html"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("users", equalTo(usersDTO)))
                .andExpect(forwardedUrl("users"));
    }
}