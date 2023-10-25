package it.euris.stazioneconcordia.trello.service.impl;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.euris.stazioneconcordia.data.dto.CardDTO;
import it.euris.stazioneconcordia.data.dto.CommentDTO;
import it.euris.stazioneconcordia.trello.service.TrelloCardService;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.lang.reflect.Type;
import java.util.List;

import static it.euris.stazioneconcordia.trello.utils.TrelloConstants.*;
import static it.euris.stazioneconcordia.trello.utils.TrelloConstants.TOKEN_VALUE;

@Service
public class TrelloCardServiceImpl implements TrelloCardService {


    private final RestTemplate restTemplate;

    private final Gson gson;

    public TrelloCardServiceImpl() {
        this.restTemplate = new RestTemplate();
        this.gson = new Gson();
    }

    public List<CardDTO> getCardsByIdBoard(String idBoard) {

        String url = UriComponentsBuilder.fromHttpUrl(URL_API_TRELLO_GET_CARDS_BY_ID_BOARD)
                .buildAndExpand(idBoard, KEY_VALUE, TOKEN_VALUE)
                .toString();

        String json = restTemplate.getForObject(url, String.class);
        Type listType = new TypeToken<List<CardDTO>>() {
        }.getType();
        List<CardDTO> cardDTOs = gson.fromJson(json, listType);

        if (cardDTOs == null) {
            System.out.println("no cards found");
            return null;
        }

        System.out.println(cardDTOs.toString());
        System.out.println(listType.toString());
        return cardDTOs;

    }

    public List<CardDTO> getCardsByIdLists(String idList) {
        String url = UriComponentsBuilder.fromHttpUrl(URL_API_TRELLO_GET_CARDS_BY_ID_LISTS)
                .buildAndExpand(idList, KEY_VALUE, TOKEN_VALUE)
                .toString();
        String json = restTemplate.getForObject(url, String.class);

        Type listType = new TypeToken<List<CardDTO>>() {
        }.getType();
        List<CardDTO> cardDTOS = gson.fromJson(json, listType);
        if (cardDTOS == null) {
            System.out.println("no cards found");
            return null;
        }
        System.out.println(cardDTOS.toString());
        return cardDTOS;
    }

    public CardDTO getCardByIdCard(String idCard) {
        String url = UriComponentsBuilder.fromHttpUrl(URL_API_TRELLO_GET_CARD_BY_ID_CARD)
                .buildAndExpand(idCard, KEY_VALUE, TOKEN_VALUE)
                .toString();

        String json = restTemplate.getForObject(url, String.class);

        Type listType = new TypeToken<CardDTO>() {
        }.getType();
        CardDTO cardDTO = gson.fromJson(json, listType);
        if (cardDTO == null) {
            System.out.println("no cards found");
            return null;
        }
        System.out.println(cardDTO.toString());
        return cardDTO;
    }

    public List<CommentDTO> getAllCommentsByIdCard(String idCard) {
        String url = UriComponentsBuilder.fromHttpUrl(URL_API_TRELLO_GET_COMMENTS_BY_ID_CARD)
                .buildAndExpand(idCard, KEY_VALUE, TOKEN_VALUE)
                .toString();

        String json = restTemplate.getForObject(url, String.class);
        System.out.println(json.toString());
        Type listType = new TypeToken<List<CommentDTO>>() {
        }.getType();
        List<CommentDTO> commentsDTO = gson.fromJson(json, listType);
        for (CommentDTO commentDTO : commentsDTO)

            System.out.println(commentsDTO.toString());
        return commentsDTO;

    }


    public static void main(String[] args) {

        TrelloCardServiceImpl trelloCardService = new TrelloCardServiceImpl();
        trelloCardService.getCardsByIdBoard(BOARD_ID_VALUE);
// trelloCardService.getCardsByIdLists(LIST_O1_ID_VALUE);
//        trelloCardService.getCardByIdCard(CARD_01_ID_VALUE);
//        trelloCardService.getAllCommentsByIdCard(CARD_01_ID_VALUE);
    }


}