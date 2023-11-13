package it.euris.stazioneconcordia.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.euris.stazioneconcordia.data.model.Card;
import it.euris.stazioneconcordia.data.model.Labels;
import it.euris.stazioneconcordia.data.model.Lists;
import it.euris.stazioneconcordia.service.CardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CardControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    CardService cardService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldGetOneCard() throws Exception {

        Card card = Card
                .builder()
                .id(1L)
                .name("test name")
                .description("test description")
                .list(Lists.builder().id(1L).build())
                .labels(Labels.builder().id(1L).build())
                .build();
        List<Card> cards = List.of(card);

        when(cardService.findAll()).thenReturn(cards);

        mockMvc.perform(MockMvcRequestBuilders.get("/cards/v1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value(card.getName()))
                .andExpect(jsonPath("$[0].description").value(card.getDescription()));
    }

    @Test
    void shouldGetAllCard() throws Exception {

        Card card1 = Card
                .builder()
                .id(1L)
                .name("test name1")
                .description("test description")
                .list(Lists.builder().id(1L).build())
                .labels(Labels.builder().id(1L).build())
                .build();
        Card card2 = Card
                .builder()
                .id(2L)
                .name("test name2")
                .description("test description")
                .list(Lists.builder().id(2L).build())
                .labels(Labels.builder().id(1L).build())
                .build();
        List<Card> cards = List.of(card1, card2);

        when(cardService.findAll()).thenReturn(cards);

        mockMvc.perform(MockMvcRequestBuilders.get("/cards/v1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void shouldInsertACard() throws Exception {
        Card card = Card
                .builder()
                .id(1L)
                .name("test name")
                .description("test description")
                .list(Lists.builder().id(1L).build())
                .labels(Labels.builder().id(1L).build())
                .build();

        when(cardService.insert(any())).thenReturn(card);


        mockMvc.perform(post("/cards/v1")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsString(card.toDto())))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.name").value(card.getName()))
                .andExpect(jsonPath("$.description").value(card.getDescription()));
    }

    @Test
    void shouldUpdateACard() throws Exception {
        Card card = Card
                .builder()
                .id(1L)
                .name("test name")
                .description("test description")
                .list(Lists.builder().id(1L).build())
                .labels(Labels.builder().id(1L).build())
                .build();

        when(cardService.update(any())).thenReturn(card);


        mockMvc.perform(put("/cards/v1")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsString(card.toDto())))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.name").value(card.getName()))
                .andExpect(jsonPath("$.description").value(card.getDescription()));
    }

    @Test
    void shouldDeleteACardById() throws Exception {
        Card card = Card
                .builder()
                .id(11L)
                .name("test name")
                .description("test description")
                .list(Lists.builder().id(1L).build())
                .build();


        mockMvc.perform(MockMvcRequestBuilders.delete("/cards/v1/11")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Optional.empty())))
                .andDo(print())
                .andExpect(status().isOk());


    }

    @Test
    void shouldFindACardById() throws Exception {
        Card card = Card
                .builder()
                .id(11L)
                .name("test name")
                .description("test description")
                .list(Lists.builder().id(1L).build())
                .labels(Labels.builder().id(1L).build())
                .build();
        Long id = 11L;
        when(cardService.findById(id)).thenReturn(card);

        mockMvc.perform(MockMvcRequestBuilders.get("/cards/v1/11")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsString(card.toDto())))
                .andDo(print())
                .andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.name").value(card.getName()))
                .andExpect(jsonPath("$.description").value(card.getDescription()));


    }

    @Test
    void shouldFindAllCardByExpirationDateInLast5Days() throws Exception {

        Card card1 = Card
                .builder()
                .id(1L)
                .list(Lists.builder().id(1L).build())
                .labels(Labels.builder().id(1L).build())
                .expirationDate(LocalDateTime.now().minusDays(4L))
                .build();
        Card card2 = Card
                .builder()
                .id(2L)
                .list(Lists.builder().id(2L).build())
                .labels(Labels.builder().id(1L).build())
                .expirationDate(LocalDateTime.now().minusDays(8L))
                .build();
        Card card3 = Card
                .builder()
                .id(3L)
                .list(Lists.builder().id(3L).build())
                .labels(Labels.builder().id(1L).build())
                .expirationDate(LocalDateTime.now().minusDays(2L))
                .build();
        List<Card> cards = List.of(card1, card3);

        when(cardService.findAllCardsWhitExpirationDateInLast5Days()).thenReturn(cards);

        mockMvc.perform(MockMvcRequestBuilders.get("/cards/v1/expiration-date")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsString(Optional.of(cards))))
                .andDo(print())
                .andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2));


    }

    @Test
    void shouldFindACardByMediumPriority() throws Exception {
        Card card1 = Card
                .builder()
                .id(1L)
                .labels(Labels.builder().id(13L).build())
                .name("test name")
                .description("test description")
                .list(Lists.builder().id(1L).build())
                .build();
        Card card2 = Card
                .builder()
                .id(2L)
                .labels(Labels.builder().id(14L).build())
                .name("test name")
                .description("test description")
                .list(Lists.builder().id(1L).build())
                .build();
        Card card = Card
                .builder()
                .id(3L)
                .labels(Labels.builder().id(15L).build())
                .name("test name")
                .description("test description")
                .list(Lists.builder().id(1L).build())
                .build();

        when(cardService.findByLabels(13L)).thenReturn(List.of(card1));

        mockMvc.perform(MockMvcRequestBuilders.get("/cards/v1/labels/13")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsString(card1.toDto())))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(card1.getId()));


    }

}