package it.euris.stazioneconcordia.service;


import java.util.List;

public interface ListService {


    List<it.euris.stazioneconcordia.data.model.List> findAll();

    it.euris.stazioneconcordia.data.model.List insert(it.euris.stazioneconcordia.data.model.List list);

    it.euris.stazioneconcordia.data.model.List update(it.euris.stazioneconcordia.data.model.List list);

    Boolean deleteById(Long idList);

    it.euris.stazioneconcordia.data.model.List findById(Long idList);


}
