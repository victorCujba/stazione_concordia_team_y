package it.euris.stazioneconcordia.service.impl;

import it.euris.stazioneconcordia.data.enums.Priority;
import it.euris.stazioneconcordia.data.model.Card;
import it.euris.stazioneconcordia.exception.IdMustBeNullException;
import it.euris.stazioneconcordia.exception.IdMustNotBeNullException;
import it.euris.stazioneconcordia.repository.CardRepository;
import it.euris.stazioneconcordia.service.CardService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@AllArgsConstructor
@Service
public class CardServiceImpl implements CardService {

    CardRepository cardRepository;

    @Override
    public List<Card> findAll() {
        return cardRepository.findAll();
    }

    @Override
    public List<Card> findByPriority(Priority priority) {

        List<Card> cards = cardRepository.findAll();
        List<Card> cardsWithPriority = cards.stream()
                .filter(card -> card.getPriority() == priority)
                .collect(Collectors.toList());

        return cardsWithPriority;


    }


    @Override
    public List<Card> findAllCardsWhitExpirationDateInLast5Days() {


        List<Card> cards = cardRepository.findAll();

        List<Card> cardsNearExpiration = cards
                .stream()
                .filter(Card -> Card.getExpirationDate().isAfter(LocalDateTime.now().minusDays(5L)))
                .collect(Collectors.toList());


        if (cardsNearExpiration.size() > 1) {
            return cardsNearExpiration.stream()
                    .sorted(Comparator.comparing(Card::getExpirationDate).reversed())
                    .collect(Collectors.toList());

        }
        return cardsNearExpiration;

    }

    @Override
    public Card insert(Card card) {
        if (card.getId() != null) {
            throw new IdMustBeNullException();
        }
        return cardRepository.save(card);
    }

    @Override
    public Card update(Card card) {
        if (card.getId() == null) {
            throw new IdMustNotBeNullException();
        }
        return cardRepository.save(card);
    }

    @Override
    public Boolean deleteById(Long idCard) {
        cardRepository.deleteById(idCard);
        return cardRepository.findById(idCard).isEmpty();
    }

    @Override
    public Card findById(Long idCard) {
        return cardRepository.findById(idCard).orElse(Card.builder().build());
    }
}
