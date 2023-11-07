package it.euris.stazioneconcordia.repository;

import it.euris.stazioneconcordia.data.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface CardRepository extends JpaRepository<Card, Long> {

    String SELECT_CARD_BY_PRIORITY =
            "SELECT card.id, card.id_trello, card.name, card.position, card.priority, card.description, card.expiration_date, card.date_last_activity, " +
                    " card.closed, card.id_list, card.id_user, card.id_label "
                    + "FROM card "
                    + "WHERE card.priority = :priority";

    String SELECT_BY_TRELLO_ID = "SELECT card.id " +
            "FROM card " +
            " WHERE card.id_trello = :id_trello ";

    String SELECT_ID_TRELLO_FROM_CARD = "SELECT card.id_trello FROM card ";

    String SELECT_CARD_BY_ID_TRELLO_AND_ID_LABEL = "SELECT card.id, card.id_trello, card.name, card.position, card.priority, card.description, card.expiration_date, card.date_last_activity, " +
            " card.closed, card.id_list, card.id_user, card.id_label "
            + " FROM Card "
            + " WHERE card.id_trello = :id_trello "
            + " AND card.id_list = :id_label ";
    String SELECT_CARD_BY_ID_TRELLO = "SELECT card.id, card.id_trello, card.name, card.position, card.priority, card.description, card.expiration_date, card.date_last_activity, " +
            " card.closed, card.id_list, card.id_user, card.id_label "
            + " FROM Card "
            + " WHERE card.id_trello = :id_trello ";

    String INSERT_INTO_CARD_TABLE = "INSERT INTO card ( card.id_trello, card.name, card.position, card.priority, card.description, card.expiration_date, card.date_last_activity, card.closed, card.id_list, card.id_label ) " +
            "VALUES (:id_trello, :name, :position, :priority, :description, :expiration_date, :date_last_activity, :closed, :id_list, :id_label )";


    @Query(value = SELECT_CARD_BY_PRIORITY, nativeQuery = true)
    List<Card> findByPriority(@Param("priority") String priority);


    @Query(value = SELECT_BY_TRELLO_ID, nativeQuery = true)
    Long getCardByIdTrello(@Param("id_trello") String idTrelloCard);

    @Query(value = SELECT_ID_TRELLO_FROM_CARD, nativeQuery = true)
    List<String> getTrelloIds();


    @Query(value = SELECT_CARD_BY_ID_TRELLO_AND_ID_LABEL, nativeQuery = true)
    Card findByTrelloIdAndIdLabel(@Param("id_trello") String idTrello, @Param("id_label") Long idLabel);
    @Query(value = SELECT_CARD_BY_ID_TRELLO, nativeQuery = true)
    Card findByTrelloId(@Param("id_trello") String idTrello);

    @Modifying
    @Query(value = INSERT_INTO_CARD_TABLE, nativeQuery = true)
    @Transactional
    Integer insert(
            @Param("id_trello") String idTrello,
            @Param("name") String name,
            @Param("position") Long position,
            @Param("priority") String priority,
            @Param("description") String description,
            @Param("expiration_date") LocalDateTime expirationDate,
            @Param("date_last_activity") LocalDateTime dateLastActivity,
            @Param("closed") Boolean closed,
            @Param("id_list") Long idList,
            @Param("id_label") Long idLabel
    );

}
