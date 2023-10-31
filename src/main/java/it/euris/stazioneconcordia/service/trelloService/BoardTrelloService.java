package it.euris.stazioneconcordia.service.trelloService;

import it.euris.stazioneconcordia.data.model.Board;
import it.euris.stazioneconcordia.data.trelloDto.BoardTrelloDTO;

public interface BoardTrelloService {
    BoardTrelloDTO getBoardFromTrelloByIdBoard(String idBoard);



}
