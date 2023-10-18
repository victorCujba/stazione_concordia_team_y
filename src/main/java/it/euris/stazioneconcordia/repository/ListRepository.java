package it.euris.stazioneconcordia.repository;

import it.euris.stazioneconcordia.data.model.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListRepository extends JpaRepository<List,Long> {
}
