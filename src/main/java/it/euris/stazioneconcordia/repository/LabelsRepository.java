package it.euris.stazioneconcordia.repository;

import it.euris.stazioneconcordia.data.model.Labels;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LabelsRepository extends JpaRepository<Labels, String> {
}
