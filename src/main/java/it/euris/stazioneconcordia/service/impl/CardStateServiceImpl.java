package it.euris.stazioneconcordia.service.impl;

import it.euris.stazioneconcordia.data.dto.CardStateDTO;
import it.euris.stazioneconcordia.data.enums.ListLabel;
import it.euris.stazioneconcordia.data.model.Card;
import it.euris.stazioneconcordia.data.model.CardState;
import it.euris.stazioneconcordia.repository.CardRepository;
import it.euris.stazioneconcordia.repository.CardStateRepository;
import it.euris.stazioneconcordia.service.CardStateService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        return cardStateRepository.save(cardState);
    }

    @Override
    public CardState update(CardState cardState) {
        return cardStateRepository.save(cardState);
    }

    @Override
    public Boolean deleteById(String idCardState) {
        cardStateRepository.deleteById(idCardState);
        return cardStateRepository.findById(idCardState).isEmpty();
    }

    @Override
    public CardState findById(String idCardState) {
        return cardStateRepository.findById(idCardState).orElse(CardState.builder().build());
    }
}
