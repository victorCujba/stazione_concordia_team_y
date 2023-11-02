package it.euris.stazioneconcordia.controller;

import it.euris.stazioneconcordia.data.dto.*;
import it.euris.stazioneconcordia.data.model.*;
import it.euris.stazioneconcordia.data.trelloDto.*;
import it.euris.stazioneconcordia.service.*;

import it.euris.stazioneconcordia.service.trelloService.*;
import it.euris.stazioneconcordia.trello.service.impl.TrelloCommentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static it.euris.stazioneconcordia.trello.utils.TrelloConstants.*;


@AllArgsConstructor
@RestController
@RequestMapping("/v1/from-trello")
public class TrelloController {

    private BoardTrelloService boardTrelloService;
    private LabelsTrelloService labelsTrelloService;
    private BoardService boardService;
    private ListsService listsService;
    private CardService cardService;
    private LabelsService labelsService;
    private UserService userService;
    private CommentService commentService;
    private TrelloCommentService trelloCommentService;
    private CardTrelloService cardTrelloService;
    private CommentTrelloService commentTrelloService;
    private ListsTrelloService listsTrelloService;
    private UserTrelloService userTrelloService;


    @GetMapping("/sync")
    public void getInfoFromTrello(@RequestParam String idBoard) {
        getBoardFromTrello(idBoard);
        insertBoardFromTrelloToDb(idBoard);

    }


    public BoardTrelloDTO getBoardFromTrello(@RequestParam String idBoard) {
        return boardTrelloService.getBoardFromTrelloByIdBoard(idBoard);
    }

    public void insertBoardFromTrelloToDb(@RequestParam String idBoard) {
        BoardDTO boardDTO = getBoardFromTrello(idBoard).trellotoDto();
        boardService.insertBoardFromTrello(boardDTO);
    }


    public List<ListsTrelloDto> getListsFromTrelloBoard(@RequestParam String idBoard) {
        return listsTrelloService.getListsByIdBoard(idBoard);
    }

    public List<CardTrelloDto> getCardsFromTrelloList(@RequestParam String idTrelloList) {
        return cardTrelloService.getCardsByIdList(idTrelloList);
    }

    public UserTrelloDto getUserFromTrelloByUsername(@RequestParam String username) {
        return userTrelloService.getUserFromTrello(username);
    }


    public List<LabelsTrelloDto> getLabelsFromTrelloBoard(@RequestParam String idBoard) {
        return labelsTrelloService.getLabelsByIdBoard(idBoard);

    }

    public List<UserTrelloDto> getAllUsersFromBoard(@RequestParam String idBoard) {
        return userTrelloService.getUsersFromTrello(idBoard);
    }

    @GetMapping("/comments")
    public List<CommentTrelloDto> getCommentsFromCard(@RequestParam String idCard) {
        return commentTrelloService.getCommentsFromCardByIdCard(idCard);
    }

    @GetMapping("/comment")
    public List<CommentDTO> getComment(@RequestParam Long idCard) {
//        trelloCardService.getCardsByIdBoard(BOARD_ID_VALUE);
//        trelloCardService.getCardsByIdLists(LIST_O1_ID_VALUE);
//        trelloCardService.getCardByIdCard(CARD_01_ID_VALUE);
//        List<CommentTrelloDto> commentDTOs = trelloCommentService.getAllCommentsByIdCard(numberToString(idCard));
//        for (CommentDTO commentDTO : commentDTOs) {
//            String date = commentDTO.getDate().substring(0, 19);
//            commentDTO.setDate(date);
//            Comment comment = commentDTO.toModel();
//            commentService.insert(comment);
//        }
//
//        return commentDTOs;
        return null;
    }
}