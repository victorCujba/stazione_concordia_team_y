package it.euris.stazioneconcordia.service.trelloService.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.euris.stazioneconcordia.data.trelloDto.CommentTrelloDto;
import it.euris.stazioneconcordia.data.trelloDto.LabelsTrelloDto;
import it.euris.stazioneconcordia.service.trelloService.LabelsTrelloService;
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
public class LabelsTrelloServiceImpl implements LabelsTrelloService {
    @Override
    @SneakyThrows
    public List<LabelsTrelloDto> getLabelsByIdBoard(String idBoard) {
        URI targetURI = new URI(buildUrlGetLabelsFromTrelloByIdBoard(idBoard));
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
    public void deleteALabel(String idLabel) {
        URI targetUri = new URI(buildUrlDeleteALabel(idLabel));

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
    @SneakyThrows
    @Override
    public void createANewLabel( LabelsTrelloDto labelsTrelloDto) {
        URI targetUri = new URI(buildUrlCreateALabel());
        Gson gson = new Gson();
        String requestBody = gson.toJson(labelsTrelloDto);
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
    public void updateALabel( LabelsTrelloDto labelsTrelloDto) {
        URI targetUri = new URI(buildUrlCreateALabel());
        Gson gson = new Gson();
        String requestBody = gson.toJson(labelsTrelloDto);
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

    private Type getListType() {
        return new TypeToken<List<LabelsTrelloDto>>() {}.getType();
    }

    private String buildUrlGetLabelsFromTrelloByIdBoard(String idBoard) {
        return UriComponentsBuilder.fromHttpUrl(URL_API_TRELLO_GET_LABELS_BY_ID_BOARD)
                .buildAndExpand(idBoard, KEY_VALUE, TOKEN_VALUE).toString();

    }
    private String buildUrlDeleteALabel(String idLabel) {
        return UriComponentsBuilder.fromHttpUrl(URL_API_TRELLO_DELETE_A_LABEL)
                .buildAndExpand(idLabel,KEY_VALUE, TOKEN_VALUE).toString();
    }
    private String buildUrlUpdateALabel(String idLabel) {
        return UriComponentsBuilder.fromHttpUrl(URL_API_TRELLO_CREATE_NEW_COMMENT_ON_A_CARD)
                .buildAndExpand(idLabel,KEY_VALUE, TOKEN_VALUE).toString();
    }
    private String buildUrlCreateALabel() {
        return UriComponentsBuilder.fromHttpUrl(URL_API_TRELLO_CREATE_A_LABEL)
                .buildAndExpand(KEY_VALUE, TOKEN_VALUE).toString();
    }

}
