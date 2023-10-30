package it.euris.stazioneconcordia.service;

import it.euris.stazioneconcordia.data.model.Board;
import it.euris.stazioneconcordia.data.trelloDto.BoardTrelloDTO;

import java.util.List;

public interface BoardService {

    List<Board> findAll();

    Board insert(Board board);

    Board update(Board board);

    Boolean deleteById(Long idBoard);

    Board findById(Long idBoard);

    Board getBoardFromTrello(String idBoard, String key, String token);
}
