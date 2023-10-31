package it.euris.stazioneconcordia.service.trelloService.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.euris.stazioneconcordia.data.dto.LabelsDTO;
import it.euris.stazioneconcordia.data.model.Labels;
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

import static it.euris.stazioneconcordia.trello.utils.TrelloConstants.*;
@Service
public class LabelsTrelloServiceImpl implements LabelsTrelloService {

    private final Gson gson;

    public LabelsTrelloServiceImpl(Gson gson) {
        this.gson = new Gson();
    }

    @SneakyThrows
    @Override
    public List<LabelsTrelloDto> getLabelsFromTrelloBoard(String idBoard) {

        URI targetURI = new URI(buildUrlGetLabelsFromTrelloBoardRequest(idBoard));

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(targetURI)
                .GET()
                .build();
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        Type listType = new TypeToken<List<LabelsTrelloDto>>() {
        }.getType();

        return gson.fromJson(response.body(), listType);
    }

    private String buildUrlGetLabelsFromTrelloBoardRequest(String idBoard) {
        return UriComponentsBuilder.fromHttpUrl(URL_API_TRELLO_GET_LABELS_BY_ID_BOARD)
                .buildAndExpand(idBoard, KEY_VALUE, TOKEN_VALUE).toString();
    }

    public static void main(String[] args) {
        LabelsTrelloServiceImpl labelsTrelloService = new LabelsTrelloServiceImpl(new Gson());
        labelsTrelloService.getLabelsFromTrelloBoard(BOARD_ID_VALUE);
    }

}
