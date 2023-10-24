package it.euris.stazioneconcordia.service.impl;

import it.euris.stazioneconcordia.data.enums.ListLabel;
import it.euris.stazioneconcordia.data.model.Card;
import it.euris.stazioneconcordia.data.model.CardState;
import it.euris.stazioneconcordia.data.model.Lists;
import it.euris.stazioneconcordia.exception.IdMustBeNullException;
import it.euris.stazioneconcordia.exception.IdMustNotBeNullException;
import it.euris.stazioneconcordia.repository.CardRepository;
import it.euris.stazioneconcordia.repository.CardStateRepository;
import it.euris.stazioneconcordia.repository.ListsRepository;
import it.euris.stazioneconcordia.service.ListsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ListsServiceImpl implements ListsService {

    ListsRepository listsRepository;

    CardRepository cardRepository;

    CardStateRepository cardStateRepository;

    @Override
    public List<Lists> findAll() {
        return listsRepository.findAll();
    }

    @Override
    public Lists insert(Lists lists) {
//        if (lists.getId() != null) {
//            throw new IdMustBeNullException();
//        }
        return listsRepository.save(lists);
    }

    @Override
    public Lists update(Lists lists) {
//        if (lists.getId() == null) {
//            throw new IdMustNotBeNullException();
//        }
        return listsRepository.save(lists);
    }

    @Override
    public Boolean deleteById(String idLists) {
        listsRepository.deleteById(idLists);
        return listsRepository.findById(idLists).isEmpty();
    }

    @Override
    public Lists findById(String idLists) {
        return listsRepository.findById(idLists).orElse(Lists.builder().build());
    }

    @Override
    public Lists findByLabel(ListLabel toListLabel) {
        return listsRepository.findAll()
                .stream()
                .filter(lists -> lists.getLabel() == toListLabel)
                .findFirst()
                .orElse(null);
    }


}
