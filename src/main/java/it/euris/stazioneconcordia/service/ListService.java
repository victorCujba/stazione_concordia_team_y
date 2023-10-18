package it.euris.stazioneconcordia.service;


import java.util.List;

public interface ListService {


    List<List> findAll();

    List insert(List list);

    List update(List list);

    Boolean deleteById(Long idList);

    List findById(Long idList);


}
