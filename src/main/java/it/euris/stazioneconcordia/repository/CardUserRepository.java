package it.euris.stazioneconcordia.repository;

import it.euris.stazioneconcordia.data.model.CardUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardUserRepository extends JpaRepository<CardUser, Long> {

}
