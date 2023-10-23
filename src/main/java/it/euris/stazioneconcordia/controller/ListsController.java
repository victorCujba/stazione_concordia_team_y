package it.euris.stazioneconcordia.controller;


import it.euris.stazioneconcordia.data.dto.ListsDTO;
import it.euris.stazioneconcordia.data.model.Lists;
import it.euris.stazioneconcordia.exception.IdMustBeNullException;
import it.euris.stazioneconcordia.exception.IdMustNotBeNullException;
import it.euris.stazioneconcordia.service.ListsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/lists")
public class ListsController {

    ListsService listsService;

    @GetMapping("/v1")
    public List<ListsDTO> findAll() {
        return listsService.findAll().stream().map(Lists::toDto).toList();
    }

    @PostMapping("/v1")
    public ListsDTO insert(@RequestBody ListsDTO listsDTO) {
        try {
            Lists lists = listsDTO.toModel();
            return listsService.insert(lists).toDto();
        } catch (IdMustBeNullException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage()
            );
        }
    }

    @PutMapping("/v1")
    public ListsDTO update(@RequestBody ListsDTO listsDTO) {
        try {
            Lists lists = listsDTO.toModel();
            return listsService.update(lists).toDto();
        } catch (IdMustNotBeNullException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage()
            );
        }
    }

    @DeleteMapping("/v1/{id}")
    public Boolean deleteById(@PathVariable("id") String idCard) {
        return listsService.deleteById(idCard);
    }

    @GetMapping("/v1/{id}")
    public ListsDTO findById(@PathVariable("id") String idCard) {
        return listsService.findById(idCard).toDto();
    }


}
