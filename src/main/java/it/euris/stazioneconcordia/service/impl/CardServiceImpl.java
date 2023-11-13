package it.euris.stazioneconcordia.service.impl;

import it.euris.stazioneconcordia.data.model.Card;
import it.euris.stazioneconcordia.data.model.Labels;
import it.euris.stazioneconcordia.exception.IdMustBeNullException;
import it.euris.stazioneconcordia.exception.IdMustNotBeNullException;
import it.euris.stazioneconcordia.repository.CardRepository;
import it.euris.stazioneconcordia.repository.LabelsRepository;
import it.euris.stazioneconcordia.repository.ListsRepository;
import it.euris.stazioneconcordia.service.CardService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class CardServiceImpl implements CardService {

    CardRepository cardRepository;
    LabelsRepository labelsRepository;
    ListsRepository listsRepository;

    @Override
    public List<Card> findAll() {
        return cardRepository.findAll();
    }

    @Override
    public List<Card> findByLabels(Long idLabels) {

        List<Card> cards = cardRepository.findAll();
        List<Card> cardsWithPriority = cards.stream()
                .filter(card -> Objects.equals(card.getLabels().getId(), idLabels))
                .collect(Collectors.toList());

        return cardsWithPriority;
    }


    @Override
    public List<Card> findAllCardsWhitExpirationDateInLast5Days() {
        List<Card> cards = cardRepository.findAll();

        List<Card> cardsNearExpiration = cards
                .stream()
                .filter(card -> card.getExpirationDate()!=null)
                .filter(Card -> Card.getExpirationDate().isAfter(LocalDateTime.now().minusDays(5L)))
                .filter(Card -> Card.getExpirationDate().isBefore(LocalDateTime.now().plusDays(5L)))
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

    @Override
    public Card getCardByIdTrelloFromDb(String idTrelloCard) {
        return cardRepository.getCardByIdTrello(idTrelloCard);
    }

    @Override
    public List<String> getAllIdTrelloForCardsFromDb() {
        return cardRepository.getTrelloIds();
    }

    @Override
    public boolean cardExistByTrelloIdAndLabel(String idTrello, Long idLabel) {
        Card existingCard = cardRepository.findByTrelloIdAndIdLabel(idTrello, idLabel);
        return existingCard != null;
    }

    @Override
    public boolean cardExistByTrelloId(String idTrello) {

        Card existingCard = cardRepository.getCardByIdTrello(idTrello);
        return existingCard != null;
    }

    @Override
    public Card getCardIfExistByTrelloId(String idTrello) {
        return null;
    }

    @Override
    public List<Card> getHighPriorityCards() {
        Long idPriority = labelsRepository.getLabelIdByNameIgnoreCase("Alta Priorità").getId();
        return cardRepository.findByIdLabel(idPriority);
    }

    @Override
    public List<Card> getMediumPriorityCards() {
        Long idPriority = labelsRepository.getLabelIdByNameIgnoreCase("Media Priorità").getId();
        return cardRepository.findByIdLabel(idPriority);
    }

    @Override
    public List<Card> getLowPriorityCards() {
        Long idPriority = labelsRepository.getLabelIdByNameIgnoreCase("Bassa Priorità").getId();
        return cardRepository.findByIdLabel(idPriority);
    }

    @Override
    public List<Card> getExpiringIn5DaysCards() {
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = LocalDateTime.now().plusDays(5L);
        return cardRepository.getExpiringIn5DaysCards(startDate, endDate);
    }

    @Override
    public List<Card> getByLabelName(String labelName) {
        Labels labels = labelsRepository.getLabelIdByNameIgnoreCase(labelName);
        return cardRepository.findByIdLabel(labels.getId());
    }


}

