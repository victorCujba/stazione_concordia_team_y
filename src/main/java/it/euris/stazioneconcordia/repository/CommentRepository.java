package it.euris.stazioneconcordia.repository;

import it.euris.stazioneconcordia.data.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query(value = "SELECT * FROM comment WHERE id_card=?1", nativeQuery = true)
    List<Comment> findCommentByIdCard(String idCard);
}
