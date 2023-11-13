package it.euris.stazioneconcordia.controller.trelloController;

import it.euris.stazioneconcordia.data.trelloDto.UserTrelloDto;
import it.euris.stazioneconcordia.service.UserService;
import it.euris.stazioneconcordia.service.trelloService.UserTrelloService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/v1/from-trello/User")
public  class UserTrelloController {
    private UserService userService;
    private UserTrelloService userTrelloService;
    public UserTrelloDto getUserFromTrelloByUsername(String username) {
        return userTrelloService.getUserFromTrelloByUserName(username);
    }
    public List<UserTrelloDto> getAllUsersFromBoard(String idBoard) {
        return userTrelloService.getUsersFromTrelloByIdBoard(idBoard);
    }
}

