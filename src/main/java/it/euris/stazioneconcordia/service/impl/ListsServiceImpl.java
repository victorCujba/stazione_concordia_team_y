package it.euris.stazioneconcordia.service.impl;

import it.euris.stazioneconcordia.data.enums.ListLabel;
import it.euris.stazioneconcordia.data.model.Lists;
import it.euris.stazioneconcordia.exception.IdMustBeNullException;
import it.euris.stazioneconcordia.exception.IdMustNotBeNullException;
import it.euris.stazioneconcordia.repository.CardRepository;
import it.euris.stazioneconcordia.repository.CardStateRepository;
import it.euris.stazioneconcordia.repository.ListsRepository;
import it.euris.stazioneconcordia.service.ListsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
        if (lists.getId() != null) {
            throw new IdMustBeNullException();
        }
        return listsRepository.save(lists);
    }

    @Override
    public Lists update(Lists lists) {
        if (lists.getId() == null) {
            throw new IdMustNotBeNullException();
        }
        return listsRepository.save(lists);
    }

    @Override
    public Boolean deleteById(Long idLists) {
        listsRepository.deleteById(idLists);
        return listsRepository.findById(idLists).isEmpty();
    }

    @Override
    public Lists findById(Long idLists) {
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

    @Override
    public Lists getListByIdTrelloFromDb(String idTrello) {
        return listsRepository.getListByIdTrello(idTrello);
    }

    @Override
    public List<String> getAllIdTrelloForLists() {
        return listsRepository.getAllIdTrelloLists();
    }


}
