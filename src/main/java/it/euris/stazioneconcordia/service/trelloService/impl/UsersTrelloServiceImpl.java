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
import java.util.List;

import static it.euris.stazioneconcordia.trello.utils.TrelloConstants.*;

@Service
@AllArgsConstructor
public class UsersTrelloServiceImpl implements UserTrelloService {


    @Override
    @SneakyThrows
    public List<UserTrelloDto> getUsersFromTrello(String idBoard) {

        URI targetUri = new URI(buildUrlGetUsersFromTrelloBoardByIdBoard(idBoard));
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(targetUri)
                .GET()
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        Gson gson = new Gson();

        return gson.fromJson(response.body(), getListType());
    }

    private Type getListType() {
        return new TypeToken<List<UserTrelloDto>>() {
        }.getType();
    }

    private String buildUrlGetUsersFromTrelloBoardByIdBoard(String idBoard) {
        return UriComponentsBuilder.fromHttpUrl(URL_API_TRELLO_GET_ALL_USERS_BY_ID_BOARD)
                .buildAndExpand(idBoard, KEY_VALUE, TOKEN_VALUE).toString();
    }
}
