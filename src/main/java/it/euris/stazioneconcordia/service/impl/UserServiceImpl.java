package it.euris.stazioneconcordia.service.impl;

import it.euris.stazioneconcordia.data.model.User;
import it.euris.stazioneconcordia.exception.IdMustBeNullException;
import it.euris.stazioneconcordia.exception.IdMustNotBeNullException;
import it.euris.stazioneconcordia.repository.UserRepository;
import it.euris.stazioneconcordia.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    UserRepository userRepository;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User insert(User user) {

        if (user.getId() != null) {
            throw new IdMustBeNullException();
        }
        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
        if (user.getId() == null) {
            throw new IdMustNotBeNullException();
        }
        return userRepository.save(user);
    }

    @Override
    public Boolean deleteById(String idUser) {
        userRepository.deleteById(idUser);
        return userRepository.findById(idUser).isEmpty();

    }

    @Override
    public User findById(String idUser) {

        return userRepository.findById(idUser).orElse(User.builder().build());
    }

}
