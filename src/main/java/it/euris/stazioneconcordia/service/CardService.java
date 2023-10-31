package it.euris.stazioneconcordia.service;

import it.euris.stazioneconcordia.data.dto.CardDTO;
import it.euris.stazioneconcordia.data.model.Card;
import it.euris.stazioneconcordia.data.trelloDto.CardTrelloDto;

import java.util.List;

public interface CardService {

    List<Card> findAll();

    List<Card> findByLabels(Long idLabels);

    List<Card> findAllCardsWhitExpirationDateInLast5Days();

    Card insert(Card card);

    Card update(Card card);

    Boolean deleteById(Long idCard);

    Card findById(Long idCard);

    List<CardTrelloDto> getCardsFromTrelloList(String idList);
}
