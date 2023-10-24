package it.euris.stazioneconcordia.service;

import it.euris.stazioneconcordia.data.model.Board;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public interface BoardService {

    List<Board> findAll();

    Board insert(Board board);

    Board update(Board board);

    Boolean deleteById(String idBoard);

    Board findById(String idBoard);

    Board getBoardFromTrello(String idBoard, String key, String token);
}
