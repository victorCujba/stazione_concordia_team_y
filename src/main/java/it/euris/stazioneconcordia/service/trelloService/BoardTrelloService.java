package it.euris.stazioneconcordia.service.trelloService;

import it.euris.stazioneconcordia.data.trelloDto.BoardTrelloDTO;

public interface BoardTrelloService {
    BoardTrelloDTO getBoardFromTrelloByIdBoard(String idBoard);
    void deleteABoard(String idBoard);
    void updateABoard(String idBoard,BoardTrelloService boardTrelloService);



}
