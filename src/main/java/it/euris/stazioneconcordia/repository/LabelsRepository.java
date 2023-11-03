package it.euris.stazioneconcordia.repository;

import it.euris.stazioneconcordia.data.model.Labels;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LabelsRepository extends JpaRepository<Labels, Long> {

    String SELECT_LABEL_BY_ID_TRELLO = "SELECT labels.id, labels.id_trello, labels.id_board, labels.name, labels.color, labels.uses " +
            "FROM labels " +
            "WHERE labels.id_trello =:id_trello ";

    String SELECT_ID_TRELLO = "SELECT labels.id_trello " +
            " FROM labels";

    @Query(value = SELECT_LABEL_BY_ID_TRELLO, nativeQuery = true)
    Labels getLabelByIdTrello(@Param("id_trello") String idTrello);

    @Query(value = SELECT_ID_TRELLO, nativeQuery = true)
    List<String> getAllIdTrelloLabels();

}