package it.euris.stazioneconcordia.service;

import it.euris.stazioneconcordia.data.enums.Priority;
import it.euris.stazioneconcordia.data.model.Card;

import java.time.LocalDateTime;
import java.util.List;

public interface CardService {

    List<Card> findAll();

   List<Card>  findByPriority(Priority priority);

    List<Card> findAllCardsWhitExpirationDateInLast5Days();

    Card insert(Card card);

    Card update(Card card);

    Boolean deleteById(String idCard);

    Card findById(String idCard);


}
