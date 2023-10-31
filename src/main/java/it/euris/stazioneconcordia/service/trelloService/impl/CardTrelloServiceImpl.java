package it.euris.stazioneconcordia.service.trelloService.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.euris.stazioneconcordia.data.dto.CardDTO;
import it.euris.stazioneconcordia.data.enums.TaskPriorityLabel;
import it.euris.stazioneconcordia.data.trelloDto.CardTrelloDto;
import it.euris.stazioneconcordia.data.trelloDto.LabelsTrelloDto;
import it.euris.stazioneconcordia.service.trelloService.CardTrelloService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static it.euris.stazioneconcordia.trello.utils.TrelloConstants.*;

@Service
@AllArgsConstructor
public class CardTrelloServiceImpl implements CardTrelloService {

    @Autowired
    private LabelsTrelloServiceImpl labelsTrelloService;

    private final Gson gson;

    public CardTrelloServiceImpl(Gson gson) {
        this.gson = new Gson();
    }

    @Override
    @SneakyThrows
    public List<CardTrelloDto> getCardsByIdBoard(String idTrelloBoard) {

        URI targetURI = new URI(buildUrlGetCardsFromTrelloByIdBoard(idTrelloBoard));
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(targetURI)
                .GET()
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        Type listType = getListType();

        return gson.fromJson(response.body(), listType);

    }


    @Override
    @SneakyThrows
    public List<CardTrelloDto> getCardsByIdList(String idTrelloList) {

        URI targetURI = new URI(buildUrlGetCardsFromTrelloByIdListsRequest(idTrelloList));
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(targetURI)
                .GET()
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        Type listType = getListType();

        return gson.fromJson(response.body(), listType);
    }


    @Override
    @SneakyThrows
    public CardTrelloDto getCardByIdCard(String idCard) {

        URI targetUri = new URI(buildUrlGetCardFromTrelloByIdCard(idCard));
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(targetUri)
                .GET()
                .build();
        HttpClient httpClient = HttpClient.newHttpClient();

        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        return gson.fromJson(response.body(), CardTrelloDto.class);
    }

    @SneakyThrows
    public List<CardTrelloDto> getHighPriorityCardsByIdBoard(String idBoard) {

        URI targetUri = new URI(buildUrlGetCardsByIdBoardRequest(idBoard));

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(targetUri)
                .GET()
                .build();
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        List<CardTrelloDto> cardTrelloDtos = gson.fromJson(response.body(), getListType());
        List<CardTrelloDto> highPriorityCards = cardTrelloDtos.stream().filter(cardTrelloDto -> cardTrelloDto.getIdLabels().stream().findFirst().
                        equals(TaskPriorityLabel.ALTA_PRIORITA.getIdTrelloLabel()))
                .toList();
        System.out.println(highPriorityCards);
        return highPriorityCards;

    }

    private List<LabelsTrelloDto> getLabelsFromTrello(String idBoard) {
        return labelsTrelloService.getLabelsFromTrelloBoard(idBoard);
    }
//
//    public List<CardTrelloDto> getHighPriorityCardByIdList(String idList) {
//        String url = buildUrlGetCardsByIdListRequest(idList);
//        String json = getJsonFromRequest(url);
//        String mediumPriority = "652d5736ec602d3f7cd4c6af";
//
//        List<CardTrelloDto> cardTrelloDtos = gson.fromJson(json, listCardsDtoTypeClass());
//
//        return getCardFilteredByPriorityLabelId(mediumPriority, cardTrelloDtos);
//
//    }
//
//
//    public List<CardTrelloDto> getMediumPriorityCardsByIdBoard(String idBoard) {
//
//        String url = buildUrlGetCardsByIdBoardRequest(idBoard);
//        String json = getJsonFromRequest(url);
//        String highPriorityLabel = "652d5736ec602d3f7cd4c6ac";
//
//
//        List<it.euris.stazioneconcordia.data.trelloDto.CardTrelloDto> cardTrelloDtos = gson.fromJson(json, listCardsDtoTypeClass());
//
//        return getCardFilteredByPriorityLabelId(highPriorityLabel, cardTrelloDtos);
//
//    }
//
//    public List<CardTrelloDto> getMediumPriurityCardsByIdList(String idList) {
//        String url = buildUrlGetCardsByIdListRequest(idList);
//        String json = getJsonFromRequest(url);
//        String mediumPriorityLabel = "652d5736ec602d3f7cd4c6af";
//
//        List<CardTrelloDto> cardTrelloDtos = gson.fromJson(json, listCardsDtoTypeClass());
//
//        return getCardFilteredByPriorityLabelId(mediumPriorityLabel, cardTrelloDtos);
//    }
//
//    public List<CardTrelloDto> getLowPriorityCardsByIdBoard(String idBoard) {
//        String url = buildUrlGetCardsByIdBoardRequest(idBoard);
//        String json = getJsonFromRequest(url);
//        String lowPriorityLabel = "652d5736ec602d3f7cd4c6b2";
//
//        List<CardTrelloDto> cardTrelloDtos = gson.fromJson(json, listCardsDtoTypeClass());
//
//        return getCardFilteredByPriorityLabelId(lowPriorityLabel, cardTrelloDtos);
//    }
//
//    public List<CardTrelloDto> getLowPriorityCardsByIdList(String idList) {
//        String url = buildUrlGetCardsByIdListRequest(idList);
//        String json = getJsonFromRequest(url);
//        String lowPriorityLabel = "652d5736ec602d3f7cd4c6b2";
//
//        List<CardTrelloDto> cardTrelloDtos = gson.fromJson(json, listCardsDtoTypeClass());
//
//        return getCardFilteredByPriorityLabelId(lowPriorityLabel, cardTrelloDtos);
//    }
//
//    public List<CardTrelloDto> getDesiredPriorityCardsByIdBoard(String idBoard) {
//        String url = buildUrlGetCardsByIdBoardRequest(idBoard);
//        String json = getJsonFromRequest(url);
//        String desiredPriorityLabel = "652d5736ec602d3f7cd4c6b5";
//
//        List<CardTrelloDto> cardTrelloDtos = gson.fromJson(json, listCardsDtoTypeClass());
//
//        return getCardFilteredByPriorityLabelId(desiredPriorityLabel, cardTrelloDtos);
//    }
//
//    public List<CardTrelloDto> getDesiredPriorityCardsByIdList(String idList) {
//        String url = buildUrlGetCardsByIdListRequest(idList);
//        String json = getJsonFromRequest(url);
//        String desiredPriorityLabel = "652d5736ec602d3f7cd4c6b5";
//
//        List<CardTrelloDto> cardTrelloDtos = gson.fromJson(json, listCardsDtoTypeClass());
//
//        return getCardFilteredByPriorityLabelId(desiredPriorityLabel, cardTrelloDtos);
//    }
//
//    public List<CardTrelloDto> getExpiringIn5DaysCardsByIdBoard(String idBoard) {
//        String url = buildUrlGetCardsByIdBoardRequest(idBoard);
//        String json = getJsonFromRequest(url);
//
//        List<CardTrelloDto> cardTrelloDtos = gson.fromJson(json, listCardsDtoTypeClass());
//
//        List<CardTrelloDto> expiringIn5DaysCards = getExpiringIn5DaysCardsFromToday(cardTrelloDtos);
//        System.out.println(expiringIn5DaysCards);
//        return expiringIn5DaysCards;
//    }
//
//
//    public List<CardTrelloDto> getExpiringIn5DaysCardsByIdList(String idList) {
//        String url = buildUrlGetCardsByIdListRequest(idList);
//        String json = getJsonFromRequest(url);
//
//        List<CardTrelloDto> cardTrelloDtos = gson.fromJson(json, listCardsDtoTypeClass());
//
//        List<CardTrelloDto> expiringIn5DaysCards = getExpiringIn5DaysCardsFromToday(cardTrelloDtos);
//        System.out.println(expiringIn5DaysCards);
//        return expiringIn5DaysCards;
//
//    }
//
//    private static List<CardTrelloDto> getExpiringIn5DaysCardsFromToday(List<CardTrelloDto> cardTrelloDtos) {
//        LocalDateTime today = LocalDateTime.now();
//        LocalDateTime localDateTimeIn5Days = LocalDateTime.now().plusDays(5L);
//
//        List<CardTrelloDto> expiringIn5DaysCards = cardTrelloDtos.stream()
//                .filter(cardTrelloDto -> {
//                    if (cardTrelloDto.getDue() != null) {
//                        LocalDateTime expirationDate = LocalDateTime.parse(cardTrelloDto.getDue().substring(0, 19));
//                        return today.isBefore(expirationDate)
//                                && localDateTimeIn5Days.isAfter(expirationDate);
//                    }
//                    return false;
//                }).toList();
//
//        if (expiringIn5DaysCards.isEmpty()) {
//            System.out.println("There are no cards which expire in 5 days!!!");
//        }
//        return expiringIn5DaysCards;
//    }
//
//    private static List<CardTrelloDto> getCardFilteredByPriorityLabelId(String idLabel, List<CardTrelloDto> cardTrelloDtos) {
//        List<CardTrelloDto> highPriorityCards = cardTrelloDtos
//                .stream()
//                .filter(cardDTO -> cardDTO.getIdLabels().contains(idLabel))
//                .toList();
//        if (!highPriorityCards.isEmpty()) {
//            return highPriorityCards;
//        }
//        System.out.println(highPriorityCards);
//        System.out.printf("There are no cards with selected priority " + idLabel);
//        return null;
//    }

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

    private static Type listCardsDtoTypeClass() {
        return new TypeToken<List<CardDTO>>() {
        }.getType();
    }

    private static Type cardDtoTypeClass() {
        return new TypeToken<CardDTO>() {
        }.getType();
    }

    @Override
    @SneakyThrows
    public CardTrelloDto putCardToAnotherListByIdCardAndIdList(String idCard, String idList) {
        URI targetUri = new URI(buildUrlPutCardToAnotherListRequest(idCard, idList));

        CardTrelloDto cardTrelloDto = getCardByIdCard(idCard);
        cardTrelloDto.setIdList(idList);
//        if (cardTrelloDto.trellotoDto().toModel().getStateHistory().isEmpty()) {
//            List<CardState> cardStateHistory = new ArrayList<>();
//            CardStateDTO initializingState = CardStateDTO.builder()
//                    .dateLastUpdate(localDateTimeToString(LocalDateTime.now()).substring(0, 19))
//                    .fromList(cardTrelloDto.getIdList())
//                    .toList(idList)
//                    .idCard(cardTrelloDto.getId())
//                    .build();
//            cardStateHistory.add(initializingState.toModel());


//        }

        Gson gson = new Gson();
        String requestBody = gson.toJson(cardTrelloDto);

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(targetUri)
                .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        int statusCode = response.statusCode();
        String responseBody = response.body();

        System.out.println("HTTP Status Code: " + statusCode);
        System.out.println("Response Body: " + responseBody);
        return cardTrelloDto;
    }

    private String buildUrlPutCardToAnotherListRequest(String idCard, String idList) {
        return UriComponentsBuilder.fromHttpUrl(URL_API_TRELLO_PUT_MOVE_CARD_BY_ID_LIST_AND_ID_CARD)
                .buildAndExpand(idCard, idList, KEY_VALUE, TOKEN_VALUE).toString();
    }


    private String buildUrlGetCardFromTrelloByIdCard(String idCard) {
        return UriComponentsBuilder.fromHttpUrl(URL_API_TRELLO_GET_CARD_BY_ID_CARD)
                .buildAndExpand(idCard, KEY_VALUE, TOKEN_VALUE).toString();
    }

    private String buildUrlGetCardsFromTrelloByIdListsRequest(String idTrelloList) {
        return UriComponentsBuilder.fromHttpUrl(URL_API_TRELLO_GET_CARDS_BY_ID_LISTS)
                .buildAndExpand(idTrelloList, KEY_VALUE, TOKEN_VALUE).toString();
    }

    private String buildUrlGetCardsFromTrelloByIdBoard(String idTrelloBoard) {
        return UriComponentsBuilder.fromHttpUrl(URL_API_TRELLO_GET_CARDS_BY_ID_BOARD)
                .buildAndExpand(idTrelloBoard, KEY_VALUE, TOKEN_VALUE).toString();
    }

    private static Type getListType() {
        return new TypeToken<List<CardTrelloDto>>() {
        }.getType();
    }

    public static void main(String[] args) {
        Gson gson1 = new Gson();
        CardTrelloServiceImpl cardTrelloService = new CardTrelloServiceImpl(gson1);
        //cardTrelloService.getCardsByIdBoard(BOARD_ID_VALUE);
        cardTrelloService.getHighPriorityCardsByIdBoard(BOARD_ID_VALUE);
    }


}
