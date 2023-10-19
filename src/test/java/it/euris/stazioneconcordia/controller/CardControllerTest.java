package it.euris.stazioneconcordia.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.euris.stazioneconcordia.data.enums.Priority;
import it.euris.stazioneconcordia.data.model.Card;
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
                .lists(Lists.builder().id(1L).build())
                .build();
        List<Card> cards = List.of(card);

        when(cardService.findAll()).thenReturn(cards);

        mockMvc.perform(MockMvcRequestBuilders.get("/cards/v1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
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
                .lists(Lists.builder().id(1L).build())
                .build();
        Card card2 = Card
                .builder()
                .id(12L)
                .name("test name2")
                .description("test description")
                .lists(Lists.builder().id(1L).build())
                .build();
        List<Card> cards = List.of(card1,card2);

        when(cardService.findAll()).thenReturn(cards);

        mockMvc.perform(MockMvcRequestBuilders.get("/cards/v1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
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
                .lists(Lists.builder().id(1L).build())
                .build();

        when(cardService.insert(any())).thenReturn(card);


        mockMvc.perform(post("/cards/v1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(card.toDto())))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
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
                .lists(Lists.builder().id(1L).build())
                .build();

        when(cardService.update(any())).thenReturn(card);


        mockMvc.perform(put("/cards/v1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(card.toDto())))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
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
                .lists(Lists.builder().id(1L).build())
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
                .lists(Lists.builder().id(1L).build())
                .build();
        Long id = 11L;
        when(cardService.findById(id)).thenReturn(card);

        mockMvc.perform(MockMvcRequestBuilders.get("/cards/v1/11")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(card.toDto())))
                .andDo(print())
                .andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.name").value(card.getName()))
                .andExpect(jsonPath("$.description").value(card.getDescription()));


    } @Test
    void shouldFindAllCardByExpirationDateInLast5Days() throws Exception {

        Card card1 = Card
                .builder()
                .id(1L)
                .lists(Lists.builder().id(1L).build())
                .expirationDate(LocalDateTime.now().minusDays(4L))
                .build();
        Card card2 = Card
                .builder()
                .id(2L)
                .lists(Lists.builder().id(1L).build())
                .expirationDate(LocalDateTime.now().minusDays(8L))
                .build();
        Card card3 = Card
                .builder()
                .id(3L)
                .lists(Lists.builder().id(1L).build())
                .expirationDate(LocalDateTime.now().minusDays(2L))
                .build();
        List<Card> cards = List.of(card1, card3);

        when(cardService.findAllCardsWhitExpirationDateInLast5Days()).thenReturn(cards);

        mockMvc.perform(MockMvcRequestBuilders.get("/cards/v1/expiration-date")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Optional.of(cards))))
                .andDo(print())
                .andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2));



    }
    @Test
    void shouldFindACardByMediumPriority() throws Exception {
        Card card1 = Card
                .builder()
                .id(1L)
                .priority(Priority.MEDIUM)
                .name("test name")
                .description("test description")
                .lists(Lists.builder().id(1L).build())
                .build();
        Card card2 = Card
                .builder()
                .id(2L)
                .priority(Priority.HIGH)
                .name("test name")
                .description("test description")
                .lists(Lists.builder().id(1L).build())
                .build();
        Card card = Card
                .builder()
                .id(3L)
                .priority(Priority.LOW)
                .name("test name")
                .description("test description")
                .lists(Lists.builder().id(1L).build())
                .build();

        when(cardService.findByPriority(Priority.MEDIUM)).thenReturn(List.of(card1));

        mockMvc.perform(MockMvcRequestBuilders.get("/cards/v1/priority/MEDIUM")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(card1.toDto())))
                .andDo(print())
                .andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(card1.getId()));


    }

}