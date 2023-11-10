package it.euris.stazioneconcordia.service.trelloService.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.euris.stazioneconcordia.data.trelloDto.BoardTrelloDTO;
import it.euris.stazioneconcordia.data.trelloDto.CardTrelloDto;
import it.euris.stazioneconcordia.service.trelloService.BoardTrelloService;
import it.euris.stazioneconcordia.utility.LocalDateTimeTypeAdapter;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;

import static it.euris.stazioneconcordia.service.trello.utils.TrelloConstants.*;

@AllArgsConstructor
@Service
public class BoardTrelloServiceImpl implements BoardTrelloService {


    @Override
    @SneakyThrows
    public BoardTrelloDTO getBoardFromTrelloByIdBoard(String idBoard) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter());
        Gson gson = gsonBuilder.create();

        URI targetURI = new URI(buildUrlForGetBoardFromTrelloByIdBoard(idBoard));

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(targetURI)
                .GET()
                .build();
        HttpClient httpClient = HttpClient.newHttpClient();

        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());


        BoardTrelloDTO boardTrelloDTO = gson.fromJson(response.body(), BoardTrelloDTO.class);
        System.out.println(boardTrelloDTO.toString());
        return boardTrelloDTO;
    }
    @SneakyThrows
    @Override
    public void updateABoard(String idBoard,BoardTrelloService boardTrelloService) {
        URI targetUri = new URI(buildUrlForUpdateABoard(idBoard));
        Gson gson = new Gson();
        String requestBody = gson.toJson(boardTrelloService);
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
    @SneakyThrows
    @Override
    public void deleteABoard(String idBoard) {
        URI targetUri = new URI(buildUrlForDeleteABoard(idBoard));

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(targetUri)
                .DELETE()
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


    private String buildUrlForGetBoardFromTrelloByIdBoard(String idBoard) {
        return UriComponentsBuilder.fromHttpUrl(URL_API_TRELLO_GET_BOARD_BY_ID_BOARD)
                .buildAndExpand(idBoard, KEY_VALUE, TOKEN_VALUE).toString();
    }
    private String buildUrlForUpdateABoard(String idBoard) {
        return UriComponentsBuilder.fromHttpUrl(URL_API_TRELLO_UPDATE_BOARD)
                .buildAndExpand(idBoard, KEY_VALUE, TOKEN_VALUE).toString();
    }
    private String buildUrlForDeleteABoard(String idBoard) {
        return UriComponentsBuilder.fromHttpUrl(URL_API_TRELLO_DELETE_BOARD)
                .buildAndExpand(idBoard, KEY_VALUE, TOKEN_VALUE).toString();
    }


}
