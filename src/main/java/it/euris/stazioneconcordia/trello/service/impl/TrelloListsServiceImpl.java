//package it.euris.stazioneconcordia.trello.service.impl;
//
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
//import it.euris.stazioneconcordia.data.dto.ListsDTO;
//import it.euris.stazioneconcordia.data.model.Lists;
//import it.euris.stazioneconcordia.data.trelloDto.ListsTrelloDto;
//import it.euris.stazioneconcordia.exception.IdMustBeNullException;
//import it.euris.stazioneconcordia.repository.ListsRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.web.util.UriComponentsBuilder;
//
//import java.io.IOException;
//import java.lang.reflect.Type;
//import java.net.URI;
//import java.net.URISyntaxException;
//import java.net.http.HttpClient;
//import java.net.http.HttpRequest;
//import java.net.http.HttpResponse;
//import java.util.List;
//
//import static it.euris.stazioneconcordia.trello.utils.TrelloConstants.*;
//
//@Service
//public class TrelloListsServiceImpl {
//
//    @Autowired
//    ListsRepository listsRepository;
//
//    private final Gson gson;
//
//    public TrelloListsServiceImpl() {
//        this.gson = new Gson();
//    }
//
//
//    public List<Lists> getAllListsFromTrelloBoardByIdBoard(String idBoard) throws IOException, InterruptedException, URISyntaxException {
////        URI targetURI = new URI(buildUrlForGetAllListsFromTrelloBoardByIdBoard(idBoard));
//
//        HttpRequest httpRequest = HttpRequest.newBuilder()
//                .uri(targetURI)
//                .GET()
//                .build();
//        HttpClient httpClient = HttpClient.newHttpClient();
//        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
//
//
//        List<ListsTrelloDto> listsTrelloDtos = gson.fromJson(response.body(), listsTrelloDtoTypeClass());
//
//        List<ListsDTO> listsDTO = listsTrelloDtos.stream()
//                .map(ListsTrelloDto::trellotoDto)
//                .toList();
//
//        List<Lists> lists = listsDTO.stream().map(ListsDTO::toModel).toList();
//
//        listsDTO.stream()
//                .map(ListsDTO::toModel)
//                .forEach(this::insert);
//        System.out.println(lists);
//        return lists;
//
//    }
//
//    public Lists insert(Lists lists) {
//        if (lists.getId() != null) {
//            throw new IdMustBeNullException();
//        }
//        return listsRepository.save(lists);
//    }
//
//
//    private static Type listsTrelloDtoTypeClass() {
//        return new TypeToken<List<ListsTrelloDto>>() {
//        }.getType();
//    }
//
////    private String buildUrlForGetAllListsFromTrelloBoardByIdBoard(String idBoard) {
////        return UriComponentsBuilder.fromHttpUrl(URL_API_TRELLO_GET_LISTS_BY_ID_BOARD)
////                .buildAndExpand(idBoard, KEY_VALUE, TOKEN_VALUE).toString();
////    }
//}
