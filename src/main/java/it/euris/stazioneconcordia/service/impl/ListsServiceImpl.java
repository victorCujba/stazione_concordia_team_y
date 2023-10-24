package it.euris.stazioneconcordia.service.impl;

import com.google.gson.Gson;
import it.euris.stazioneconcordia.data.dto.BoardDTO;
import it.euris.stazioneconcordia.data.dto.ListsDTO;
import it.euris.stazioneconcordia.data.enums.ListLabel;
import it.euris.stazioneconcordia.data.model.Board;
import it.euris.stazioneconcordia.data.model.Card;
import it.euris.stazioneconcordia.data.model.Lists;
import it.euris.stazioneconcordia.exception.IdMustBeNullException;
import it.euris.stazioneconcordia.exception.IdMustNotBeNullException;
import it.euris.stazioneconcordia.repository.CardRepository;
import it.euris.stazioneconcordia.repository.CardStateRepository;
import it.euris.stazioneconcordia.repository.ListsRepository;
import it.euris.stazioneconcordia.service.ListsService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ListsServiceImpl implements ListsService {

    ListsRepository listsRepository;

    CardRepository cardRepository;

    CardStateRepository cardStateRepository;

    @Override
    public List<Lists> findAll() {
        return listsRepository.findAll();
    }

    @Override
    public Lists insert(Lists lists) {
//        if (lists.getId() != null) {
//            throw new IdMustBeNullException();
//        }
        return listsRepository.save(lists);
    }

    @Override
    public Lists update(Lists lists) {
//        if (lists.getId() == null) {
//            throw new IdMustNotBeNullException();
//        }
        return listsRepository.save(lists);
    }

    @Override
    public Boolean deleteById(String idLists) {
        listsRepository.deleteById(idLists);
        return listsRepository.findById(idLists).isEmpty();
    }

    @Override
    public Lists findById(String idLists) {
        return listsRepository.findById(idLists).orElse(Lists.builder().build());
    }

    @Override
    public Lists findByLabel(ListLabel toListLabel) {
        return listsRepository.findAll()
                .stream()
                .filter(lists -> lists.getLabel() == toListLabel)
                .findFirst()
                .orElse(null);
    }

    @Override
    @SneakyThrows
    public Lists[] getListFromTrelloBoard(String idBoard, String key, String token) {
        String url = "https://api.trello.com/1/boards/" + idBoard + "/lists?key=" + key + "&token=" + token;
        URI targetURI = new URI(url);
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(targetURI)
                .GET()
                .build();
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        Gson gson = new Gson();
        ListsDTO[] listDTOs = gson.fromJson(response.body(), ListsDTO[].class);
        for (ListsDTO listDTO : listDTOs) {
            Lists list = listDTO.toModel();
            insert(list);
        }

        Lists[] lists = new Lists[listDTOs.length];
        for (int i = 0; i < listDTOs.length; i++) {
            lists[i] = listDTOs[i].toModel();
        }

        return lists;
    }
}
