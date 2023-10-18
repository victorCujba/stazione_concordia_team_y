package it.euris.stazioneconcordia.repository;

import it.euris.stazioneconcordia.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
