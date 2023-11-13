package it.euris.stazioneconcordia.controller.trelloController;

import it.euris.stazioneconcordia.data.dto.BoardDTO;
import it.euris.stazioneconcordia.data.model.Board;
import it.euris.stazioneconcordia.data.model.Card;
import it.euris.stazioneconcordia.data.trelloDto.BoardTrelloDTO;
import it.euris.stazioneconcordia.service.BoardService;
import it.euris.stazioneconcordia.service.trelloService.BoardTrelloService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/v1/from-trello/boards")
public class BoardTrelloController {

    private BoardTrelloService boardTrelloService;
    private BoardService boardService;

    public BoardTrelloDTO getBoardFromTrello(String idBoard) {
        return boardTrelloService.getBoardFromTrelloByIdBoard(idBoard);
    }
    @PutMapping("/update-board-to-trello")
    public void updateBoardFromDbToTrello(@RequestParam Long idBoard) {
        BoardTrelloDTO boardTrelloDTO= boardService.findById(idBoard).toDto().toTrelloDto();
        Board board =boardService.findById(idBoard);

        boardTrelloDTO.setId(board.getIdTrello());

        boardTrelloService.updateABoard(board.getIdTrello(),boardTrelloService);
    }
    public void boardCompareToBoard(Board newBoard, Board existingBoard ){

        if (newBoard.getDateLastActivity().isAfter(existingBoard.getDateLastActivity())||
                newBoard.getDateLastActivity().isEqual(existingBoard.getDateLastActivity() )){

            newBoard.setId(existingBoard.getId());
            newBoard.setDateLastActivity(LocalDateTime.now());
            boardService.update(newBoard);

        }else{
            updateBoardFromDbToTrello(existingBoard.getId());
        }
    }
    public void insertBoardFromTrelloToDb(String idBoard) {
        BoardTrelloDTO boardTrelloDTO =getBoardFromTrello(idBoard);
        BoardDTO boardDTO = boardTrelloDTO.trellotoDto();
        Board board = boardDTO.toModel();
        Board existingBoard=boardService.getBoardByIdTrelloFromDb(idBoard);
        if (existingBoard == null) {
            boardService.insert(board);

        } else {
            boardCompareToBoard(board,existingBoard);
        }
    }
    @DeleteMapping("/delete-a-board-to-trello")
    public void deleteABoardToTrello(@RequestParam Long idBoard) {
        Board board = boardService.findById(idBoard);

        boardTrelloService.deleteABoard(board.getIdTrello());
    }
}
