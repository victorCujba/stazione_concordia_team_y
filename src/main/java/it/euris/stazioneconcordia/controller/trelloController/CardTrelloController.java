package it.euris.stazioneconcordia.controller.trelloController;

import it.euris.stazioneconcordia.data.model.Board;
import it.euris.stazioneconcordia.data.model.Card;
import it.euris.stazioneconcordia.data.trelloDto.CardTrelloDto;
import it.euris.stazioneconcordia.service.CardService;
import it.euris.stazioneconcordia.service.trelloService.CardTrelloService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/v1/from-trello/cards")
public class CardTrelloController {
    private CardTrelloService cardTrelloService;
    private CardService cardService;
    public List<CardTrelloDto> getCardsFromTrelloList(String idTrelloList) {
        return cardTrelloService.getCardsByIdList(idTrelloList);
    }
    public List<CardTrelloDto> getCardsFromTrelloByIdList(String idList) {
        return cardTrelloService.getCardsByIdList(idList);
    }
    public void findIfExistANewCardOfDBAndPutOnTrello(){
        List<Card> cardsOfDB =cardService.findAll();
        cardsOfDB.forEach(card -> {
            if(card.getIdTrello().isEmpty()){
                insertNewCardsFromDbToTrello(card.getId());
                cardService.deleteById(card.getId());
            }
        });
    }
    @PutMapping("/update-cards-to-trello")
    public void updateCardsFromDbToTrello(@RequestParam Long idCard) {
        CardTrelloDto cardTrelloDto= cardService.findById(idCard).toDto().toTrelloDto();
        Card card =cardService.findById(idCard);

        cardTrelloDto
                .setIdList(cardService
                        .findById(card.getId())
                        .getList()
                        .getIdTrello());
        cardTrelloDto
                .setIdLabels(Collections.singletonList(card.getLabels().getIdTrello()));
        cardTrelloService.updateACard(card.getIdTrello(),cardTrelloDto);
    }

    @PostMapping("/insert-new-cards-to-trello")
    public void insertNewCardsFromDbToTrello(@RequestParam Long idCard) {
        CardTrelloDto cardTrelloDto= cardService.findById(idCard).toDto().toTrelloDto();
        Card card =cardService.findById(idCard);

        cardTrelloDto
                .setIdList(card
                        .getList()
                        .getIdTrello());
        cardTrelloDto
                .setIdLabels(Collections.singletonList(card.getLabels().getIdTrello()));
        cardTrelloService.createACard(cardTrelloDto);
    }
    @DeleteMapping("/delete-a-cards-to-trello")
    public void deleteACardsToTrello(@RequestParam Long idCard) {
        Card card = cardService.findById(idCard);

        cardTrelloService.deleteACard(card.getIdTrello());
    }
    public void cardCompareToCard(Card newCard, Card existingCard ){

        if (newCard.getDateLastActivity().isAfter(existingCard.getDateLastActivity())||
                newCard.getDateLastActivity().isEqual(existingCard.getDateLastActivity() )){

            newCard.setId(existingCard.getId());
            newCard.setDateLastActivity(LocalDateTime.now());
            cardService.update(newCard);

        }else{
            updateCardsFromDbToTrello(existingCard.getId());
        }
    }


}
