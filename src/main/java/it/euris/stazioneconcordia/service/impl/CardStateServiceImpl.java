package it.euris.stazioneconcordia.service.impl;

import it.euris.stazioneconcordia.data.dto.CardStateDTO;
import it.euris.stazioneconcordia.data.enums.ListLabel;
import it.euris.stazioneconcordia.data.model.Card;
import it.euris.stazioneconcordia.data.model.CardState;
import it.euris.stazioneconcordia.data.model.Lists;
import it.euris.stazioneconcordia.repository.CardRepository;
import it.euris.stazioneconcordia.repository.CardStateRepository;
import it.euris.stazioneconcordia.service.CardStateService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CardStateServiceImpl implements CardStateService {

    CardRepository cardRepository;

    CardStateRepository cardStateRepository;


    @Override
    public List<CardState> findAll() {
        return cardStateRepository.findAll();
    }


    public CardStateDTO updateCardState(String idCard, ListLabel fromList, ListLabel toList) {
        CardState cardState = cardStateRepository.findByCardIdAndFromList(idCard, fromList);
        if (cardState != null) {
            cardState.setToList(toList);
            cardState = cardStateRepository.save(cardState);
            return cardState.toDto();
        } else {

            //TODO handle when CardState is not found
            return null;
        }
    }

}
