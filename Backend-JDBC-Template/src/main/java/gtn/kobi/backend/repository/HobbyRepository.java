package gtn.kobi.backend.repository;

import gtn.kobi.backend.model.Hobby;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HobbyRepository {
    List<Hobby> findByUserId(Integer userId);
    Hobby save(Hobby hobby);

    Optional<Hobby> findById(Integer id);

    void delete (Hobby hobby);
}
