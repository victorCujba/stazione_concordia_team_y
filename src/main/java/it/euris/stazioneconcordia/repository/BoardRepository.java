package it.euris.stazioneconcordia.repository;

import it.euris.stazioneconcordia.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board,Long> {
}
