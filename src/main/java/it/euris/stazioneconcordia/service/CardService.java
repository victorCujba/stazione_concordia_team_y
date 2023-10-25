package it.euris.stazioneconcordia.service;

import it.euris.stazioneconcordia.data.model.Card;

import java.util.List;

public interface CardService {

    List<Card> findAll();

    List<Card> findByLabels(String idLabels);

    List<Card> findAllCardsWhitExpirationDateInLast5Days();

    Card insert(Card card);

    Card update(Card card);

    Boolean deleteById(String idCard);

    Card findById(String idCard);

    Card[] getCardsFromTrelloList(String idList, String key, String token);
}
