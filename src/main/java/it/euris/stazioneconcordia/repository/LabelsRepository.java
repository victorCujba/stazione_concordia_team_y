package it.euris.stazioneconcordia.repository;

import it.euris.stazioneconcordia.data.model.Labels;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LabelsRepository extends JpaRepository<Labels, Long> {

    String SELECT_LABEL_BY_ID_TRELLO = "SELECT * " +
            "FROM labels " +
            "WHERE labels.id_trello = :id_trello ";

    String SELECT_ID_TRELLO = "SELECT labels.id_trello " +
            " FROM labels";
    String SELECT_DEFAULT_LABEL = "SELECT labels.id" +
            "FROM labels " +
            "WHERE labels.name = DefaultLabel ";

    String SELECT_LABEL_BY_NAME = "SELECT *" +
            "FROM labels" +
            "WHERE labels.name = :name";

    @Query(value = SELECT_LABEL_BY_ID_TRELLO, nativeQuery = true)
    Labels getLabelByIdTrello(@Param("id_trello") String idTrello);

    @Query(value = SELECT_ID_TRELLO, nativeQuery = true)
    List<String> getAllIdTrelloLabels();

    @Query(value = SELECT_DEFAULT_LABEL, nativeQuery = true)
    Long getDefaultLabel();

    @Query(value = SELECT_LABEL_BY_NAME, nativeQuery = true)
    Long getLabelIdByNameFromDb(@Param("name") String highPriority);
}