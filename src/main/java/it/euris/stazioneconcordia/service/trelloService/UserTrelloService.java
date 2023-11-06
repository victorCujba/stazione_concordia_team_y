package it.euris.stazioneconcordia.service.trelloService;

import it.euris.stazioneconcordia.data.trelloDto.UserTrelloDto;

import java.util.List;

public interface UserTrelloService {

    List<String> getUsersIdFromTrello(String idBoard);

    List<UserTrelloDto> getUsersFromTrelloByIdBoard(String idBoard);


    UserTrelloDto getUserFromTrelloByUserName(String username);
}
