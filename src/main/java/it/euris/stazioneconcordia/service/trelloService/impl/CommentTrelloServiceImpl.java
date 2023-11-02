package it.euris.stazioneconcordia.service.trelloService.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.euris.stazioneconcordia.data.dto.CommentDTO;
import it.euris.stazioneconcordia.data.trelloDto.CommentTrelloDto;
import it.euris.stazioneconcordia.service.trelloService.CommentTrelloService;
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
public class CommentTrelloServiceImpl implements CommentTrelloService {

    @Override
    @SneakyThrows
    public List<CommentTrelloDto> getCommentsFromCardByIdCard(String idCard) {

        URI targetURI = new URI(buildUrlGetAllCommentsFromCardByIdCard(idCard));
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(targetURI)
                .GET()
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        Gson gson = new Gson();
        Type listType = getListType();
        System.out.println(gson.fromJson(response.body(), listType).toString());

        return gson.fromJson(response.body(), listType);
    }

    private Type getListType() {
        return new TypeToken<List<CommentTrelloDto>>() {
        }.getType();
    }

    private String buildUrlGetAllCommentsFromCardByIdCard(String idCard) {
        return UriComponentsBuilder.fromHttpUrl(URL_API_TRELLO_GET_COMMENTS_BY_ID_CARD)
                .buildAndExpand(idCard, KEY_VALUE, TOKEN_VALUE).toString();
    }

    public static void main(String[] args) {
        CommentTrelloServiceImpl commentTrelloService = new CommentTrelloServiceImpl();
        commentTrelloService.getCommentsFromCardByIdCard(CARD_01_ID_VALUE);
    }

}
