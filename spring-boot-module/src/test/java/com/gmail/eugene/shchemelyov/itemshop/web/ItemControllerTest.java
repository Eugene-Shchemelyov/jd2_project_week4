package com.gmail.eugene.shchemelyov.itemshop.web;

import com.gmail.eugene.shchemelyov.itemshop.repository.model.enums.ItemStatusEnum;
import com.gmail.eugene.shchemelyov.itemshop.service.ItemService;
import com.gmail.eugene.shchemelyov.itemshop.service.model.ItemDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ItemControllerTest {
    private MockMvc mockMvc;
    private ItemController itemController;
    @Mock
    private ItemService itemService;

    private ItemDTO itemDTO = new ItemDTO(1L, "ItemOne", ItemStatusEnum.READY, false);
    private List<ItemDTO> itemsDTO = asList(itemDTO, itemDTO);

    @Before
    public void initialize() {
        itemController = new ItemController(itemService);
        mockMvc = MockMvcBuilders.standaloneSetup(itemController).build();
    }

    @Test
    @WithMockUser(roles = "ROLE_CUSTOMER")
    public void shouldReturnListItems() throws Exception {
        when(itemService.getItems()).thenReturn(itemsDTO);
        this.mockMvc.perform(get("/private/items.html"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("items", equalTo(itemsDTO)))
                .andExpect(forwardedUrl("items"));
    }
}