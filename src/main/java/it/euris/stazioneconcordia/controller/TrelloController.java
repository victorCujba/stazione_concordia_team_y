package it.euris.stazioneconcordia.controller;

import it.euris.stazioneconcordia.data.dto.*;
import it.euris.stazioneconcordia.data.model.*;
import it.euris.stazioneconcordia.data.trelloDto.*;
import it.euris.stazioneconcordia.repository.CardRepository;
import it.euris.stazioneconcordia.service.*;

import it.euris.stazioneconcordia.service.trelloService.*;
import it.euris.stazioneconcordia.trello.service.impl.TrelloCommentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
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
        insertLabelsFromTrelloToDb(idBoard);
        insertUsersFromTrelloToDb(idBoard);
        insertListsFromTrelloToDb(idBoard);
        insertCardsFromTrelloToDb();
        insertCommentsFromTrelloToDb();

    }


    public BoardTrelloDTO getBoardFromTrello(String idBoard) {
        return boardTrelloService.getBoardFromTrelloByIdBoard(idBoard);
    }

    public void insertBoardFromTrelloToDb(String idBoard) {
        if (boardService.getBoardByIdTrelloFromDb(idBoard) == null) {
            BoardDTO boardDTO = getBoardFromTrello(idBoard).trellotoDto();
            boardService.insertBoardFromTrello(boardDTO);
        }
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
                    if (!(labelsService.getAllIdTrelloForLabels().contains(labels.getIdTrello()))) {
                        labels.setBoard(Board.builder().id(idBoardFromDB).build());
                        labelsService.insert(labels);
                    }
                });
        Labels defaultLabel = Labels.builder().idTrello("0").name("DefaultLabel")
                .board(Board.builder().id(idBoardFromDB).build()).build();
        if (!(labelsService.getAllIdTrelloForLabels().contains(defaultLabel.getIdTrello()))) {
            labelsService.insert(defaultLabel);
        }
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
                    if (!(listsService.getAllIdTrelloForLists().contains(lists.getIdTrello()))) {
                        lists.setBoard(Board.builder().id(idBoardFromDB).build());
                        listsService.insert(lists);
                    }
                });
    }

    public List<CardTrelloDto> getCardsFromTrelloByIdList(String idList) {
        return cardTrelloService.getCardsByIdList(idList);
    }

    public void insertCardsByIdListAndIdLabel(String idList, String idLabel) {
        Long idListFromDb = listsService.getListByIdTrelloFromDb(idList).getId();
        Long idLabelFromDb = labelsService.getLabelByIdTrelloFromDb(idLabel).getId();
        getCardsFromTrelloByIdList(idList).stream()
                .map(CardTrelloDto::trellotoDto)
                .map(CardDTO::toModel)
                .forEach(card -> {
//                    cardExist(card);
                    card.setList(Lists.builder().id(idListFromDb).build());
                    card.setLabels(Labels.builder().id(idLabelFromDb).build());
                    cardService.insert(card);
                });
    }

    //    public Card cardExist(Card card) {
//        Card resultCard;
//
//                cardService
//                .findAll()
//                .forEach(card1 -> {
//                    if (card1.getIdTrello().equals(card.getIdTrello())) {
//                        resultCard = compareCard(card1, card);
//
//                    }
//                });
//        return resultCard;
//
//    }
    public Card compareCard(Card card, Card card2) {
        if (card.getDateLastActivity().isAfter(card2.getDateLastActivity())) {
            return card;
        } else {
            return card2;
        }
    }


    public void insertCardsFromTrelloToDb() {
        List<CardTrelloDto> cardTrelloDtos = new ArrayList<>();

        for (String idList : getAllIdTrelloForListsFromDb()) {
            List<CardTrelloDto> cardsList = getCardsFromTrelloByIdList(idList);
            cardTrelloDtos.addAll(cardsList);
        }
        for (CardTrelloDto card : cardTrelloDtos) {
            if (!(cardService.cardExistByTrelloId(card.getId()))) {
                List<String> idLabels = card.getIdLabels();
                if (idLabels == null || idLabels.isEmpty()) {
                    String idLabel = "0";
                    insertCardByLabel(idLabel, card);
                } else {
                    for (String idLabel : idLabels) {
                        insertCardByLabel(idLabel, card);
                    }
                }
            } else {
                return;
            }
        }
    }

    private void insertCardByLabel(String idLabel, CardTrelloDto card) {

        Long idLabelFromDb;
        if (idLabel != null) {
            idLabelFromDb = labelsService.getLabelByIdTrelloFromDb(idLabel).getId();
        } else {
            idLabelFromDb = labelsService.getDefaultLabelId();
        }

        if (!cardService.cardExistByTrelloIdAndLabel(card.getId(), idLabelFromDb)) {
            Long idListFromDb = listsService.getListByIdTrelloFromDb(card.getIdList()).getId();

            card.trellotoDto().toModel().setList(Lists.builder().id(idListFromDb).build());
            card.trellotoDto().toModel().setLabels(Labels.builder().id(idLabelFromDb).build());
            card.trellotoDto().toModel().setList(Lists.builder().id(idLabelFromDb).build());
            cardService.insertIntoDb(card.trellotoDto().toModel());
        }

    }


    private List<String> getAllIdTrelloForLabelsFromDb() {
        return labelsService.getAllIdTrelloForLabels();
    }

    private List<String> getAllIdTrelloForListsFromDb() {
        return listsService.getAllIdTrelloForLists();
    }

    public void insertCommentsFromTrelloToDb() {
        List<CommentTrelloDto> commentList = new ArrayList<>();

        List<String> idTrelloCardList = cardService.getAllIdTrelloForCardsFromDb();
        for (String idCard : idTrelloCardList) {
            List<CommentTrelloDto> commentTrelloDtos = getCommentsFromCardByIdCard(idCard);
            commentList.addAll(commentTrelloDtos);
        }

        for (CommentTrelloDto commentTrelloDto : commentList) {
            CommentDTO commentDTO = commentTrelloDto.trellotoDto();
            Comment comment = commentDTO.toModel();
            commentService.insertComment(comment);
        }

    }

    public List<CommentTrelloDto> getCommentsFromCardByIdCard(String idCard) {
        return commentTrelloService.getCommentsFromCardByIdCard(idCard);
    }

    @GetMapping("/high-priority-cards")
    public List<CardDTO> getHighPriorityCards() {
        return cardService.getHighPriorityCards().stream().map(Card::toDto).toList();
    }

    @GetMapping("/medium-priority-cards")
    public List<CardDTO> getMediumPriorityCards() {
        return cardService.getMediumPriorityCards().stream().map(Card::toDto).toList();
    }

    @GetMapping("/low-priority-cards")
    public List<CardDTO> getLowPriorityCards() {
        return cardService.getLowPriorityCards().stream().map(Card::toDto).toList();
    }

    @GetMapping("/expiring-in-5-days-cards")
    public List<CardDTO> getExpiringCards() {
        return cardService.getExpiringIn5DaysCards().stream().map(Card::toDto).toList();
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