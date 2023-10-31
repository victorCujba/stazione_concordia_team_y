package it.euris.stazioneconcordia.repository;

import it.euris.stazioneconcordia.data.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {

    String SELECT_CARD_BY_PRIORITY =
            "SELECT Card.id, Card.id, Card.idTrello, Card.name, Card.position, Card.description, Card.expirationDate, Card.dateLastActivity "
                    + "FROM Card "
                    + "WHERE Card.priority = :priority";

    @Query(value = SELECT_CARD_BY_PRIORITY, nativeQuery = true)
    List<Card> findByPriority(@Param("priority") String priority);
}
