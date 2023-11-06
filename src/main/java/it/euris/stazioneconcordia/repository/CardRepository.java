package it.euris.stazioneconcordia.repository;

import it.euris.stazioneconcordia.data.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface CardRepository extends JpaRepository<Card, Long> {

    String SELECT_CARD_BY_PRIORITY =
            "SELECT Card.id, Card.idTrello, Card.name, Card.position, Card.description, Card.expirationDate, Card.dateLastActivity "
                    + "FROM Card "
                    + "WHERE Card.priority = :priority";

    String SELECT_BY_TRELLO_ID = "SELECT Card.id " +
            "FROM Card " +
            " WHERE Card.id_trello = :id_trello ";

    String SELECT_ID_TRELLO_FROM_CARD = "SELECT card.id_trello FROM card ";

    String SELECT_CARD_BY_ID_TRELLO_AND_ID_LABEL = "SELECT Card.id, card.id_trello, card.name, card.position, card.description, card.date_last_activity " +
            "FROM Card " +
            "WHERE card.id_trello = :id_trello " +
            "AND card.id_list = :id_list ";


    @Query(value = SELECT_CARD_BY_PRIORITY, nativeQuery = true)
    List<Card> findByPriority(@Param("priority") String priority);


    @Query(value = SELECT_BY_TRELLO_ID, nativeQuery = true)
    Long getCardByIdTrello(@Param("id_trello") String idTrelloCard);

    @Query(value = SELECT_ID_TRELLO_FROM_CARD, nativeQuery = true)
    List<String> getTrelloIds();


    @Query(value = SELECT_CARD_BY_ID_TRELLO_AND_ID_LABEL, nativeQuery = true)
    Card findByTrelloIdAndIdLabel(@Param("id_trello") String idTrello, @Param("id_list") Long idLabel);
}
