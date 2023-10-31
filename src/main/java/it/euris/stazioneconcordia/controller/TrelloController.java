package it.euris.stazioneconcordia.controller;

import it.euris.stazioneconcordia.data.dto.*;
import it.euris.stazioneconcordia.data.model.*;
import it.euris.stazioneconcordia.data.trelloDto.*;
import it.euris.stazioneconcordia.service.*;
import it.euris.stazioneconcordia.trello.service.impl.TrelloCommentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static it.euris.stazioneconcordia.utility.DataConversionUtils.stringToLong;


@AllArgsConstructor
@RestController
@RequestMapping("/v1/from-trello")
public class TrelloController {

    private BoardService boardService;

    private ListsService listsService;

    private CardService cardService;

    private LabelsService labelsService;

    private UserService userService;

    private CommentService commentService;

    private TrelloCommentService trelloCommentService;


    @GetMapping("/sync")
    public void getInfoFromTrello(@RequestParam String idBoard) {
        getBoardFromTrello(idBoard);
        getAllUsersFromTrelloBoardByIdBoard(idBoard);
        getLabelsFromTrelloBoardByIdBoard(idBoard);
        List<ListsTrelloDto> listsTrelloDtos = getListsFromTrelloBoard(idBoard);
        for (ListsTrelloDto listsTrelloDto : listsTrelloDtos) {
            List<CardTrelloDto> cardTrelloDtos = getCardsFromTrelloList(listsTrelloDto.getId());
            for (CardTrelloDto cardTrelloDto : cardTrelloDtos) {
                getComment((cardTrelloDto.getId()));
            }
        }
    }

    public BoardTrelloDTO getBoardFromTrello(@RequestParam String idBoard) {
        return boardService.getBoardFromTrello(idBoard);
    }

    public List<ListsTrelloDto> getListsFromTrelloBoard(@RequestParam String idBoard) {
        return listsService.getListsFromTrelloByIdBoard(idBoard);
    }

    public List<CardTrelloDto> getCardsFromTrelloList(@RequestParam String idList) {
        return cardService.getCardsFromTrelloList(idList);
    }

    public UserTrelloDto getUserByUserName(@RequestParam String username) {
        return userService.getUserFromTrelloByUserName(username);
    }


    public List<LabelsTrelloDto> getLabelsFromTrelloBoardByIdBoard(@RequestParam String idBoard) {
        return labelsService.getLabelsFromTrelloBoardByIdBoard(idBoard);

    }

    public List<UserTrelloDto> getAllUsersFromTrelloBoardByIdBoard(@RequestParam String idBoard) {
        return userService.getAllUsersFromTrelloBoardByIdBoard(idBoard);
    }

    @GetMapping("/comments")
    public List<CommentTrelloDto> getCommentsFromCardByIdCard(@RequestParam String idCard) {
        return commentService.getCommentsFromCardByIdCard(idCard);

    }

    @GetMapping("/comment")
    public List<CommentTrelloDto> getComment(@RequestParam String idCard) {

        List<CommentTrelloDto> commentTrelloDTOs = trelloCommentService.getAllCommentsByIdCard(idCard);
        for (CommentTrelloDto commentTrelloDto : commentTrelloDTOs) {
            String date = commentTrelloDto.getDate().substring(0, 19);
            commentTrelloDto.setDate(date);
            Comment comment = commentTrelloDto.trellotoDto().toModel();
            commentService.insert(comment);
        }

        return commentTrelloDTOs;
    }
}
