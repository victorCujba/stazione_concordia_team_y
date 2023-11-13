package it.euris.stazioneconcordia.service.impl;

import it.euris.stazioneconcordia.data.model.CardUser;
import it.euris.stazioneconcordia.exception.IdMustBeNullException;
import it.euris.stazioneconcordia.exception.IdMustNotBeNullException;
import it.euris.stazioneconcordia.repository.CardUserRepository;
import it.euris.stazioneconcordia.service.CardUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CardUserServiceImpl implements CardUserService {

    CardUserRepository cardUserRepository;

    @Override
    public List<CardUser> findAll() {
        return cardUserRepository.findAll();
    }

    @Override
    public CardUser insert(CardUser cardUser) {
        if (cardUser.getId() != null) {
            throw new IdMustBeNullException();
        }
        return cardUserRepository.save(cardUser);
    }

    @Override
    public CardUser update(CardUser cardUser) {
        if (cardUser.getId() == null) {
            throw new IdMustNotBeNullException();
        }
        return cardUserRepository.save(cardUser);
    }

    @Override
    public Boolean deleteById(Long id) {
        cardUserRepository.deleteById(id);
        return cardUserRepository.findById(id).isEmpty();
    }

    @Override
    public CardUser findById(Long id) {
        return cardUserRepository.findById(id).orElse(CardUser.builder().build());
    }
}
