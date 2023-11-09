package it.euris.stazioneconcordia.service;

import it.euris.stazioneconcordia.data.dto.CardDTO;
import it.euris.stazioneconcordia.data.model.Card;

import java.util.List;
import java.util.Set;

public interface CardService {

    List<Card> findAll();

    List<Card> findByLabels(Long idLabels);

    List<Card> findAllCardsWhitExpirationDateInLast5Days();

    Card insert(Card card);

    Card update(Card card);

    Boolean deleteById(Long idCard);

    Card findById(Long idCard);


    Card getCardByIdTrelloFromDb(String idCard);

    List<String> getAllIdTrelloForCardsFromDb();

    boolean cardExistByTrelloIdAndLabel(String idTrello, Long idLabel);

    boolean cardExistByTrelloId(String idTrello);
    Card getCardIfExistByTrelloId(String idTrello);

    List<Card> getHighPriorityCards();

    List<Card> getMediumPriorityCards();

    List<Card> getLowPriorityCards();

    List<Card> getExpiringIn5DaysCards();

    List<Card> getByLabelName(String labelName);
}
