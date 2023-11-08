package it.euris.stazioneconcordia.service;

import it.euris.stazioneconcordia.data.model.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User insert(User user);

    User update(User user);

    Boolean deleteById(String idUser);

    User findById(String idUser);


    User getUserByUserName(String userName);
}
