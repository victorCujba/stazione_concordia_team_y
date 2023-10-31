package it.euris.stazioneconcordia.service.trelloService;

import it.euris.stazioneconcordia.data.trelloDto.CardTrelloDto;

import java.util.List;

public interface CardTrelloService {

    List<CardTrelloDto> getCardsByIdList(String idTrelloList);

    CardTrelloDto getCardByIdCard(String idCard);

    CardTrelloDto putCardToAnotherListByIdCardAndIdList(String idCard, String idList);




}
