package it.euris.stazioneconcordia.service.trelloService.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.euris.stazioneconcordia.data.trelloDto.UserTrelloDto;
import it.euris.stazioneconcordia.service.trelloService.UserTrelloService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import static it.euris.stazioneconcordia.trello.utils.TrelloConstants.*;

@Service
@AllArgsConstructor
public class UsersTrelloServiceImpl implements UserTrelloService {

    private final Gson gson;

    public UsersTrelloServiceImpl() {
        this.gson = new Gson();
    }

    @Override
    @SneakyThrows
    public List<String> getUsersIdFromTrello(String idBoard) {

        URI targetUri = new URI(buildUrlGetUsersFromTrelloBoardByIdBoard(idBoard));
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(targetUri)
                .GET()
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        List<UserTrelloDto> userTrelloDtos = gson.fromJson(response.body(), getListType());

        return userTrelloDtos.stream().map(UserTrelloDto::getId).toList();
    }

    @Override
    @SneakyThrows
    public List<UserTrelloDto> getUsersFromTrelloByIdBoard(String idBoard) {
        List<String> usersId = getUsersIdFromTrello(idBoard);
        List<UserTrelloDto> userTrelloDtos = new ArrayList<>();

        for (String idUser : usersId) {
            URI targetUri = new URI(buildUrlGetUsersFromTrelloBoardByIdUser(idUser));
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(targetUri)
                    .GET()
                    .build();

            HttpClient httpClient = HttpClient.newHttpClient();
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            UserTrelloDto userTrelloDto = gson.fromJson(response.body(), UserTrelloDto.class);
            userTrelloDtos.add(userTrelloDto);

        }

        return userTrelloDtos;
    }


    @Override
    @SneakyThrows
    public UserTrelloDto getUserFromTrelloByUserName(String username) {

        URI targetURI = new URI(buildUrlGetUserFromTrelloByUserName(username));

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(targetURI)
                .GET()
                .build();
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        return gson.fromJson(response.body(), UserTrelloDto.class);

    }

    private Type getListType() {
        return new TypeToken<List<UserTrelloDto>>() {
        }.getType();
    }


    private String buildUrlGetUserFromTrelloByUserName(String username) {
        return UriComponentsBuilder.fromHttpUrl(URL_API_TRELLO_GET_USER_BY_USERNAME)
                .build(username, KEY_VALUE, TOKEN_VALUE).toString();
    }


    private String buildUrlGetUsersFromTrelloBoardByIdBoard(String idBoard) {
        return UriComponentsBuilder.fromHttpUrl(URL_API_TRELLO_GET_ALL_USERS_BY_ID_BOARD)
                .buildAndExpand(idBoard, KEY_VALUE, TOKEN_VALUE).toString();
    }

    private String buildUrlGetUsersFromTrelloBoardByIdUser(String idUser) {
        return UriComponentsBuilder.fromHttpUrl(URL_API_TRELLO_GET_USER_BY_ID_USER)
                .buildAndExpand(idUser, KEY_VALUE, TOKEN_VALUE).toString();
    }
}
