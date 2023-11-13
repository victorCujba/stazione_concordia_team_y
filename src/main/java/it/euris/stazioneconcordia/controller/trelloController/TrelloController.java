package it.euris.stazioneconcordia.controller.trelloController;

import it.euris.stazioneconcordia.controller.trelloController.BoardTrelloController;
import it.euris.stazioneconcordia.controller.trelloController.CardTrelloController;
import it.euris.stazioneconcordia.controller.trelloController.CommentTrelloController;
import it.euris.stazioneconcordia.controller.trelloController.LabelsTrelloController;
import it.euris.stazioneconcordia.data.dto.*;
import it.euris.stazioneconcordia.data.model.*;
import it.euris.stazioneconcordia.data.trelloDto.*;
import it.euris.stazioneconcordia.service.*;

import it.euris.stazioneconcordia.service.trelloService.*;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static it.euris.stazioneconcordia.utility.DataConversionUtils.*;


@AllArgsConstructor
@RestController
@RequestMapping("/v1/from-trello")
public class TrelloController {


    private LabelsTrelloService labelsTrelloService;
    private BoardService boardService;
    private ListsService listsService;
    private CardService cardService;
    private LabelsService labelsService;
    private UserService userService;
    private CommentService commentService;
    private CardTrelloService cardTrelloService;

    private ListsTrelloService listsTrelloService;
    private UserTrelloService userTrelloService;

    BoardTrelloController boardTrelloController;
    CardTrelloController cardTrelloController;
    ListsTrelloController listsTrelloController;
    UserTrelloController userTrelloController;
    LabelsTrelloController labelsTrelloController;
    CommentTrelloController commentTrelloController;

    @GetMapping("/sync")
    public void getInfoFromTrello(@RequestParam String idBoard) {
        insertBoardFromTrelloToDb(idBoard);
        insertLabelsFromTrelloToDb(idBoard);
        insertUsers(idBoard);
        listsTrelloController.findIfExistANewListOfDBAndPutOnTrello();
        insertListsFromTrelloToDb(idBoard);
        cardTrelloController.findIfExistANewCardOfDBAndPutOnTrello();
        insertCardsFromTrelloToDb(idBoard);
        commentTrelloController.findIfExistANewCommentOfDBAndPutOnTrello();
        insertCommentsFromTrelloToDb();

    }

    @PostMapping("/insertBoardFromTrello")
    public void insertBoard(@RequestParam String idBoard) {
        insertBoardFromTrelloToDb(idBoard);
    }

    @PostMapping("/insertListFromTrello")
    public void insertList(@RequestParam String idBoard) {
        List<ListsTrelloDto> listsTrelloDtos = listsTrelloService.getListsByIdBoard(idBoard);
        Board board = boardService.getBoardByIdTrelloFromDb(idBoard);

        listsTrelloDtos.forEach(listsTrelloDto -> {
            ListsDTO listsDTO = listsTrelloDto.trellotoDto();
            Lists updatedList = listsDTO.toModel();
            updatedList.setBoard(board);

            Lists existingList = listsService.getListByIdTrelloFromDb(updatedList.getIdTrello());
            if (existingList == null) {
                listsService.insert(updatedList);
            } else {
                listsTrelloController.listsCompareToLists(updatedList,existingList);
            }
        });
    }

    @PostMapping("/insert-users-from-trello")
    public void insertUsers(@RequestParam String idBoard) {
        List<UserTrelloDto> userTrelloDtos = userTrelloController.getAllUsersFromBoard(idBoard);

        userTrelloDtos.forEach(userTrelloDto -> {
            UserDTO userDTO = userTrelloDto.trellotoDto();
            User updatedUser = userDTO.toModel();

            User existingUser = userService.getUserByIdTrelloFromDb(updatedUser.getIdTrello());
            if (existingUser == null) {
                userService.insert(updatedUser);
            } else {
                updatedUser.setId(existingUser.getId());
                userService.update(updatedUser);
            }
        });
    }

    @PostMapping("/insert-cards-from-trello")
    public void insertCards(@RequestParam String idBoard) {
        List<CardTrelloDto> cardTrelloDtos = cardTrelloService.getCardsByIdBoard(idBoard);

        cardTrelloDtos.forEach(cardTrelloDto -> {
            CardDTO cardDTO = cardTrelloDto.trellotoDto();
            Card updatedCard = cardDTO.toModel();
            updatedCard.setLabels(labelsService.getLabelByIdTrelloFromDb(updatedCard.getLabels().getIdTrello()));
            updatedCard.setList(listsService.getListByIdTrelloFromDb(updatedCard.getList().getIdTrello()));
            updatedCard.setDateLastActivity(cardService.getCardByIdTrelloFromDb(updatedCard.getIdTrello()).getDateLastActivity());

            Card existingCard = cardService.getCardByIdTrelloFromDb(updatedCard.getIdTrello());
            if (existingCard == null) {
                cardService.insert(updatedCard);
            } else {
               cardTrelloController.cardCompareToCard(updatedCard,existingCard);
            }
        });
    }
    @PostMapping("/insert-labels-from-trello")
    public void insertLabels(@RequestParam String idBoard) {
        List<LabelsTrelloDto> labelsTrelloDtos = labelsTrelloService.getLabelsByIdBoard(idBoard);

        labelsTrelloDtos.forEach(labelsTrelloDto -> {
            LabelsDTO labelsDTO = labelsTrelloDto.trellotoDto();
            Labels updatedLabels = labelsDTO.toModel();
            updatedLabels.setBoard(boardService.getBoardByIdTrelloFromDb(idBoard));

            Labels existingLabel = labelsService.getLabelByIdTrelloFromDb(updatedLabels.getIdTrello());
            if (existingLabel == null) {
                labelsService.insert(updatedLabels);
            } else {
                updatedLabels.setId(existingLabel.getId());
                labelsService.update(updatedLabels);
            }
        });
    }

    @PostMapping("/insert-comments-from-trello")
    public void insertComments(@RequestParam String idBoard) {


    }

    public void insertCommentsFromTrelloToDb() {
        List<CommentTrelloDto> commentList = new ArrayList<>();

        List<String> idTrelloCardList = cardService.getAllIdTrelloForCardsFromDb();
        for (String idCard : idTrelloCardList) {
            List<CommentTrelloDto> commentTrelloDtos = commentTrelloController.getCommentsFromCardByIdCard(idCard);
            commentList.addAll(commentTrelloDtos);
        }

        for (CommentTrelloDto commentTrelloDto : commentList) {
            CommentDTO commentDTO = commentTrelloDto.trellotoDto();
            Comment comment = commentDTO.toModel();
            commentService.insertComment(comment);
        }

    }

    public void insertBoardFromTrelloToDb(String idBoard) {
        BoardTrelloDTO boardTrelloDTO = boardTrelloController.getBoardFromTrello(idBoard);
        BoardDTO boardDTO = boardTrelloDTO.trellotoDto();
        Board board = boardDTO.toModel();
        Board existingBoard=boardService.getBoardByIdTrelloFromDb(idBoard);
        if (existingBoard == null) {
            board.setDateLastActivity(LocalDateTime.now());
            boardService.insert(board);
        } else {
            board.setId(existingBoard.getId());
            board.setDateLastActivity(LocalDateTime.now());
            boardService.update(board);
        }
    }

    public void insertLabelsFromTrelloToDb(String idBoard) {
        Long idBoardFromDB = boardService.getBoardByIdTrelloFromDb(idBoard).getId();
        labelsTrelloController.getLabelsFromTrelloBoard(idBoard).stream()
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

    public void insertUsersFromTrelloToDb(String idBoard) {
       userTrelloController.getAllUsersFromBoard(idBoard).stream()
                .map(UserTrelloDto::trellotoDto)
                .map(UserDTO::toModel)
                .forEach(user -> {
                    if (userService.getUserByIdTrelloFromDb(user.getIdTrello())==null){
                        userService.insert(user);
                    }else {
                        userService.update(user);
                    }});

    }

    public void insertListsFromTrelloToDb(String idBoard) {
        Long idBoardFromDB = boardService.getBoardByIdTrelloFromDb(idBoard).getId();
        listsTrelloController.getAllListsFromBoard(idBoard).stream()
                .map(ListsTrelloDto::trellotoDto)
                .map(ListsDTO::toModel)
                .forEach(lists -> {
                    if (!(listsService.getAllIdTrelloForLists().contains(lists.getIdTrello()))) {
                        lists.setBoard(Board.builder().id(idBoardFromDB).build());
                        listsService.insert(lists);
                    }
                });
    }
    public void insertCardsByIdListAndIdLabel(String idList, String idLabel) {
        Long idListFromDb = listsService.getListByIdTrelloFromDb(idList).getId();
        Long idLabelFromDb = labelsService.getLabelByIdTrelloFromDb(idLabel).getId();
        cardTrelloController.getCardsFromTrelloByIdList(idList).stream()
                .map(CardTrelloDto::trellotoDto)
                .map(CardDTO::toModel)
                .forEach(card -> {
                    card.setList(Lists.builder().id(idListFromDb).build());
                    card.setLabels(Labels.builder().id(idLabelFromDb).build());
                    cardService.insert(card);
                });
    }

    public void insertCardsFromTrelloToDb(String idBoard) {
        List<CardTrelloDto> cardTrelloDtos = new ArrayList<>();

        for (String idList : getAllIdTrelloForListsFromDb()) {
            List<CardTrelloDto> cardsList = cardTrelloController.getCardsFromTrelloByIdList(idList);
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
    private List<String> getAllIdTrelloForListsFromDb() {
        return listsService.getAllIdTrelloForLists();
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
//            cardService.insertIntoDb(card.trellotoDto().toModel());
        }

    }


    private List<String> getAllIdTrelloForLabelsFromDb() {
        return labelsService.getAllIdTrelloForLabels();
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

    @PostMapping("/comment")
    public CommentDTO addCommentToCardByIdCard(String idCardTrello, String userName, String commentBody) {
//        String idUser = userService.getUserByUserName(userName).getId();
        CommentDTO commentToInsert = CommentDTO.builder().idCard(idCardTrello)
                .commentBody(commentBody)
                .idCard(idCardTrello)
//                .idUser(idUser)
                .date(localDateTimeToString(LocalDateTime.now()))
                .idTrello("testIdTrello")
                .build();
        commentService.insert(commentToInsert.toModel());
        return commentToInsert;
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