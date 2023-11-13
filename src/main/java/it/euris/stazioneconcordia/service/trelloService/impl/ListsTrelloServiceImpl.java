package it.euris.stazioneconcordia.service.trelloService.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.euris.stazioneconcordia.data.model.Lists;
import it.euris.stazioneconcordia.data.trelloDto.CardTrelloDto;
import it.euris.stazioneconcordia.data.trelloDto.ListsTrelloDto;
import it.euris.stazioneconcordia.service.trelloService.ListsTrelloService;
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

import static it.euris.stazioneconcordia.service.trello.utils.TrelloConstants.*;

@AllArgsConstructor
@Service
public class ListsTrelloServiceImpl implements ListsTrelloService {
    @Override
    @SneakyThrows
    public List<ListsTrelloDto> getListsByIdBoard(String idBoard) {

        URI targetURI = new URI(buildUrlGetListsFromTrelloByIdBoard(idBoard));
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
    @SneakyThrows
    @Override
    public void updateAList(String idList, ListsTrelloDto listsTrelloDto) {
        URI targetUri = new URI(buildUrlUpdateAListsToTrello(idList));
        Gson gson = new Gson();
        String requestBody = gson.toJson(listsTrelloDto);
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(targetUri)
                .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                .header("Accept", "application/json")
                .header("Content-type", "application/json")
                .build();
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        int statusCode = response.statusCode();
        String responseBody = response.body();

        System.out.println("HTTP Status Code: " + statusCode);
        System.out.println("Response Body: " + responseBody);
    }
    @SneakyThrows
    @Override
    public void closeAList(String idList, ListsTrelloDto listsTrelloDto) {
        URI targetUri = new URI(buildUrlCloseAListsToTrello(idList));
        Gson gson = new Gson();
       String requestBody = gson.toJson(listsTrelloDto);
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(targetUri)
                .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                .header("Accept", "application/json")
                .header("Content-type", "application/json")
                .build();
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        int statusCode = response.statusCode();
        String responseBody = response.body();

        System.out.println("HTTP Status Code: " + statusCode);
        System.out.println("Response Body: " + responseBody);
    }
    @SneakyThrows
    @Override
    public void createANewList( ListsTrelloDto listsTrelloDto) {
        URI targetUri = new URI(buildUrlCreateANewListsToTrello());
        Gson gson = new Gson();
       String requestBody = gson.toJson(listsTrelloDto);
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(targetUri)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .header("Accept", "application/json")
                .header("Content-type", "application/json")
                .build();
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        int statusCode = response.statusCode();
        String responseBody = response.body();

        System.out.println("HTTP Status Code: " + statusCode);
        System.out.println("Response Body: " + responseBody);
    }

    private Type getListType() {
        return new TypeToken<List<ListsTrelloDto>>() {}.getType();
    }

    private String buildUrlGetListsFromTrelloByIdBoard(String idBoard) {
        return UriComponentsBuilder.fromHttpUrl(URL_API_TRELLO_GET_LISTS_BY_ID_BOARD)
                .buildAndExpand(idBoard, KEY_VALUE, TOKEN_VALUE).toString();
    }
    private String buildUrlUpdateAListsToTrello(String idList) {
        return UriComponentsBuilder.fromHttpUrl(URL_API_TRELLO_UPDATE_LIST)
                .buildAndExpand(idList, KEY_VALUE, TOKEN_VALUE).toString();
    }
    private String buildUrlCloseAListsToTrello(String idList) {
        return UriComponentsBuilder.fromHttpUrl(URL_API_TRELLO_CLOSE_A_LIST)
                .buildAndExpand(idList, KEY_VALUE, TOKEN_VALUE).toString();
    }
    private String buildUrlCreateANewListsToTrello() {
        return UriComponentsBuilder.fromHttpUrl(URL_API_TRELLO_CREATE_A_NEW_LIST)
                .buildAndExpand(KEY_VALUE, TOKEN_VALUE).toString();
    }
}
