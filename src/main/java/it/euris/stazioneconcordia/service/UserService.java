package it.euris.stazioneconcordia.service;

import it.euris.stazioneconcordia.data.model.User;
import it.euris.stazioneconcordia.data.trelloDto.UserTrelloDto;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User insert(User user);

    User update(User user);

    Boolean deleteById(String idUser);

    User findById(String idUser);

    UserTrelloDto getUserFromTrelloByUserName(String username);

    List<UserTrelloDto> getAllUsersFromTrelloBoardByIdBoard(String idBoard);
}
