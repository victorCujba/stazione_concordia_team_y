package it.euris.stazioneconcordia.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.euris.stazioneconcordia.data.model.Board;
import it.euris.stazioneconcordia.data.model.Lists;
import it.euris.stazioneconcordia.data.model.User;
import it.euris.stazioneconcordia.service.ListsService;
import it.euris.stazioneconcordia.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void shouldGetOneUser() throws Exception {

        User user = User
                .builder()
                .id("1")
                .fullName("Test name")
                .bio("Test bio")
                .avatarUrl("Test url")
                .email("test@testmail.com")
                .build();

        List<User> users = List.of(user);

        when(userService.findAll()).thenReturn(users);

        mockMvc.perform(MockMvcRequestBuilders.get("/users/v1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].fullName").value("Test name"))
                .andExpect(jsonPath("$[0].bio").value("Test bio"));

    }

    @Test
    public void shouldSaveAUser() throws Exception {

        User user = User
                .builder()
                .id("1")
                .fullName("Test name")
                .bio("Test bio")
                .avatarUrl("Test url")
                .email("test@testmail.com")
                .build();

        when(userService.insert(any())).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.post("/users/v1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user.toDto())))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    public void shouldUpdateAUser() throws Exception {
        User user = User
                .builder()
                .id("1")
                .fullName("Test name")
                .bio("Test bio")
                .avatarUrl("Test url")
                .email("test@testmail.com")
                .build();

        when(userService.update(any())).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.put("/users/v1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user.toDto())))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    public void shouldDeleteAUserById() throws Exception {
        String idUser = "1";

        when(userService.deleteById(idUser)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/users/v1/{id}", idUser))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetUserById() throws Exception {
        String idUser = "1";

        User user = User
                .builder()
                .id("1")
                .fullName("Test name")
                .bio("Test bio")
                .avatarUrl("Test url")
                .email("test@testmail.com")
                .build();

        when(userService.findById(idUser)).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.get("/users/v1/{id}",idUser))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }
}