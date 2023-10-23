package it.euris.stazioneconcordia.controller;

import it.euris.stazioneconcordia.data.dto.CardStateDTO;
import it.euris.stazioneconcordia.data.enums.ListLabel;
import it.euris.stazioneconcordia.data.model.CardState;
import it.euris.stazioneconcordia.service.CardStateService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/cards-state")
public class CardStateController {

    CardStateService cardStateService;

    @GetMapping("/v1")
    public List<CardStateDTO> findAll() {
        return cardStateService.findAll().stream().map(CardState::toDto).toList();
    }

    @PutMapping("/v1/{idCard}")
    public CardStateDTO updateCardState(
            @PathVariable("idCard") String idCard,
            @RequestParam ListLabel fromList,
            @RequestParam ListLabel toList
    ) {
        return cardStateService.updateCardState(idCard, fromList, toList);
    }

}
