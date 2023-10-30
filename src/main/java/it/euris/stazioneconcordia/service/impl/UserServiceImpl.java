package it.euris.stazioneconcordia.service.impl;

import com.google.gson.Gson;
import it.euris.stazioneconcordia.data.dto.UserDTO;
import it.euris.stazioneconcordia.data.model.User;
import it.euris.stazioneconcordia.exception.IdMustBeNullException;
import it.euris.stazioneconcordia.exception.IdMustNotBeNullException;
import it.euris.stazioneconcordia.repository.UserRepository;
import it.euris.stazioneconcordia.service.UserService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

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
    public User getUserFromTrello(String username, String key, String token) {
        String url = "https://api.trello.com/1/members/" + username + "/?key=" + key + "&token=" + token;
        URI targetURI = new URI(url);
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(targetURI)
                .GET()
                .build();
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        Gson gson = new Gson();
        UserDTO userDTO = gson.fromJson(response.body(), UserDTO.class);
        User user = userDTO.toModel();
        return insert(user);
    }

    @Override
    @SneakyThrows
    public User[] getUserFromTrelloBoard(Long idBoard, String key, String token) {
        String url = "https://api.trello.com/1/boards/" + idBoard + "/members?key=" + key + "&token=" + token;
        URI targetURI = new URI(url);
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(targetURI)
                .GET()
                .build();
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        Gson gson = new Gson();
        UserDTO[] userDTOs = gson.fromJson(response.body(), UserDTO[].class);
        for (UserDTO userDTO : userDTOs) {
            User user = userDTO.toModel();
            insert(user);
        }
        User[] users = new User[userDTOs.length];
        for (int i = 0; i < userDTOs.length; i++) {
            users[i] = userDTOs[i].toModel();
        }

        return users;
    }
}
