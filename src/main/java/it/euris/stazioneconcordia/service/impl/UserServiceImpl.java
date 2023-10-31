package it.euris.stazioneconcordia.service.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.euris.stazioneconcordia.data.dto.UserDTO;
import it.euris.stazioneconcordia.data.model.User;
import it.euris.stazioneconcordia.data.trelloDto.UserTrelloDto;
import it.euris.stazioneconcordia.exception.IdMustBeNullException;
import it.euris.stazioneconcordia.exception.IdMustNotBeNullException;
import it.euris.stazioneconcordia.repository.UserRepository;
import it.euris.stazioneconcordia.service.UserService;
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

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    UserRepository userRepository;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User insert(User user) {

        if (user.getId() != null) {
            throw new IdMustBeNullException();
        }
        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
        if (user.getId() == null) {
            throw new IdMustNotBeNullException();
        }
        return userRepository.save(user);
    }

    @Override
    public Boolean deleteById(String idUser) {
        userRepository.deleteById(idUser);
        return userRepository.findById(idUser).isEmpty();

    }

    @Override
    public User findById(String idUser) {

        return userRepository.findById(idUser).orElse(User.builder().build());
    }

    @Override
    @SneakyThrows
    public UserTrelloDto getUserFromTrelloByUserName(String userName) {
        URI targetURI = new URI(buildUrlGetUserByUserNameRequest(userName));
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(targetURI)
                .GET()
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        Gson gson = new Gson();
        return gson.fromJson(response.body(), UserTrelloDto.class);
    }
    @Override
    @SneakyThrows
    public List<UserTrelloDto> getAllUsersFromTrelloBoardByIdBoard(String idBoard) {

        URI targetURI = new URI(buildUrlGetAllUsersFromTrelloBoardByIdBoardRequest(idBoard));
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(targetURI)
                .GET()
                .build();
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        Gson gson = new Gson();
        Type typeList = new TypeToken<List<UserTrelloDto>>() {
        }.getType();

        return gson.fromJson(response.body(), typeList);
    }
    private String buildUrlGetAllUsersFromTrelloBoardByIdBoardRequest(String idBoard) {
        return UriComponentsBuilder.fromHttpUrl(URL_API_TRELLO_GET_ALL_USERS_BY_ID_BOARD)
                .buildAndExpand(idBoard, KEY_VALUE, TOKEN_VALUE).toString();
    }
    private String buildUrlGetUserByUserNameRequest(String userName) {
        return UriComponentsBuilder.fromHttpUrl(URL_API_TRELLO_GET_USER_BY_USERNAME)
                .buildAndExpand(userName, KEY_VALUE, TOKEN_VALUE).toString();
    }
}
