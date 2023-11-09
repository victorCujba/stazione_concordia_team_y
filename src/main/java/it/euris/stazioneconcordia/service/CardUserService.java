package it.euris.stazioneconcordia.service;


import it.euris.stazioneconcordia.data.model.CardUser;

import java.util.List;

public interface CardUserService {

    List<CardUser> findAll();

    CardUser insert(CardUser cardUser);

    CardUser update(CardUser cardUser);

    Boolean deleteById(Long id);

    CardUser findById(Long id);


}
