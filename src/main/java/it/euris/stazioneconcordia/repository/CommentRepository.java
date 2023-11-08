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

    @Query(value = "SELECT * FROM comment WHERE id_card=?1", nativeQuery = true)
    List<Comment> findCommentByIdCard(String idCard);


    @Query(value = "SELECT * FROM comment WHERE id_card = :id_card ORDER BY date DESC LIMIT 1 ", nativeQuery = true)
    Comment findLastCommentByIdCard(@Param("id_card") Long idCard);
}
