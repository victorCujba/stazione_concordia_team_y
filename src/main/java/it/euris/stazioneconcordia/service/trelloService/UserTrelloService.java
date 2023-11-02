package it.euris.stazioneconcordia.service.trelloService;

import it.euris.stazioneconcordia.data.trelloDto.UserTrelloDto;

import java.util.List;

public interface UserTrelloService {

    List<UserTrelloDto> getUsersFromTrello(String idBoard);

}
