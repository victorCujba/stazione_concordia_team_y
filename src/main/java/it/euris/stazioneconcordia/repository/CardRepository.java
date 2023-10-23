package it.euris.stazioneconcordia.repository;

import it.euris.stazioneconcordia.data.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, String> {
}
