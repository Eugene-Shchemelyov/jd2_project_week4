package com.gmail.eugene.shchemelyov.itemshop.web.app;

import com.gmail.eugene.shchemelyov.itemshop.service.UserService;
import com.gmail.eugene.shchemelyov.itemshop.service.model.UserDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserService.class)
@ActiveProfiles("test")
public class UserControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private UserDTO userDTO = new UserDTO(1L, "username", "password", "CUSTOMER", false);
    private List<UserDTO> usersDTO = asList(userDTO, userDTO);

    @Before
    public void init() {
        when(userService.getUsers()).thenReturn(usersDTO);

    }

    @Test
    @WithMockUser(roles = "ROLE_ADMINISTRATOR")
    public void requestForItemsIsSuccessfullyProcessedWithAvailableItemsList() throws Exception {
        this.mockMvc.perform(get("/private/users").accept(MediaType.parseMediaType("text/html;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(content().string(allOf(
                        containsString("username")))
                );
    }
}