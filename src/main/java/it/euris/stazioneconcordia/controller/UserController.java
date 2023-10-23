package it.euris.stazioneconcordia.controller;

import it.euris.stazioneconcordia.data.dto.UserDTO;
import it.euris.stazioneconcordia.data.model.User;
import it.euris.stazioneconcordia.exception.IdMustBeNullException;
import it.euris.stazioneconcordia.exception.IdMustNotBeNullException;
import it.euris.stazioneconcordia.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    UserService userService;

    @GetMapping("/v1")
    public List<UserDTO> getAllUsers() {
        return userService.findAll().stream().map(User::toDto).toList();
    }

    @PostMapping("/v1")
    public UserDTO saveUser(@RequestBody UserDTO userDTO) {
        try {
            User user = userDTO.toModel();
            return userService.insert(user).toDto();
        } catch (IdMustBeNullException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/v1")
    public UserDTO updateUser(@RequestBody UserDTO userDTO) {
        try {
            User user = userDTO.toModel();
            return userService.update(user).toDto();
        } catch (IdMustNotBeNullException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping("/v1/{id}")
    public Boolean deleteUser(@PathVariable("id") String idUser) {
        return userService.deleteById(idUser);
    }

    @GetMapping("/v1/{id}")
    public UserDTO getUserById(@PathVariable("id") String idUser) {
        return userService.findById(idUser).toDto();
    }
}
