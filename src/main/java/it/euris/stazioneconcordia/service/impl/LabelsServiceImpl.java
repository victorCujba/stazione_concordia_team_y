package it.euris.stazioneconcordia.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.*;

import com.google.gson.reflect.TypeToken;
import it.euris.stazioneconcordia.data.model.Labels;
import it.euris.stazioneconcordia.data.trelloDto.LabelsTrelloDto;
import it.euris.stazioneconcordia.exception.IdMustBeNullException;
import it.euris.stazioneconcordia.repository.LabelsRepository;
import it.euris.stazioneconcordia.service.LabelsService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.InputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import static it.euris.stazioneconcordia.trello.utils.TrelloConstants.*;

@AllArgsConstructor
@Service
public class LabelsServiceImpl implements LabelsService {

    LabelsRepository labelsRepository;

    @Override
    public Labels insert(Labels labels) {
        if (labels.getId() != null) {
            throw new IdMustBeNullException();
        }
        return labelsRepository.save(labels);
    }

    @Override
    public Labels findById(Long idLabels) {
        return labelsRepository.findById(idLabels).orElse(Labels.builder().build());
    }

    @SneakyThrows
    @Override
    public List<LabelsTrelloDto> getLabelsFromTrelloBoardByIdBoard(String boardId) {

        URI targetURI = new URI(buildUrlForGetLabelsFromTrelloBoardByIdBoard(boardId));
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(targetURI)
                .GET()
                .build();
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<InputStream> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofInputStream());

        Type listType = new TypeToken<List<LabelsTrelloDto>>() {
        }.getType();

        Gson gson = new Gson();
        String labelsTrelloDtos = gson.fromJson(response.body().toString(), String.class);

        for(int i = 0; i < labelsTrelloDtos.length(); i++) {

        }

        System.out.println(labelsTrelloDtos.toString());

        return null;
    }


    private String buildUrlForGetLabelsFromTrelloBoardByIdBoard(String idBoard) {
        return UriComponentsBuilder.fromHttpUrl(URL_API_TRELLO_GET_LABELS_BY_ID_BOARD)
                .buildAndExpand(idBoard, KEY_VALUE, TOKEN_VALUE).toString();
    }
}