package it.euris.stazioneconcordia.service;

import it.euris.stazioneconcordia.data.model.Card;
import it.euris.stazioneconcordia.data.model.Labels;
import it.euris.stazioneconcordia.exception.IdMustBeNullException;
import it.euris.stazioneconcordia.exception.IdMustNotBeNullException;
import it.euris.stazioneconcordia.repository.CardRepository;
import it.euris.stazioneconcordia.service.impl.CardServiceImpl;
import org.assertj.core.api.recursive.comparison.ComparingSnakeOrCamelCaseFields;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CardServiceImplTest {

    @Mock
    CardRepository cardRepository;

    @InjectMocks
    CardServiceImpl cardService;

    @Test
    void shouldReturnCardsWithLabels() {
        Card card1 = Card
                .builder()
                .id(1L)
                .labels(Labels.builder().id(10L).build())
                .build();
        Card card2 = Card
                .builder()
                .id(2L)
                .labels(Labels.builder().id(11L).build())
                .build();
        Card card3 = Card
                .builder()
                .id(3L)
                .labels(Labels.builder().id(12L).build())
                .build();

        List<Card> cards = List.of(card1, card2, card3);

        when(cardRepository.findAll()).thenReturn(cards);

        List<Card> cardsWithPriority = cardService.findByLabels(12L);

        assertThat(cardsWithPriority)
                .hasSize(1)
                .first()
                .usingRecursiveComparison()
                .withIntrospectionStrategy(new ComparingSnakeOrCamelCaseFields())
                .isEqualTo(card3);
    }

    @Test
    void shouldReturnCardsWithExpirationDateInLast5Days() {
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

        List<Card> cards = List.of(card1, card2, card3);

        when(cardRepository.findAll()).thenReturn(cards);

        List<Card> cardsNearExipiration = cardService.findAllCardsWhitExpirationDateInLast5Days();

        assertThat(cardsNearExipiration)
                .hasSize(2)
                .first()
                .usingRecursiveComparison()
                .withIntrospectionStrategy(new ComparingSnakeOrCamelCaseFields())
                .isEqualTo(card3);
    }

    @Test
    void shouldReturnACard() {

        Card card = Card
                .builder()
                .id(1L)
                .name("Test name")
                .description("Test desc")
                .build();

        List<Card> cards = List.of(card);

        when(cardRepository.findAll()).thenReturn(cards);

        List<Card> returnedCourses = cardService.findAll();

        assertThat(returnedCourses)
                .hasSize(1)
                .first()
                .usingRecursiveComparison()
                .withIntrospectionStrategy(new ComparingSnakeOrCamelCaseFields())
                .isEqualTo(card);
    }

    @Test
    void shouldInsertACard() {

        Card card = Card
                .builder()
                .id(null)
                .name("Test name")
                .description("Test desc")
                .build();

        when(cardRepository.save(any())).thenReturn(card);

        Card returnedCard = cardService.insert(card);

        assertThat(returnedCard.getName())
                .isEqualTo(card.getName());
        assertThat(returnedCard.getDescription())
                .isEqualTo(card.getDescription());
    }

    @Test
    void shouldNotInsertAnyCard() {

        Card card = Card
                .builder()
                .id(1L)
                .name("Test name")
                .description("Test desc")
                .build();
        lenient().when(cardRepository.save(any())).thenReturn(card);

        assertThrows(IdMustBeNullException.class, () -> cardService.insert(card));

        assertThatThrownBy(() -> cardService.insert(card))
                .isInstanceOf(IdMustBeNullException.class);

    }

    @Test
    void shouldUpdateACard() {

        Card card = Card
                .builder()
                .id(1L)
                .name("Test name")
                .description("Test desc")
                .build();

        when(cardRepository.save(any())).thenReturn(card);

        Card returnedCard = cardService.update(card);
        assertThat(returnedCard.getName())
                .isEqualTo(card.getName());
        assertThat(returnedCard.getDescription())
                .isEqualTo(card.getDescription());
    }

    @Test
    void shouldNotUpdateAnyCard() {

        Card card = Card
                .builder()
                .id(null)
                .name("Test name")
                .description("Test desc")
                .build();
        lenient().when(cardRepository.save(any())).thenReturn(card);

        assertThatThrownBy(() -> cardService.update(card))
                .isInstanceOf(IdMustNotBeNullException.class);
    }

    @Test
    void shouldDeleteACard() {
        Long id = 12L;

        doNothing().when(cardRepository).deleteById(anyLong());
        when(cardRepository.findById(id)).thenReturn(Optional.empty());
        assertTrue(cardService.deleteById(id));
        Mockito.verify(cardRepository, times(1)).deleteById(id);
    }

    @Test
    void shouldGetACardById() {
        Long id = 1L;

        Card card = Card
                .builder()
                .id(1L)
                .name("Test name")
                .description("Test description")
                .build();
        lenient().when(cardRepository.findById(id)).thenReturn(Optional.ofNullable(card));

        assertThat(card).isNotNull();
        assertThat(card.getId()).isEqualTo(id);
        assertThat(card.getName()).isEqualTo("Test name");
    }
}