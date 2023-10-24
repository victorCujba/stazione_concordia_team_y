package it.euris.stazioneconcordia.controller;

import it.euris.stazioneconcordia.data.dto.BoardDTO;
import it.euris.stazioneconcordia.data.dto.CardDTO;
import it.euris.stazioneconcordia.data.dto.ListsDTO;
import it.euris.stazioneconcordia.data.model.Card;
import it.euris.stazioneconcordia.data.model.Lists;
import it.euris.stazioneconcordia.service.BoardService;
import it.euris.stazioneconcordia.service.CardService;
import it.euris.stazioneconcordia.service.ListsService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/v1/from-trello")
public class TrelloController {

    BoardService boardService;

    ListsService listsService;

    CardService cardService;

    @GetMapping("/board")
    public BoardDTO getBoardFromTrello(@RequestParam String idBoard, @RequestParam String key, @RequestParam String token) {
        return boardService.getBoardFromTrello(idBoard, key, token).toDto();
    }

    @GetMapping("/list")
    public ListsDTO[] getListsFromTrelloBoard(@RequestParam String idBoard, @RequestParam String key, @RequestParam String token) {
        Lists[] lists = listsService.getListFromTrelloBoard(idBoard, key, token);
        ListsDTO[] listsDTOs = new ListsDTO[lists.length];
        for (int i = 0; i < lists.length; i++) {
            listsDTOs[i] = lists[i].toDto();
        }
    return listsDTOs;
    }

    @GetMapping("/cards")
    public CardDTO[] getCardsFromTrelloList(@RequestParam String idList, @RequestParam String key, @RequestParam String token) {
        Card[] cards = cardService.getCardsFromTrelloList(idList, key, token);
        CardDTO[] cardDTOs = new CardDTO[cards.length];
        for (int i = 0; i < cards.length; i++) {
            cardDTOs[i] = cards[i].toDto();
        }
        return cardDTOs;
    }
}
