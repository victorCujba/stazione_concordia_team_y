package it.euris.stazioneconcordia.repository;

import it.euris.stazioneconcordia.data.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

public interface BoardRepository extends JpaRepository<Board, Long> {

    String SELECT_BOARD_BY_ID_TRELLO = "SELECT board.id, board.id_trello, board.name, board.description, board.url, board.date_last_activity " +
            "FROM board " +
            "WHERE board.id_trello =:id_trello ";

    String INSERT_BOARD_FROM_TRELLO = "INSERT INTO board (board.id_trello, board.name, board.description, board.url, board.date_last_activity )" +
            "VALUES ( :id_trello, :name, :description, :url, :date_last_activity )";

    @Query(value = SELECT_BOARD_BY_ID_TRELLO, nativeQuery = true)
    Board getBoardByIdTrello(@Param("id_trello") String idTrello);

    @Modifying
    @Query(value = INSERT_BOARD_FROM_TRELLO, nativeQuery = true)
    @Transactional
    Integer insertBoardFromTrello(
            @Param("id_trello") String idTrello,
            @Param("name") String name,
            @Param("description") String description,
            @Param("url") String url,
            @Param("date_last_activity") LocalDateTime dateLastActivity);


}
