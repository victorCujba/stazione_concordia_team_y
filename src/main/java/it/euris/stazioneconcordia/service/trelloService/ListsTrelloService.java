package it.euris.stazioneconcordia.service.trelloService;

import it.euris.stazioneconcordia.data.trelloDto.ListsTrelloDto;

import java.util.List;

public interface ListsTrelloService {

    List<ListsTrelloDto> getListsByIdBoard(String idBoard);

}
