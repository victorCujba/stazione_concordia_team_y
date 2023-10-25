package it.euris.stazioneconcordia.controller;

import it.euris.stazioneconcordia.data.dto.CardDTO;
import it.euris.stazioneconcordia.data.dto.CardStateDTO;
import it.euris.stazioneconcordia.data.enums.ListLabel;
import it.euris.stazioneconcordia.data.model.Card;
import it.euris.stazioneconcordia.data.model.CardState;
import it.euris.stazioneconcordia.data.model.Lists;
import it.euris.stazioneconcordia.service.CardService;
import it.euris.stazioneconcordia.service.CardStateService;
import it.euris.stazioneconcordia.service.ListsService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@RestController
@RequestMapping("/cards-state")
public class CardStateController {


    // TODO implement counter more elegant
    private static long cardStateIdCounter = 0;

    CardService cardService;

    ListsService listsService;

    CardStateService cardStateService;

    public CardStateController(CardService cardService, CardStateService cardStateService, ListsService listsService) {
        this.cardService = cardService;
        this.cardStateService = cardStateService;
        this.listsService = listsService;
    }

    @GetMapping("/v1")
    public List<CardStateDTO> findAll() {
        return cardStateService.findAll().stream().map(CardState::toDto).toList();
    }

    @GetMapping("/v1/{idCard}")
    public List<CardStateDTO> findCardStateByIdCard(@PathVariable("idCard") String idCard) {
        Card card = cardService.findById(idCard);

        if (card.getStateHistory() == null || card.getStateHistory().isEmpty()) {
            return Collections.emptyList();
        }
        List<CardStateDTO> cardStateDTOList = new ArrayList<>();
        for (CardState cardState : card.getStateHistory()) {
            cardStateDTOList.add(cardState.toDto());
        }
        return cardStateDTOList;
    }

    @PutMapping("/v1/move-card")
    public void updateCardState(@RequestParam String idCard, @RequestParam ListLabel toListLabel) {

        Card card = cardService.findById(idCard);
        Lists fromList = card.getList();
        ListLabel fromListLabel = fromList.getLabel();
        Lists toList = listsService.findByLabel(toListLabel);

        cardStateIdCounter++;

        CardState cardState = CardState
                .builder()
                .id(String.valueOf(cardStateIdCounter))
                .card(card)
                .dateLastUpdate(LocalDateTime.now())
                .fromList(fromListLabel)
                .toList(toListLabel)
                .build();

        if (card.getStateHistory().isEmpty()) {
            List<CardState> stateHistory = new ArrayList<>();
            stateHistory.add(cardState);
            card.setStateHistory(stateHistory);
        } else {
            card.getStateHistory().add(cardState);
        }


        card.getStateHistory().add(cardState);
        cardStateService.update(cardState);
        card.setDateLastActivity(LocalDateTime.now());
        card.setList(toList);
        cardService.update(card);


    }

    @GetMapping("/v1/card-by-id-list")
    public List<CardDTO> findCardsByListId(String idList) {
        Lists lists = listsService.findById(idList);
        List<Card> cards = lists.getCards();
        return cards.stream().map(Card::toDto).toList();
    }
}
