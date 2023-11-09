package it.euris.stazioneconcordia.repository;

import it.euris.stazioneconcordia.data.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {

    String SELECT_CARD_BY_PRIORITY =
            "SELECT card.id, card.id_trello, card.name, card.position, card.priority, card.description, card.expiration_date, card.date_last_activity, " +
                    " card.closed, card.id_list, card.id_user, card.id_label "
                    + "FROM card "
                    + "WHERE card.id_label = :id_label";


    String SELECT_ID_TRELLO_FROM_CARD = "SELECT card.id_trello FROM card ";

    String SELECT_CARD_BY_ID_TRELLO_AND_ID_LABEL = "SELECT card.id, card.id_trello, card.name, card.position, card.priority, card.description, card.expiration_date, card.date_last_activity, " +
            " card.closed, card.id_list, card.id_user, card.id_label "
            + " FROM Card "
            + " WHERE card.id_trello = :id_trello "
            + " AND card.id_list = :id_label ";


    @Query(value = SELECT_CARD_BY_PRIORITY, nativeQuery = true)
    List<Card> findByPriority(@Param("id_label") Long idLabel);


    @Query(value = "SELECT * FROM card WHERE card.id_trello = :id_trello", nativeQuery = true)
    Card getCardByIdTrello(@Param("id_trello") String idTrelloCard);

    @Query(value = SELECT_ID_TRELLO_FROM_CARD, nativeQuery = true)
    List<String> getTrelloIds();


    @Query(value = SELECT_CARD_BY_ID_TRELLO_AND_ID_LABEL, nativeQuery = true)
    Card findByTrelloIdAndIdLabel(@Param("id_trello") String idTrello, @Param("id_label") Long idLabel);


    @Query(value = "SELECT * FROM card  WHERE card.expiration_date BETWEEN :startDate AND :endDate", nativeQuery = true)
    List<Card> getExpiringIn5DaysCards(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}
