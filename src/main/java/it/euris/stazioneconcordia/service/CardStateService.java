package it.euris.stazioneconcordia.service;


import it.euris.stazioneconcordia.data.model.CardState;

import java.util.List;


public interface CardStateService {

    List<CardState> findAll();

    CardState insert(CardState cardState);

    CardState update(CardState cardState);

    Boolean deleteById(Long idCardState);

    CardState findById(Long idCardState);
}


