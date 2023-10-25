package it.euris.stazioneconcordia.controller;


import it.euris.stazioneconcordia.data.dto.CardDTO;
import it.euris.stazioneconcordia.data.model.Card;
import it.euris.stazioneconcordia.exception.IdMustBeNullException;
import it.euris.stazioneconcordia.exception.IdMustNotBeNullException;
import it.euris.stazioneconcordia.service.CardService;
import it.euris.stazioneconcordia.service.ListsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/cards")
public class CardController {

    CardService cardService;

    ListsService listsService;

    @GetMapping("/v1")
    public List<CardDTO> findAll() {
        return cardService.findAll().stream().map(Card::toDto).toList();
    }

    @PostMapping("/v1")
    public CardDTO insert(@RequestBody CardDTO cardDTO) {
        try {
            Card card = cardDTO.toModel();
            return cardService.insert(card).toDto();
        } catch (IdMustBeNullException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage()
            );
        }
    }

    @PutMapping("/v1")
    public CardDTO update(@RequestBody CardDTO cardDTO) {
        try {
            Card card = cardDTO.toModel();
            return cardService.update(card).toDto();
        } catch (IdMustNotBeNullException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage()
            );
        }
    }

    @DeleteMapping("/v1/{id}")
    public Boolean deleteById(@PathVariable("id") String idCard) {
        return cardService.deleteById(idCard);
    }

    @GetMapping("/v1/{id}")
    public CardDTO findById(@PathVariable("id") String idCard) {
        return cardService.findById(idCard).toDto();
    }

    @GetMapping("/v1/expiration-date")
    public List<CardDTO> findByExpirationDate() {
        return cardService.findAllCardsWhitExpirationDateInLast5Days()
                .stream()
                .map(Card::toDto)
                .toList();
    }

    @GetMapping("/v1/labels/{id-labels}")
    public List<CardDTO> findByLabels(@PathVariable("id-labels") String idLabels) {
        return cardService.findByLabels(idLabels)
                .stream()
                .map(Card::toDto)
                .toList();
    }


}
