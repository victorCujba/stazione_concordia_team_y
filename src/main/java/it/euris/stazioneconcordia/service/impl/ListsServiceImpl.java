package it.euris.stazioneconcordia.service.impl;

import it.euris.stazioneconcordia.data.model.Lists;
import it.euris.stazioneconcordia.exception.IdMustBeNullException;
import it.euris.stazioneconcordia.exception.IdMustNotBeNullException;
import it.euris.stazioneconcordia.repository.ListsRepository;
import it.euris.stazioneconcordia.service.ListsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ListsServiceImpl implements ListsService {

    ListsRepository listsRepository;

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
}
