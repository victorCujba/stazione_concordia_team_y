package it.euris.stazioneconcordia.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.euris.stazioneconcordia.data.model.Board;
import it.euris.stazioneconcordia.service.BoardService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class BoardControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    BoardService boardService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void shouldGetOneBoard() throws Exception {

        Board board = Board
                .builder()
                .id(1L)
                .name("Test name")
                .description("Test desc")
                .url("Test url")
                .build();

        List<Board> boards = List.of(board);

        when(boardService.findAll()).thenReturn(boards);

        mockMvc.perform(MockMvcRequestBuilders.get("/boards/v1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Test name"))
                .andExpect(jsonPath("$[0].description").value("Test desc"))
                .andExpect(jsonPath("$[0].url").value("Test url"));

    }

    @Test
    public void shouldSaveABoard() throws Exception {

        Board board = Board
                .builder()
                .name("Test name")
                .description("Test desc")
                .url("Test url")
                .build();

        when(boardService.insert(any())).thenReturn(board);

        mockMvc.perform(MockMvcRequestBuilders.post("/boards/v1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(board.toDto())))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    public void shouldUpdateABoard() throws Exception {
        Board board = Board
                .builder()
                .name("Test name")
                .description("Test desc")
                .url("Test url")
                .build();

        when(boardService.update(any())).thenReturn(board);

        mockMvc.perform(MockMvcRequestBuilders.put("/boards/v1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(board.toDto())))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    public void shouldDeleteABoardById() throws Exception {
        Long idBoard = 1L;

        when(boardService.deleteById(idBoard)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/boards/v1/{id}", idBoard))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetBoardById() throws Exception {
        Long idBoard = 1L;

        Board board = Board
                .builder()
                .id(idBoard)
                .build();

        when(boardService.findById(idBoard)).thenReturn(board);

        mockMvc.perform(MockMvcRequestBuilders.get("/boards/v1/{id}",idBoard))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }
}