package it.euris.stazioneconcordia.controller;

import com.google.gson.Gson;
import it.euris.stazioneconcordia.data.dto.*;
import it.euris.stazioneconcordia.data.model.*;
import it.euris.stazioneconcordia.data.trelloDto.BoardTrelloDTO;
import it.euris.stazioneconcordia.data.trelloDto.CardTrelloDto;
import it.euris.stazioneconcordia.data.trelloDto.CommentTrelloDto;
import it.euris.stazioneconcordia.service.*;

import it.euris.stazioneconcordia.service.trelloService.BoardTrelloService;
import it.euris.stazioneconcordia.service.trelloService.CardTrelloService;
import it.euris.stazioneconcordia.trello.service.impl.TrelloCardServiceImpl;
import it.euris.stazioneconcordia.trello.service.impl.TrelloCommentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static it.euris.stazioneconcordia.trello.utils.TrelloConstants.CARD_01_ID_VALUE;
import static it.euris.stazioneconcordia.utility.DataConversionUtils.numberToString;
import static it.euris.stazioneconcordia.utility.DataConversionUtils.stringToLong;


@AllArgsConstructor
@RestController
@RequestMapping("/v1/from-trello")
public class TrelloController {
    private BoardTrelloService boardTrelloService;

    private BoardService boardService;

    private ListsService listsService;

    private CardService cardService;

    private LabelsService labelsService;

    private UserService userService;

    private CommentService commentService;

    private TrelloCommentService trelloCommentService;

    private CardTrelloService cardTrelloService;


    @GetMapping("/sync")
    public void getInfoFromTrello(@RequestParam String idBoard, @RequestParam String key, @RequestParam String token) {
        getBoardFromTrello(idBoard);
        getAllUsersFromBoard(idBoard, key, token);
        getLabelsFromTrelloBoard(idBoard, key, token);
        ListsDTO[] listDTOs = getListsFromTrelloBoard(idBoard, key, token);
        for (ListsDTO listDTO : listDTOs) {
//            CardDTO[] cardDTOs = getCardsFromTrelloList(listDTO.getIdTrello(), key, token);
//            for (CardDTO cardDTO : cardDTOs) {
//                getComment(stringToLong(cardDTO.getId()));
//            }
        }
    }

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
        List<CardTrelloDto> cardTrelloDtos = cardTrelloService.getCardsByIdList(idTrelloList);
//        CardDTO[] cardDTOs = new CardDTO[cards.length];
//        for (int i = 0; i < cards.length; i++) {
//            cardDTOs[i] = cards[i].toDto();
//        }
        return null;
    }

    public UserDTO getUserByUsername(@RequestParam String username, @RequestParam String key, @RequestParam String token) {
        User user = userService.getUserFromTrello(username, key, token);
        return user.toDto();
    }


    public LabelsDTO[] getLabelsFromTrelloBoard(@RequestParam String idBoard, @RequestParam String key, @RequestParam String token) {
        Labels[] labels = labelsService.getLabelsFromTrelloBoard(idBoard, key, token);
        LabelsDTO[] labelsDTOs = new LabelsDTO[labels.length];
        for (int i = 0; i < labels.length; i++) {
            labelsDTOs[i] = labels[i].toDto();
        }
        return labelsDTOs;
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
    public CommentDTO[] getCommentsFromCard(@RequestParam Long idCard, @RequestParam String key, @RequestParam String token) {
        Comment[] comments = commentService.getCommentsFromCard(idCard, key, token);
        CommentDTO[] commentDTOs = new CommentDTO[comments.length];
        for (int i = 0; i < comments.length; i++) {
            commentDTOs[i] = comments[i].toDto();
        }
        return commentDTOs;
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