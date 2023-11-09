package it.euris.stazioneconcordia.service.trelloService.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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

    private Type getListType() {
        return new TypeToken<List<ListsTrelloDto>>() {}.getType();
    }

    private String buildUrlGetListsFromTrelloByIdBoard(String idBoard) {
        return UriComponentsBuilder.fromHttpUrl(URL_API_TRELLO_GET_LISTS_BY_ID_BOARD)
                .buildAndExpand(idBoard, KEY_VALUE, TOKEN_VALUE).toString();
    }
}
