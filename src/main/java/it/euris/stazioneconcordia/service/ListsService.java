package it.euris.stazioneconcordia.service;


import it.euris.stazioneconcordia.data.model.Lists;

import java.util.List;

public interface ListsService {


    List<Lists> findAll();

    Lists insert(Lists lists);

    Lists update(Lists lists);

    Boolean deleteById(Long idLists);

    Lists findById(Long idLists);


}
