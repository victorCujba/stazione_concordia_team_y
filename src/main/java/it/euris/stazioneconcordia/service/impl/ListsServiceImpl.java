package it.euris.stazioneconcordia.service.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.euris.stazioneconcordia.data.dto.ListsDTO;
import it.euris.stazioneconcordia.data.enums.ListLabel;
import it.euris.stazioneconcordia.data.model.Lists;
import it.euris.stazioneconcordia.data.trelloDto.ListsTrelloDto;
import it.euris.stazioneconcordia.exception.IdMustBeNullException;
import it.euris.stazioneconcordia.exception.IdMustNotBeNullException;
import it.euris.stazioneconcordia.repository.CardRepository;
import it.euris.stazioneconcordia.repository.CardStateRepository;
import it.euris.stazioneconcordia.repository.ListsRepository;
import it.euris.stazioneconcordia.service.ListsService;
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
        if (lists.getId() != null) {
            throw new IdMustBeNullException();
        }
        return listsRepository.save(lists);
    }

    @Override
    public Lists update(Lists lists) {
        if (lists.getId() == null) {
            throw new IdMustNotBeNullException();
        }
        return listsRepository.save(lists);
    }

    @Override
    public Boolean deleteById(Long idLists) {
        listsRepository.deleteById(idLists);
        return listsRepository.findById(idLists).isEmpty();
    }

    @Override
    public Lists findById(Long idLists) {
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
    public List<ListsTrelloDto> getListsFromTrelloByIdBoard(String idBoard) {

        URI targetURI = new URI(buildUrlGetListsFromTrelloByIdBoardRequest(idBoard));
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(targetURI)
                .GET()
                .build();
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        Gson gson = new Gson();
        Type listType = new TypeToken<List<ListsTrelloDto>>() {
        }.getType();
        return gson.fromJson(response.body(), listType);
    }

    private String buildUrlGetListsFromTrelloByIdBoardRequest(String idBoard) {
        return UriComponentsBuilder.fromHttpUrl(URL_API_TRELLO_GET_LISTS_BY_ID_BOARD)
                .buildAndExpand(idBoard, KEY_VALUE, TOKEN_VALUE).toString();
    }
}
