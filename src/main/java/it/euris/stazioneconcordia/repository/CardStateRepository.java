package it.euris.stazioneconcordia.repository;

import it.euris.stazioneconcordia.data.enums.ListLabel;
import it.euris.stazioneconcordia.data.model.Card;
import it.euris.stazioneconcordia.data.model.CardState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CardStateRepository extends JpaRepository<CardState, String> {

    CardState findByCardIdAndFromList(String idCard, ListLabel fromList);
}
