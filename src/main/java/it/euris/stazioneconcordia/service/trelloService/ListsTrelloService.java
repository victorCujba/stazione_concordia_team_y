package it.euris.stazioneconcordia.service.trelloService;

import it.euris.stazioneconcordia.data.trelloDto.ListsTrelloDto;

import java.util.List;

public interface ListsTrelloService {

    List<ListsTrelloDto> getListsByIdBoard(String idBoard);
    void updateAList(String idList, ListsTrelloDto listsTrelloDto);
    void closeAList(String idList, ListsTrelloDto listsTrelloDt);
    void createANewList( ListsTrelloDto listsTrelloDto);


}
