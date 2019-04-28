package com.gmail.eugene.shchemelyov.itemshop.web;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class PublicControllerTest {
    private MockMvc mockMvc;
    private PublicController publicController;

    @Before
    public void initialize() {
        publicController = new PublicController();
        mockMvc = MockMvcBuilders.standaloneSetup(publicController).build();
    }

    @Test
    public void requestToAboutWithSuccessfullyProceed() throws Exception {
        this.mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("hello"));
    }

    @Test
    public void requestToLoginWithSuccessfullyProceed() throws Exception {
        this.mockMvc.perform(get("/login.html"))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("login"));
    }
}