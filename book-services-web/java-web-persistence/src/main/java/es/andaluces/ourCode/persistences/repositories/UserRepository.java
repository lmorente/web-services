package es.andaluces.ourCode.persistences.repositories;

import es.andaluces.ourCode.persistences.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByNick(String nick);

}
