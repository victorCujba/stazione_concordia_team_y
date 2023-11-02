package it.euris.stazioneconcordia.controller;

import it.euris.stazioneconcordia.data.dto.*;
import it.euris.stazioneconcordia.data.model.*;
import it.euris.stazioneconcordia.data.trelloDto.BoardTrelloDTO;
import it.euris.stazioneconcordia.data.trelloDto.CardTrelloDto;
import it.euris.stazioneconcordia.data.trelloDto.CommentTrelloDto;
import it.euris.stazioneconcordia.data.trelloDto.LabelsTrelloDto;
import it.euris.stazioneconcordia.service.*;

import it.euris.stazioneconcordia.service.trelloService.BoardTrelloService;
import it.euris.stazioneconcordia.service.trelloService.CardTrelloService;
import it.euris.stazioneconcordia.service.trelloService.CommentTrelloService;
import it.euris.stazioneconcordia.service.trelloService.LabelsTrelloService;
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


    @GetMapping("/sync")
    public void getInfoFromTrello(@RequestParam String idBoard) {
        getBoardFromTrello(idBoard);
        getAllUsersFromBoard(idBoard, KEY_VALUE, TOKEN_VALUE);
        labelsTrelloService.getLabelsByIdBoard(idBoard);

//        for (ListsDTO listDTO : listDTOs) {
//            CardDTO[] cardDTOs = getCardsFromTrelloList(listDTO.getIdTrello(), key, token);
//            for (CardDTO cardDTO : cardDTOs) {
//                getComment(stringToLong(cardDTO.getId()));
//            }
    }
//    }

    public BoardTrelloDTO getBoardFromTrello(@RequestParam String idBoard) {
        return boardTrelloService.getBoardFromTrelloByIdBoard(idBoard);
    }


    public ListsDTO[] getListsFromTrelloBoard(@RequestParam String idBoard, @RequestParam String key, @RequestParam String token) {
        Lists[] lists = listsService.getListFromTrelloBoard(idBoard, key, token);
        ListsDTO[] listsDTOs = new ListsDTO[lists.length];
        for (int i = 0; i < lists.length; i++) {
            listsDTOs[i] = lists[i].toDto();
        }
        return listsDTOs;
    }

    public List<CardTrelloDto> getCardsFromTrelloList(@RequestParam String idTrelloList) {
        return cardTrelloService.getCardsByIdList(idTrelloList);
    }

    public UserDTO getUserByUsername(@RequestParam String username, @RequestParam String key, @RequestParam String token) {
        User user = userService.getUserFromTrello(username, key, token);
        return user.toDto();
    }


    public List<LabelsTrelloDto> getLabelsFromTrelloBoard(@RequestParam String idBoard) {
        return labelsTrelloService.getLabelsByIdBoard(idBoard);

    }

    public UserDTO[] getAllUsersFromBoard(@RequestParam String idBoard, @RequestParam String key, @RequestParam String token) {
        User[] users = userService.getUserFromTrelloBoard(idBoard, key, token);
        UserDTO[] userDTOs = new UserDTO[users.length];
        for (int i = 0; i < users.length; i++) {
            userDTOs[i] = users[i].toDto();
        }
        return userDTOs;
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