package it.euris.stazioneconcordia.service;

import it.euris.stazioneconcordia.data.enums.Priority;
import it.euris.stazioneconcordia.data.model.Card;
import it.euris.stazioneconcordia.repository.CardRepository;
import it.euris.stazioneconcordia.service.impl.CardServiceImpl;
import org.assertj.core.api.recursive.comparison.ComparingSnakeOrCamelCaseFields;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CardServiceImplTest {

    @Mock
    CardRepository cardRepository;

    @InjectMocks
    CardServiceImpl cardService;

    @Test
    void shouldReturnCardsWithHighPriority(){
        Card card1 = Card
                .builder()
                .id(1L)
                .priority(Priority.HIGH)
                .build();
        Card card2 = Card
                .builder()
                .id(2L)
                .priority(Priority.MEDIUM)
                .build();
        Card card3 = Card
                .builder()
                .id(3L)
                .priority(Priority.HIGH)
                .build();

        List<Card> cards = List.of(card1,card2,card3);

        when(cardRepository.findAll()).thenReturn(cards);

        List<Card> cardsWithPriority = cardService.findByPriority(Priority.HIGH);

        assertThat(cardsWithPriority)
                .hasSize(2)
                .first()
                .usingRecursiveComparison()
                .withIntrospectionStrategy(new ComparingSnakeOrCamelCaseFields())
                .isEqualTo(card1);
    }

    @Test
    void shouldReturnCardsWithExpirationDateInLast5Days(){
        Card card1 = Card
                .builder()
                .id(1L)
                .expirationDate(LocalDateTime.now().minusDays(4L))
                .build();
        Card card2 = Card
                .builder()
                .id(2L)
                .expirationDate(LocalDateTime.now().minusDays(8L))
                .build();
        Card card3 = Card
                .builder()
                .id(3L)
                .expirationDate(LocalDateTime.now().minusDays(2L))
                .build();

        List<Card> cards = List.of(card1,card2,card3);

        when(cardRepository.findAll()).thenReturn(cards);

        List<Card> cardsNearExipiration = cardService.findAllCardsWithExpirationDateInLast5Days();

        assertThat(cardsNearExipiration)
                .hasSize(2)
                .first()
                .usingRecursiveComparison()
                .withIntrospectionStrategy(new ComparingSnakeOrCamelCaseFields())
                .isEqualTo(card3);
    }
}