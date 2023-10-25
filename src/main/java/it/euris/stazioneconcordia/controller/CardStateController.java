package it.euris.stazioneconcordia.controller;

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
import java.util.List;


@RestController
@RequestMapping("/cards-state")
public class CardStateController {

    private CardService cardService;

    private ListsService listsService;

    private CardStateService cardStateService;

    public CardStateController(CardService cardService, CardStateService cardStateService, ListsService listsService) {
        this.cardService = cardService;
        this.cardStateService = cardStateService;
        this.listsService = listsService;
    }

    @GetMapping("/v1")
    public List<CardStateDTO> findAll() {
        return cardStateService.findAll().stream().map(CardState::toDto).toList();
    }

    @PutMapping("/v1/move-card")
    public void updateCardState(@RequestParam String idCard, @RequestParam ListLabel toListLabel) {

        Card card = cardService.findById(idCard);
        Lists fromList = card.getList();
        ListLabel fromListLabel = fromList.getLabel();
        Lists toList = listsService.findByLabel(toListLabel);

        CardState cardState = CardState
                .builder()
                .id(card.getId())
                .card(card)
                .dateLastUpdate(LocalDateTime.now())
                .fromList(fromListLabel)
                .toList(toListLabel)
                .build();


        card.getStateHistory().add(cardState);
        cardStateService.update(cardState);
        card.setDateLastActivity(LocalDateTime.now());
        card.setList(toList);
        cardService.update(card);


    }
}
