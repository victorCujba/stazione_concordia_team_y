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
        insertBoardFromTrelloToDb(idBoard);
        insertUsersFromTrelloToDb(idBoard);
        insertLabelsFromTrelloToDb(idBoard);
        insertListsFromTrelloToDb(idBoard);

    }


    public BoardTrelloDTO getBoardFromTrello(String idBoard) {
        return boardTrelloService.getBoardFromTrelloByIdBoard(idBoard);
    }

    public void insertBoardFromTrelloToDb(String idBoard) {
        BoardDTO boardDTO = getBoardFromTrello(idBoard).trellotoDto();
        boardService.insertBoardFromTrello(boardDTO);
    }


    public List<ListsTrelloDto> getListsFromTrelloBoard(String idBoard) {
        return listsTrelloService.getListsByIdBoard(idBoard);
    }

    public List<CardTrelloDto> getCardsFromTrelloList(String idTrelloList) {
        return cardTrelloService.getCardsByIdList(idTrelloList);
    }

    public UserTrelloDto getUserFromTrelloByUsername(String username) {
        return userTrelloService.getUserFromTrelloByUserName(username);
    }


    public List<LabelsTrelloDto> getLabelsFromTrelloBoard(String idBoard) {
        return labelsTrelloService.getLabelsByIdBoard(idBoard);

    }

    public void insertLabelsFromTrelloToDb(String idBoard) {
        Long idBoardFromDB = boardService.getBoardByIdTrelloFromDb(idBoard).getId();
        getLabelsFromTrelloBoard(idBoard).stream()
                .map(LabelsTrelloDto::trellotoDto)
                .map(LabelsDTO::toModel)
                .forEach(labels -> {
                    labels.setBoard(Board.builder().id(idBoardFromDB).build());
                    labelsService.insert(labels);
                });
    }

    public List<UserTrelloDto> getAllUsersFromBoard(String idBoard) {
        return userTrelloService.getUsersFromTrelloByIdBoard(idBoard);
    }

    public void insertUsersFromTrelloToDb(String idBoard) {
        getAllUsersFromBoard(idBoard).stream()
                .map(UserTrelloDto::trellotoDto)
                .map(UserDTO::toModel)
                .forEach(userService::insert);
    }

    public List<ListsTrelloDto> getAllListsFromBoard(String idBoard) {
        return listsTrelloService.getListsByIdBoard(idBoard);
    }


    public void insertListsFromTrelloToDb(String idBoard) {
        Long idBoardFromDB = boardService.getBoardByIdTrelloFromDb(idBoard).getId();
        getAllListsFromBoard(idBoard).stream()
                .map(ListsTrelloDto::trellotoDto)
                .map(ListsDTO::toModel)
                .forEach(lists -> {
                    lists.setBoard(Board.builder().id(idBoardFromDB).build());
                    listsService.insert(lists);
                });
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