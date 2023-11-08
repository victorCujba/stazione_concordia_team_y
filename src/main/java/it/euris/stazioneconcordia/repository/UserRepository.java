package it.euris.stazioneconcordia.repository;

import it.euris.stazioneconcordia.data.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, String> {

    @Query(value = "SELECT * FROM user WHERE user.full_name = :full_name ", nativeQuery = true)
    User getUserIdByUsername(@Param("full_name") String userName);
}
