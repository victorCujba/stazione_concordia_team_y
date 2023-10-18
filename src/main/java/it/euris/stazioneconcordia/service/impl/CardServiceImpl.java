package it.euris.stazioneconcordia.service.impl;

import it.euris.stazioneconcordia.data.enums.Priority;
import it.euris.stazioneconcordia.data.model.Card;
import it.euris.stazioneconcordia.service.CardService;

import java.time.LocalDateTime;
import java.util.List;

public class CardServiceImpl implements CardService {
    @Override
    public List<Card> findAll() {
        return null;
    }

    @Override
    public Card findByPriority(Priority priority) {
        return null;
    }

    @Override
    public Card findByExpirationDate(LocalDateTime expirationDate) {
        return null;
    }

    @Override
    public Card insert(Card board) {
        return null;
    }

    @Override
    public Card update(Card board) {
        return null;
    }

    @Override
    public Boolean deleteById(Integer idCard) {
        return null;
    }

    @Override
    public Card findById(Integer idCard) {
        return null;
    }
}
