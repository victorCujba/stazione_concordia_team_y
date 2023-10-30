package it.euris.stazioneconcordia.service.impl;

import com.google.gson.Gson;
import it.euris.stazioneconcordia.data.dto.LabelsDTO;
import it.euris.stazioneconcordia.data.model.Labels;
import it.euris.stazioneconcordia.data.trelloDto.LabelsTrelloDto;
import it.euris.stazioneconcordia.exception.IdMustBeNullException;
import it.euris.stazioneconcordia.repository.LabelsRepository;
import it.euris.stazioneconcordia.service.LabelsService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;

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
        LabelsTrelloDto[] labelsTrelloDtos = gson.fromJson(response.body(), LabelsTrelloDto[].class);
        for (LabelsTrelloDto labelsTrelloDto : labelsTrelloDtos) {
            LabelsDTO labelsDTO = labelsTrelloDto.trellotoDto();
            Labels labels = labelsDTO.toModel();
            insert(labels);
        }

        Labels[] labels = new Labels[labelsTrelloDtos.length];
        for (int i = 0; i < labels.length; i++) {
            labels[i] = labelsTrelloDtos[i].trellotoDto().toModel();
        }

        return labels;


    }
}
