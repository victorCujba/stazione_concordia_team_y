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

    @Query(value = SELECT_BOARD_BY_ID_TRELLO, nativeQuery = true)
    Board getBoardByIdTrello(@Param("id_trello") String idTrello);

}
