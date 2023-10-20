package it.euris.stazioneconcordia.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.euris.stazioneconcordia.data.dto.CardDTO;
import it.euris.stazioneconcordia.data.dto.CommentDTO;
import it.euris.stazioneconcordia.data.model.Card;
import it.euris.stazioneconcordia.data.model.Comment;
import it.euris.stazioneconcordia.data.model.Lists;
import it.euris.stazioneconcordia.exception.IdMustBeNullException;
import it.euris.stazioneconcordia.service.CommentService;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class CommentControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CommentService commentService;


    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void shouldReturnAllComments() throws Exception {

        List<CommentDTO> commentsDTO = generateCommentList();
        List<Comment> comments = commentsDTO.stream().map(CommentDTO::toModel).toList();

        when(commentService.findAll()).thenReturn(comments);
        mockMvc.perform(MockMvcRequestBuilders.get("/comments/v1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].id").value(comments.get(0).getId()))
                .andExpect(jsonPath("$[1].id").value(comments.get(1).getId()))
                .andExpect(jsonPath("$[2].id").value(comments.get(2).getId()));

    }


    @Test
    public void shouldSaveCommentWithIdNull() throws Exception {
        CommentDTO commentDTO = generateCommentDTO();
        Comment savedComment = commentDTO.toModel();

        when(commentService.insert(any())).thenReturn(savedComment);

        mockMvc.perform(MockMvcRequestBuilders.post("/comments/v1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(commentDTO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id").value(nullValue()))
                .andExpect(jsonPath("$.commentBody").value(savedComment.getCommentBody()));
    }

    @Test
    void shouldThrowExceptionWhenSavingCommentWithIdNotNull() throws Exception {
        CommentDTO commentDTO = generateCommentDTOWithId();
        Comment comment = commentDTO.toModel();

        when(commentService.insert(comment)).thenThrow(new IdMustBeNullException());

        assertThrows(ServletException.class, () -> {
            mockMvc.perform(MockMvcRequestBuilders.post("/comments/v1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(commentDTO)))
                    .andDo(print());
        });
    }

    @Test
    void shouldUpdateACommentWithIdNotNull() throws Exception {
        CommentDTO commentDTO = generateCommentDTOWithId();
        Comment updatedComment = commentDTO.toModel();
        updatedComment.setCommentBody("New comment");

        when(commentService.update(any())).thenReturn(updatedComment);

        mockMvc.perform(MockMvcRequestBuilders.put("/comments/v1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(commentDTO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id").value(updatedComment.getId()))
                .andExpect(jsonPath("$.commentBody").value("New comment"));

    }

    @Test
    void shouldDeleteCommentById() throws Exception {

        Long idComment = 1L;

        when(commentService.deleteById(idComment)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/comments/v1/{id}", idComment))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnCommentById() throws Exception {
        List<CommentDTO> commentsDto = generateCommentList();
        List<Comment> comments = commentsDto.stream().map(CommentDTO::toModel).toList();

        when(commentService.findById(comments.get(0).getId())).thenReturn(comments.get(0));
        Long id = comments.get(0).getId();

        mockMvc.perform(MockMvcRequestBuilders.get("/comments/v1/{id}", id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id").value(comments.get(0).getId()));
    }

    @Test
    void shouldGetLastCommentFromCard() throws Exception {

        Card testCard = Card.builder()
                .id(1L)
                .lists(Lists.builder().id(1L).build())
                .name("test card")
                .build();
        CardDTO cardDTO = testCard.toDto();
        Long id = cardDTO.toModel().getId();


        CommentDTO testComment1 = CommentDTO.builder()
                .id("1")
                .idCard(cardDTO.getId())
                .date("2023-10-18T12:12:12")
                .commentBody("First comment")
                .build();
        CommentDTO testComment2 = CommentDTO.builder()
                .id("1")
                .idCard(cardDTO.getId())
                .date("2000-12-10T12:12:12")
                .commentBody("Last comment")
                .build();
        List<CommentDTO> commentsDto = List.of(testComment1, testComment2);
        List<Comment> comments = commentsDto.stream().map(CommentDTO::toModel).toList();

        when(commentService.getLastComment(any())).thenReturn(comments.get(0));

        mockMvc.perform(MockMvcRequestBuilders.get("/comments/v1/last-comment/{card-id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testComment1.getId()));
    }


    private List<CommentDTO> generateCommentList() {
        List<CommentDTO> comments = new ArrayList<>();
        CommentDTO commentDTO1 = CommentDTO
                .builder()
                .id("1")
                .idCard("1")
                .build();

        CommentDTO commentDTO2 = CommentDTO
                .builder()
                .id("2")
                .idCard("2")
                .build();

        CommentDTO commentDTO3 = CommentDTO
                .builder()
                .id("3")
                .idCard("3")
                .build();
        comments.add(commentDTO1);
        comments.add(commentDTO2);
        comments.add(commentDTO3);
        return comments;
    }

    private CommentDTO generateCommentDTO() {
        return CommentDTO
                .builder()
                .date("2023-10-19T12:00:00")
                .idUser("1")
                .idCard("1")
                .commentBody("comment body")
                .build();
    }

    private CommentDTO generateCommentDTOWithId() {
        return CommentDTO
                .builder()
                .id("1")
                .date("2023-10-19T12:00:00")
                .idUser("1")
                .idCard("1")
                .commentBody("comment body")
                .build();

    }
}