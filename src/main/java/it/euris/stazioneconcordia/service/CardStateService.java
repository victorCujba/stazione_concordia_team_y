package it.euris.stazioneconcordia.service;

import it.euris.stazioneconcordia.data.dto.CardStateDTO;
import it.euris.stazioneconcordia.data.enums.ListLabel;
import it.euris.stazioneconcordia.data.model.Board;
import it.euris.stazioneconcordia.data.model.CardState;

import java.util.List;
import java.util.Optional;

public interface CardStateService {

    List<CardState> findAll();

    CardState insert(CardState cardState);

    CardState update(CardState cardState);

    Boolean deleteById(String idCardState);

    CardState findById(String idCardState);
}


