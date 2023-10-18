package it.euris.stazioneconcordia.service;

import it.euris.stazioneconcordia.model.Board;

import java.util.List;

public interface BoardService {

    List<Board> findAll();

    Board insert(Board board);

    Board update(Board board);

    Boolean deleteById(Long idBoard);

    Board findById(Long idBoard);
}
