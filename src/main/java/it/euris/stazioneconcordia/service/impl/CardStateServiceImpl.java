package it.euris.stazioneconcordia.service.impl;


import it.euris.stazioneconcordia.data.model.CardState;
import it.euris.stazioneconcordia.exception.IdMustBeNullException;
import it.euris.stazioneconcordia.repository.CardStateRepository;
import it.euris.stazioneconcordia.service.CardStateService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CardStateServiceImpl implements CardStateService {


    CardStateRepository cardStateRepository;


    @Override
    public List<CardState> findAll() {
        return cardStateRepository.findAll();
    }

    @Override
    public CardState insert(CardState cardState) {
        if (cardState.getId() != null) {
            throw new IdMustBeNullException();
    }
        return cardStateRepository.save(cardState);
    }

    @Override
    public CardState update(CardState cardState) {
        return cardStateRepository.save(cardState);
    }

    @Override
    public Boolean deleteById(Long idCardState) {
        cardStateRepository.deleteById(idCardState);
        return cardStateRepository.findById(idCardState).isEmpty();
    }

    @Override
    public CardState findById(Long idCardState) {
        return cardStateRepository.findById(idCardState).orElse(CardState.builder().build());
    }
}
