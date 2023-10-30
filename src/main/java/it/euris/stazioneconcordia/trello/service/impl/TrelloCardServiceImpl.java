package it.euris.stazioneconcordia.trello.service.impl;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.euris.stazioneconcordia.data.dto.CardDTO;
import it.euris.stazioneconcordia.data.trelloDto.CardTrelloDto;
import it.euris.stazioneconcordia.trello.service.TrelloCardService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.List;

import static it.euris.stazioneconcordia.trello.utils.TrelloConstants.*;

@Service
public class TrelloCardServiceImpl implements TrelloCardService {


    private final RestTemplate restTemplate;

    private final Gson gson;

    public TrelloCardServiceImpl() {
        this.restTemplate = new RestTemplate();
        this.gson = new Gson();
    }

    public List<CardTrelloDto> getCardsByIdBoard(String idBoard) {

        String url = buildUrlGetCardsByIdBoardRequest(idBoard);
        String json = getJsonFromRequest(url);

        List<CardTrelloDto> cardTrelloDtos = gson.fromJson(json, listCardsDtoTypeClass());
        if (cardTrelloDtos == null) {
            System.out.println("no cards found");
            return null;
        }
        return cardTrelloDtos;
    }


    public List<CardTrelloDto> getCardsByIdLists(String idList) {

        String url = buildUrlGetCardsByIdListRequest(idList);
        String json = getJsonFromRequest(url);

        List<CardTrelloDto> cardTrelloDtos = gson.fromJson(json, listCardsDtoTypeClass());
        if (cardTrelloDtos == null) {
            System.out.println("no cards found");
            return null;
        }
        System.out.println(cardTrelloDtos);
        return cardTrelloDtos;
    }

    public CardTrelloDto getCardByIdCard(String idCard) {

        String url = buildUrlGetCardByIdCardRequest(idCard);
        String json = getJsonFromRequest(url);

        CardTrelloDto cardTrelloDto = gson.fromJson(json, cardDtoTypeClass());
        if (cardTrelloDto == null) {
            System.out.println("no cards found");
            return null;
        }

        return cardTrelloDto;
    }

    public List<CardTrelloDto> getHighPriorityCardsByIdBoard(String idBoard) {
        String url = buildUrlGetCardsByIdBoardRequest(idBoard);
        String json = getJsonFromRequest(url);
        String highPriorityLabel = "652d5736ec602d3f7cd4c6ac";


        List<CardTrelloDto> cardTrelloDtos = gson.fromJson(json, listCardsDtoTypeClass());

        return getCardFilteredByPriorityLabelId(highPriorityLabel, cardTrelloDtos);

    }

    public List<CardTrelloDto> getHighPriorityCardByIdList(String idList) {
        String url = buildUrlGetCardsByIdListRequest(idList);
        String json = getJsonFromRequest(url);
        String mediumPriority = "652d5736ec602d3f7cd4c6af";

        List<CardTrelloDto> cardTrelloDtos = gson.fromJson(json, listCardsDtoTypeClass());

        return getCardFilteredByPriorityLabelId(mediumPriority, cardTrelloDtos);

    }

    public List<CardTrelloDto> getMediumPriorityCardsByIdBoard(String idBoard) {

        String url = buildUrlGetCardsByIdBoardRequest(idBoard);
        String json = getJsonFromRequest(url);
        String highPriorityLabel = "652d5736ec602d3f7cd4c6ac";


        List<CardTrelloDto> cardTrelloDtos = gson.fromJson(json, listCardsDtoTypeClass());

        return getCardFilteredByPriorityLabelId(highPriorityLabel, cardTrelloDtos);

    }

    public List<CardTrelloDto> getMediumPriurityCardsByIdList(String idList) {
        String url = buildUrlGetCardsByIdListRequest(idList);
        String json = getJsonFromRequest(url);
        String mediumPriorityLabel = "652d5736ec602d3f7cd4c6af";

        List<CardTrelloDto> cardTrelloDtos = gson.fromJson(json, listCardsDtoTypeClass());

        return getCardFilteredByPriorityLabelId(mediumPriorityLabel, cardTrelloDtos);
    }

    public List<CardTrelloDto> getLowPriorityCardsByIdBoard(String idBoard) {
        String url = buildUrlGetCardsByIdBoardRequest(idBoard);
        String json = getJsonFromRequest(url);
        String lowPriorityLabel = "652d5736ec602d3f7cd4c6b2";

        List<CardTrelloDto> cardTrelloDtos = gson.fromJson(json, listCardsDtoTypeClass());

        return getCardFilteredByPriorityLabelId(lowPriorityLabel, cardTrelloDtos);
    }

    public List<CardTrelloDto> getLowPriorityCardsByIdList(String idList) {
        String url = buildUrlGetCardsByIdListRequest(idList);
        String json = getJsonFromRequest(url);
        String lowPriorityLabel = "652d5736ec602d3f7cd4c6b2";

        List<CardTrelloDto> cardTrelloDtos = gson.fromJson(json, listCardsDtoTypeClass());

        return getCardFilteredByPriorityLabelId(lowPriorityLabel, cardTrelloDtos);
    }

    public List<CardTrelloDto> getDesiredPriorityCardsByIdBoard(String idBoard) {
        String url = buildUrlGetCardsByIdBoardRequest(idBoard);
        String json = getJsonFromRequest(url);
        String desiredPriorityLabel = "652d5736ec602d3f7cd4c6b5";

        List<CardTrelloDto> cardTrelloDtos = gson.fromJson(json, listCardsDtoTypeClass());

        return getCardFilteredByPriorityLabelId(desiredPriorityLabel, cardTrelloDtos);
    }

    public List<CardTrelloDto> getDesiredPriorityCardsByIdList(String idList) {
        String url = buildUrlGetCardsByIdListRequest(idList);
        String json = getJsonFromRequest(url);
        String desiredPriorityLabel = "652d5736ec602d3f7cd4c6b5";

        List<CardTrelloDto> cardTrelloDtos = gson.fromJson(json, listCardsDtoTypeClass());

        return getCardFilteredByPriorityLabelId(desiredPriorityLabel, cardTrelloDtos);
    }

    public List<CardTrelloDto> getExpiringIn5DaysCardsByIdBoard(String idBoard) {
        String url = buildUrlGetCardsByIdBoardRequest(idBoard);
        String json = getJsonFromRequest(url);

        List<CardTrelloDto> cardTrelloDtos = gson.fromJson(json, listCardsDtoTypeClass());

        List<CardTrelloDto> expiringIn5DaysCards = getExpiringIn5DaysCardsFromToday(cardTrelloDtos);
        System.out.println(expiringIn5DaysCards);
        return expiringIn5DaysCards;
    }


    public List<CardTrelloDto> getExpiringIn5DaysCardsByIdList(String idList) {
        String url = buildUrlGetCardsByIdListRequest(idList);
        String json = getJsonFromRequest(url);

        List<CardTrelloDto> cardTrelloDtos = gson.fromJson(json, listCardsDtoTypeClass());

        List<CardTrelloDto> expiringIn5DaysCards = getExpiringIn5DaysCardsFromToday(cardTrelloDtos);
        System.out.println(expiringIn5DaysCards);
        return expiringIn5DaysCards;

    }

    private static List<CardTrelloDto> getExpiringIn5DaysCardsFromToday(List<CardTrelloDto> cardTrelloDtos) {
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime localDateTimeIn5Days = LocalDateTime.now().plusDays(5L);

        List<CardTrelloDto> expiringIn5DaysCards = cardTrelloDtos.stream()
                .filter(cardTrelloDto -> {
                    if (cardTrelloDto.getDue() != null) {
                        LocalDateTime expirationDate = LocalDateTime.parse(cardTrelloDto.getDue().substring(0, 19));
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

    private static List<CardTrelloDto> getCardFilteredByPriorityLabelId(String idLabel, List<CardTrelloDto> cardTrelloDtos) {
        List<CardTrelloDto> highPriorityCards = cardTrelloDtos
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