package gtn.kobi.backend.repository;

import gtn.kobi.backend.model.Users;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository {
    Optional<Users> findByUsername(String username);
    Users save(Users user);
}
