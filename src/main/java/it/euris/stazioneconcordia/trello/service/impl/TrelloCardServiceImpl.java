package it.euris.stazioneconcordia.trello.service.impl;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.euris.stazioneconcordia.data.dto.CardDTO;
import it.euris.stazioneconcordia.trello.service.TrelloCardService;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.lang.reflect.Type;
import java.sql.SQLOutput;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.List;

import static it.euris.stazioneconcordia.trello.utils.TrelloConstants.*;
import static it.euris.stazioneconcordia.trello.utils.TrelloConstants.TOKEN_VALUE;
import static it.euris.stazioneconcordia.utility.DataConversionUtils.stringToLocalDateTime;

@Service
public class TrelloCardServiceImpl implements TrelloCardService {


    private final RestTemplate restTemplate;

    private final Gson gson;

    public TrelloCardServiceImpl() {
        this.restTemplate = new RestTemplate();
        this.gson = new Gson();
    }

    public List<CardDTO> getCardsByIdBoard(String idBoard) {

        String url = buildUrlGetCardsByIdBoardRequest(idBoard);
        String json = getJsonFromRequest(url);

        List<CardDTO> cardDTOs = gson.fromJson(json, listCardsDtoTypeClass());
        if (cardDTOs == null) {
            System.out.println("no cards found");
            return null;
        }
        return cardDTOs;
    }


    public List<CardDTO> getCardsByIdLists(String idList) {

        String url = buildUrlGetCardsByIdListRequest(idList);
        String json = getJsonFromRequest(url);

        List<CardDTO> cardDTOS = gson.fromJson(json, listCardsDtoTypeClass());
        if (cardDTOS == null) {
            System.out.println("no cards found");
            return null;
        }
        System.out.println(cardDTOS);
        return cardDTOS;
    }

    public CardDTO getCardByIdCard(String idCard) {

        String url = buildUrlGetCardByIdCardRequest(idCard);
        String json = getJsonFromRequest(url);

        CardDTO cardDTO = gson.fromJson(json, cardDtoTypeClass());
        if (cardDTO == null) {
            System.out.println("no cards found");
            return null;
        }

        return cardDTO;
    }

    public List<CardDTO> getHighPriorityCardsByIdBoard(String idBoard) {
        String url = buildUrlGetCardsByIdBoardRequest(idBoard);
        String json = getJsonFromRequest(url);
        String highPriorityLabel = "652d5736ec602d3f7cd4c6ac";


        List<CardDTO> cardDTOS = gson.fromJson(json, listCardsDtoTypeClass());

        return getCardFilteredByPriorityLabelId(highPriorityLabel, cardDTOS);

    }

    public List<CardDTO> getHighPriorityCardByIdList(String idList) {
        String url = buildUrlGetCardsByIdListRequest(idList);
        String json = getJsonFromRequest(url);
        String mediumPriority = "652d5736ec602d3f7cd4c6af";

        List<CardDTO> cardDTOS = gson.fromJson(json, listCardsDtoTypeClass());

        return getCardFilteredByPriorityLabelId(mediumPriority, cardDTOS);

    }

    public List<CardDTO> getMediumPriorityCardsByIdBoard(String idBoard) {

        String url = buildUrlGetCardsByIdBoardRequest(idBoard);
        String json = getJsonFromRequest(url);
        String highPriorityLabel = "652d5736ec602d3f7cd4c6ac";


        List<CardDTO> cardDTOS = gson.fromJson(json, listCardsDtoTypeClass());

        return getCardFilteredByPriorityLabelId(highPriorityLabel, cardDTOS);

    }

    public List<CardDTO> getMediumPriurityCardsByIdList(String idList) {
        String url = buildUrlGetCardsByIdListRequest(idList);
        String json = getJsonFromRequest(url);
        String mediumPriorityLabel = "652d5736ec602d3f7cd4c6af";

        List<CardDTO> cardDTOS = gson.fromJson(json, listCardsDtoTypeClass());

        return getCardFilteredByPriorityLabelId(mediumPriorityLabel, cardDTOS);
    }

    public List<CardDTO> getLowPriorityCardsByIdBoard(String idBoard) {
        String url = buildUrlGetCardsByIdBoardRequest(idBoard);
        String json = getJsonFromRequest(url);
        String lowPriorityLabel = "652d5736ec602d3f7cd4c6b2";

        List<CardDTO> cardDTOS = gson.fromJson(json, listCardsDtoTypeClass());

        return getCardFilteredByPriorityLabelId(lowPriorityLabel, cardDTOS);
    }

    public List<CardDTO> getLowPriorityCardsByIdList(String idList) {
        String url = buildUrlGetCardsByIdListRequest(idList);
        String json = getJsonFromRequest(url);
        String lowPriorityLabel = "652d5736ec602d3f7cd4c6b2";

        List<CardDTO> cardDTOS = gson.fromJson(json, listCardsDtoTypeClass());

        return getCardFilteredByPriorityLabelId(lowPriorityLabel, cardDTOS);
    }

    public List<CardDTO> getDesiredPriorityCardsByIdBoard(String idBoard) {
        String url = buildUrlGetCardsByIdBoardRequest(idBoard);
        String json = getJsonFromRequest(url);
        String desiredPriorityLabel = "652d5736ec602d3f7cd4c6b5";

        List<CardDTO> cardDTOS = gson.fromJson(json, listCardsDtoTypeClass());

        return getCardFilteredByPriorityLabelId(desiredPriorityLabel, cardDTOS);
    }

    public List<CardDTO> getDesiredPriorityCardsByIdList(String idList) {
        String url = buildUrlGetCardsByIdListRequest(idList);
        String json = getJsonFromRequest(url);
        String desiredPriorityLabel = "652d5736ec602d3f7cd4c6b5";

        List<CardDTO> cardDTOS = gson.fromJson(json, listCardsDtoTypeClass());

        return getCardFilteredByPriorityLabelId(desiredPriorityLabel, cardDTOS);
    }

    public List<CardDTO> getExpiringIn5DaysCardsByIdBoard(String idBoard) {
        String url = buildUrlGetCardsByIdBoardRequest(idBoard);
        String json = getJsonFromRequest(url);

        List<CardDTO> cardDTOS = gson.fromJson(json, listCardsDtoTypeClass());

        List<CardDTO> expiringIn5DaysCards = getExpiringIn5DaysCardsFromToday(cardDTOS);
        System.out.println(expiringIn5DaysCards);
        return expiringIn5DaysCards;
    }


    public List<CardDTO> getExpiringIn5DaysCardsByIdList(String idList) {
        String url = buildUrlGetCardsByIdListRequest(idList);
        String json = getJsonFromRequest(url);

        List<CardDTO> cardDTOS = gson.fromJson(json, listCardsDtoTypeClass());

        List<CardDTO> expiringIn5DaysCards = getExpiringIn5DaysCardsFromToday(cardDTOS);
        System.out.println(expiringIn5DaysCards);
        return expiringIn5DaysCards;

    }

    private static List<CardDTO> getExpiringIn5DaysCardsFromToday(List<CardDTO> cardDTOS) {
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime localDateTimeIn5Days = LocalDateTime.now().plusDays(5L);

        List<CardDTO> expiringIn5DaysCards = cardDTOS.stream()
                .filter(cardDTO -> {
                    if (cardDTO.getExpirationDate() != null) {
                        LocalDateTime expirationDate = LocalDateTime.parse(cardDTO.getExpirationDate().substring(0, 19));
                        return today.isBefore(expirationDate)
                                && localDateTimeIn5Days.isAfter(expirationDate);
                    }
                    return false;
                }).toList();

        if (expiringIn5DaysCards.isEmpty()) {
            System.out.println("There are no cards which expire in 5 days!!!");
        }
        return expiringIn5DaysCards;
    }

    private static List<CardDTO> getCardFilteredByPriorityLabelId(String idLabel, List<CardDTO> cardDTOS) {
        List<CardDTO> highPriorityCards = cardDTOS
                .stream()
                .filter(cardDTO -> cardDTO.getIdLabels().contains(idLabel))
                .toList();
        if (!highPriorityCards.isEmpty()) {
            return highPriorityCards;
        }
        System.out.println(highPriorityCards);
        System.out.printf("There are no cards with selected priority " + idLabel);
        return null;
    }

    private static String buildUrlGetCardByIdCardRequest(String idCard) {
        return UriComponentsBuilder.fromHttpUrl(URL_API_TRELLO_GET_CARD_BY_ID_CARD)
                .buildAndExpand(idCard, KEY_VALUE, TOKEN_VALUE)
                .toString();
    }

    private static String buildUrlGetCardsByIdBoardRequest(String idBoard) {
        return UriComponentsBuilder.fromHttpUrl(URL_API_TRELLO_GET_CARDS_BY_ID_BOARD)
                .buildAndExpand(idBoard, KEY_VALUE, TOKEN_VALUE)
                .toString();
    }

    private static String buildUrlGetCardsByIdListRequest(String idList) {
        return UriComponentsBuilder.fromHttpUrl(URL_API_TRELLO_GET_CARDS_BY_ID_LISTS)
                .buildAndExpand(idList, KEY_VALUE, TOKEN_VALUE)
                .toString();
    }

    private String getJsonFromRequest(String url) {
        return restTemplate.getForObject(url, String.class);
    }

    private static Type listCardsDtoTypeClass() {
        return new TypeToken<List<CardDTO>>() {
        }.getType();
    }

    private static Type cardDtoTypeClass() {
        return new TypeToken<CardDTO>() {
        }.getType();
    }

    public static void main(String[] args) {

        TrelloCardServiceImpl trelloCardService = new TrelloCardServiceImpl();
//        trelloCardService.getCardsByIdBoard(BOARD_ID_VALUE);
        trelloCardService.getCardsByIdLists(LIST_O1_ID_VALUE);
//      trelloCardService.getCardByIdCard(CARD_01_ID_VALUE);
//      trelloCardService.getAllCommentsByIdCard(CARD_01_ID_VALUE);
        //     trelloCardService.getHighPriorityCardsByIdBoard(BOARD_ID_VALUE);
//        trelloCardService.getHighPriorityCardByIdList(LIST_O1_ID_VALUE);
        // trelloCardService.getExpiringIn5DaysCardsByIdBoard(BOARD_ID_VALUE);
//        trelloCardService.getExpiringIn5DaysCardsByIdList(LIST_O3_ID_VALUE);
    }


}