package it.euris.stazioneconcordia.repository;

import it.euris.stazioneconcordia.data.model.Comment;
import it.euris.stazioneconcordia.data.trelloDto.CommentTrelloDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    String INSERT_INTO_COMMENT = "INSERT INTO comment (comment.id_trello, comment.date, comment.comment_body, comment.deleted, comment.id_card, comment.id_user )" +
            "VALUES (:id_trello, :date, :comment_body, :deleted, :id_card, :id_user )";

    @Query(value = "SELECT * FROM comment WHERE id_card=?1", nativeQuery = true)
    List<Comment> findCommentByIdCard(String idCard);

    @Modifying
    @Query(value = INSERT_INTO_COMMENT, nativeQuery = true)
    @Transactional
    Integer insertComment(
            @Param("id_trello") String idTrello,
            @Param("date") LocalDateTime date,
            @Param("comment_body") String commentBody,
            @Param("deleted") Boolean deleted,
            @Param("id_card") Long idCard,
            @Param("id_user") String idUser
    );

    @Query(value = "SELECT * FROM comment WHERE id_card = :id_card ORDER BY date DESC LIMIT 1 ", nativeQuery = true)
    Comment findLastCommentByIdCard(@Param("id_card") Long idCard);
}
