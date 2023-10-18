package it.euris.stazioneconcordia.service;

import it.euris.stazioneconcordia.enums.Priority;
import it.euris.stazioneconcordia.model.Card;

import java.time.LocalDateTime;
import java.util.List;

public interface CardService {

    List<Card> findAll();

    Card findByPriority(Priority priority);

    Card findByExpirationDate(LocalDateTime expirationDate);

    Card insert(Card board);

    Card update(Card board);

    Boolean deleteById(Integer idCard);

    Card findById(Integer idCard);


}
