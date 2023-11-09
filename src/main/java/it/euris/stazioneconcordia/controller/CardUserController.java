package it.euris.stazioneconcordia.controller;

import it.euris.stazioneconcordia.data.dto.CardUserDTO;
import it.euris.stazioneconcordia.data.model.CardUser;
import it.euris.stazioneconcordia.exception.IdMustBeNullException;
import it.euris.stazioneconcordia.exception.IdMustNotBeNullException;
import it.euris.stazioneconcordia.service.CardUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/card-users")
public class CardUserController {

    private CardUserService cardUserService;

    @GetMapping("/v1")
    public List<CardUserDTO> findAll() {
        return cardUserService.findAll().stream().map(CardUser::toDto).toList();
    }

    @PostMapping("/v1")
    public CardUserDTO insert(@RequestBody CardUserDTO cardUserDTO) {
        try {
            CardUser cardUser = cardUserDTO.toModel();
            return cardUserService.insert(cardUser).toDto();
        } catch (IdMustBeNullException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage()
            );
        }
    }

    @PutMapping("/v1")
    public CardUserDTO update(@RequestBody CardUserDTO cardUserDTO) {
        try {
            CardUser cardUser = cardUserDTO.toModel();
            return cardUserService.insert(cardUser).toDto();
        } catch (IdMustNotBeNullException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage()
            );
        }
    }


    @DeleteMapping("/v1/{id}")
    public Boolean delete(@RequestParam Long id) {
        return cardUserService.deleteById(id);
    }

    @GetMapping("/v1/{id}")
    public CardUserDTO getById(@RequestParam Long id) {
        return cardUserService.findById(id).toDto();
    }


}
