package it.euris.stazioneconcordia.service.impl;

import it.euris.stazioneconcordia.exception.IdMustBeNullException;
import it.euris.stazioneconcordia.exception.IdMustNotBeNullException;
import it.euris.stazioneconcordia.repository.ListRepository;
import it.euris.stazioneconcordia.service.ListService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ListServiceImpl implements ListService {

    ListRepository listRepository;
    @Override
    public List<it.euris.stazioneconcordia.data.model.List> findAll() {
        return listRepository.findAll();
    }

    @Override
    public it.euris.stazioneconcordia.data.model.List insert(it.euris.stazioneconcordia.data.model.List list) {
        if (list.getId() != null) {
            throw new IdMustBeNullException();
        }
        return listRepository.save(list);
    }

    @Override
    public it.euris.stazioneconcordia.data.model.List update(it.euris.stazioneconcordia.data.model.List list) {
        if (list.getId() == null) {
            throw new IdMustNotBeNullException();
        }
        return listRepository.save(list);
    }

    @Override
    public Boolean deleteById(Long idList) {
        listRepository.deleteById(idList);
        return listRepository.findById(idList).isEmpty();
    }

    @Override
    public it.euris.stazioneconcordia.data.model.List findById(Long idList) {
        return listRepository.findById(idList).orElse(it.euris.stazioneconcordia.data.model.List.builder().build());
    }
}
