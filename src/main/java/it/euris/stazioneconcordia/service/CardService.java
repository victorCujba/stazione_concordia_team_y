package it.euris.stazioneconcordia.service;

import it.euris.stazioneconcordia.data.enums.Priority;
import it.euris.stazioneconcordia.data.model.Card;

import java.time.LocalDateTime;
import java.util.List;

public interface CardService {

    List<Card> findAll();

    Card findByPriority(Priority priority);

    Card findByExpirationDate(LocalDateTime expirationDate);

    Card insert(Card card);

    Card update(Card card);

    Boolean deleteById(Long idCard);

    Card findById(Long idCard);


}
