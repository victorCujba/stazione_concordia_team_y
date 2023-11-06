package it.euris.stazioneconcordia.service;

import it.euris.stazioneconcordia.data.dto.BoardDTO;
import it.euris.stazioneconcordia.data.model.Board;


import java.util.List;

public interface BoardService {

    List<Board> findAll();

    Board insert(Board board);

    Board insertBoardFromTrello(BoardDTO boardDTO);

    Board update(Board board);

    Boolean deleteById(Long idBoard);

    Board findById(Long idBoard);

    Board getBoardByIdTrelloFromDb(String idTrello);


}
