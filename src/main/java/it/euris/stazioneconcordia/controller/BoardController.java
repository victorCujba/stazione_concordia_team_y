package it.euris.stazioneconcordia.controller;

import it.euris.stazioneconcordia.data.dto.BoardDTO;
import it.euris.stazioneconcordia.data.model.Board;
import it.euris.stazioneconcordia.exception.IdMustBeNullException;
import it.euris.stazioneconcordia.exception.IdMustNotBeNullException;
import it.euris.stazioneconcordia.service.BoardService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/boards")
public class BoardController {

    BoardService boardService;
    @GetMapping("/v1")
    public List<BoardDTO> getAllBoards() {
        return boardService.findAll().stream().map(Board::toDto).toList();
    }
    @PostMapping("/v1")
    public BoardDTO saveBoard(@RequestBody BoardDTO boardDTO) {
        try{
           Board board = boardDTO.toModel();
            return boardService.insert(board).toDto();
        }
        catch(IdMustBeNullException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
    @PutMapping("/v1")
    public BoardDTO updateBoard(@RequestBody BoardDTO boardDTO){
        try{
            Board board = boardDTO.toModel();
            return boardService.update(board).toDto();
        }
        catch(IdMustNotBeNullException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
    @DeleteMapping("/v1/{id}")
    public Boolean deleteBoard(@PathVariable("id") Long idBoard) {
        return boardService.deleteById(idBoard);

    }
    @GetMapping("/v1/{id}")
    public BoardDTO getBoardById(@PathVariable("id") Long idBoard) {
        return boardService.findById(idBoard).toDto();
    }


}