package it.euris.stazioneconcordia.service.trelloService.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.euris.stazioneconcordia.data.trelloDto.CardTrelloDto;
import it.euris.stazioneconcordia.service.trelloService.CardTrelloService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import static it.euris.stazioneconcordia.trello.utils.TrelloConstants.*;
@AllArgsConstructor
@Service
public class CardTrelloServiceImpl implements CardTrelloService {

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
        Gson gson = new Gson();

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
        Gson gson = new Gson();

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
        Gson gson = new Gson();

        return gson.fromJson(response.body(), CardTrelloDto.class);
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


}
