package it.euris.stazioneconcordia.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.euris.stazioneconcordia.data.model.Board;
import it.euris.stazioneconcordia.data.model.Lists;
import it.euris.stazioneconcordia.service.ListsService;
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
class ListsControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    ListsService listsService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void shouldGetOneList() throws Exception {

        Lists list = Lists
                .builder()
                .id(1L)
                .name("Test name")
                .position(1L)
                .board(Board.builder().id(1L).build())
                .build();

        List<Lists> lists = List.of(list);

        when(listsService.findAll()).thenReturn(lists);

        mockMvc.perform(MockMvcRequestBuilders.get("/lists/v1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Test name"))
                .andExpect(jsonPath("$[0].position").value(1L));

    }

    @Test
    public void shouldSaveAList() throws Exception {

        Lists list = Lists
                .builder()
                .id(1L)
                .name("Test name")
                .position(1L)
                .board(Board.builder().id(1L).build())
                .build();

        when(listsService.insert(any())).thenReturn(list);

        mockMvc.perform(MockMvcRequestBuilders.post("/lists/v1")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsString(list.toDto())))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void shouldUpdateAList() throws Exception {
        Lists list = Lists
                .builder()
                .id(1L)
                .name("Test name")
                .position(1L)
                .board(Board.builder().id(1L).build())
                .build();

        when(listsService.update(any())).thenReturn(list);

        mockMvc.perform(MockMvcRequestBuilders.put("/lists/v1")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsString(list.toDto())))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void shouldDeleteAListById() throws Exception {
        Long idList = 1L;

        when(listsService.deleteById(idList)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/lists/v1/{id}", idList))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetListById() throws Exception {
        Long idList = 1L;

        Lists list = Lists
                .builder()
                .id(1L)
                .name("Test name")
                .position(1L)
                .board(Board.builder().id(1L).build())
                .build();

        when(listsService.findById(idList)).thenReturn(list);

        mockMvc.perform(MockMvcRequestBuilders.get("/lists/v1/{id}", idList))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }
}