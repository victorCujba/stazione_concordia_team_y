package it.euris.stazioneconcordia.service.impl;

import com.google.gson.Gson;
import it.euris.stazioneconcordia.data.dto.LabelsDTO;
import it.euris.stazioneconcordia.data.dto.ListsDTO;
import it.euris.stazioneconcordia.data.model.Labels;
import it.euris.stazioneconcordia.data.model.Lists;
import it.euris.stazioneconcordia.repository.LabelsRepository;
import it.euris.stazioneconcordia.service.LabelsService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@AllArgsConstructor
@Service
public class LabelsServiceImpl implements LabelsService {

    LabelsRepository labelsRepository;

    @Override
    public Labels insert(Labels labels) {
//        if (lists.getId() != null) {
//            throw new IdMustBeNullException();
//        }
        return labelsRepository.save(labels);
    }

    @Override
    public Labels findById(String idLabels) {
        return labelsRepository.findById(idLabels).orElse(Labels.builder().build());
    }

    @SneakyThrows
    @Override
    public Labels[] getLabelsFromTrelloBoard(String idBoard, String key, String token) {
        String url = "https://api.trello.com/1/boards/" + idBoard + "/labels?key=" + key + "&token=" + token;
        URI targetURI = new URI(url);
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(targetURI)
                .GET()
                .build();
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        Gson gson = new Gson();
        LabelsDTO[] labelsDTOS = gson.fromJson(response.body(), LabelsDTO[].class);
        for (LabelsDTO labelsDTO : labelsDTOS) {

            Labels labels = labelsDTO.toModel();
            insert(labels);
        }

        Labels[] labels = new Labels[labelsDTOS.length];
        for (int i = 0; i < labels.length; i++) {

            labels[i] = labelsDTOS[i].toModel();
        }

        return labels;


    }
}
