package it.euris.stazioneconcordia.repository;


import it.euris.stazioneconcordia.data.model.Board;
import it.euris.stazioneconcordia.data.model.Lists;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ListsRepository extends JpaRepository<Lists, Long> {

    String SELECT_LIST_BY_ID_TRELLO = "SELECT list.id, list.id_trello, list.name, list.position, list.closed, list.label, list.id_board " +
            "FROM list " +
            "WHERE list.id_trello = :id_trello ";

    String SELECT_ID_TRELLO = "SELECT list.id_trello " + " FROM list";

    @Query(value = SELECT_LIST_BY_ID_TRELLO, nativeQuery = true)
    Lists getListByIdTrello(@Param("id_trello") String idTrello);

    @Query(value = SELECT_ID_TRELLO, nativeQuery = true)
    List<String> getAllIdTrelloLists();
}
