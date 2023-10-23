package it.euris.stazioneconcordia.service;

import it.euris.stazioneconcordia.data.dto.CardStateDTO;
import it.euris.stazioneconcordia.data.enums.ListLabel;
import it.euris.stazioneconcordia.data.model.Card;
import it.euris.stazioneconcordia.data.model.CardState;
import it.euris.stazioneconcordia.data.model.Lists;

import java.util.List;

public interface CardStateService {

    List<CardState> findAll();


    CardStateDTO updateCardState(String idCard, ListLabel fromList, ListLabel toList);
}
