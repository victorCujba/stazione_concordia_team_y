package it.euris.stazioneconcordia.repository;

import it.euris.stazioneconcordia.data.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
