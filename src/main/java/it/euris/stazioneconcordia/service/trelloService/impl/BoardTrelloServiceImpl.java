package it.euris.stazioneconcordia.service.trelloService.impl;

import com.google.gson.Gson;
import it.euris.stazioneconcordia.data.dto.BoardDTO;
import it.euris.stazioneconcordia.data.model.Board;
import it.euris.stazioneconcordia.data.trelloDto.BoardTrelloDTO;
import it.euris.stazioneconcordia.service.BoardService;
import it.euris.stazioneconcordia.service.trelloService.BoardTrelloService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static it.euris.stazioneconcordia.trello.utils.TrelloConstants.*;
@AllArgsConstructor
@Service
public class BoardTrelloServiceImpl implements BoardTrelloService {

    BoardService boardService;

    @Override
    @SneakyThrows
    public BoardTrelloDTO getBoardFromTrelloByIdBoard(String idBoard) {
        URI targetURI = new URI(buildUrlForGetBoardFromTrelloByIdBoard(idBoard));
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(targetURI)
                .GET()
                .build();
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        Gson gson = new Gson();
        BoardTrelloDTO boardTrelloDTO = gson.fromJson(response.body(), BoardTrelloDTO.class);
//        BoardDTO boardDTO = boardTrelloDTO.trellotoDto();
//        Board board = boardDTO.toModel();
        System.out.println(boardTrelloDTO.toString());
        return boardTrelloDTO;
    }


    private String buildUrlForGetBoardFromTrelloByIdBoard(String idBoard) {
        return UriComponentsBuilder.fromHttpUrl(URL_API_TRELLO_GET_BOARD_BY_ID_BOARD)
                .buildAndExpand(idBoard, KEY_VALUE, TOKEN_VALUE).toString();
    }


}
