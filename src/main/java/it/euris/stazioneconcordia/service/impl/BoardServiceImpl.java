package it.euris.stazioneconcordia.service.impl;

import it.euris.stazioneconcordia.data.model.Board;
import it.euris.stazioneconcordia.exception.IdMustBeNullException;
import it.euris.stazioneconcordia.exception.IdMustNotBeNullException;
import it.euris.stazioneconcordia.repository.BoardRepository;
import it.euris.stazioneconcordia.service.BoardService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class BoardServiceImpl implements BoardService {

    BoardRepository boardRepository;

    @Override
    public List<Board> findAll() {
        return boardRepository.findAll();
    }

    @Override
    public Board insert(Board board) {
        if (board.getId() != null) {
            throw new IdMustBeNullException();
        }
        return boardRepository.save(board);
    }

    @Override
    public Board update(Board board) {
        if (board.getId() == null) {
            throw new IdMustNotBeNullException();
        }
        return boardRepository.save(board);
    }

    @Override
    public Boolean deleteById(String idBoard) {
        boardRepository.deleteById(idBoard);
        return boardRepository.findById(idBoard).isEmpty();
    }

    @Override
    public Board findById(String idBoard) {
        return boardRepository.findById(idBoard).orElse(Board.builder().build());
    }
}
