package gtn.kobi.backend.repository;

import gtn.kobi.backend.model.Hobby;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HobbyRepository extends JpaRepository<Hobby, Integer> {
    List<Hobby> findByUserId(Integer userId);
}
