package gtn.kobi.backend.repository.impl;

import gtn.kobi.backend.model.Hobby;
import gtn.kobi.backend.repository.HobbyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class HobbyRepositoryImpl implements HobbyRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Override
    public List<Hobby> findByUserId(Integer userId) {
        String sql = "SELECT * FROM hobby WHERE user_id = ?";
        return jdbcTemplate.query(sql,new Object[]{userId}, new BeanPropertyRowMapper<>(Hobby.class));
    }

    @Override
    public Hobby save(Hobby hobby) {
        String sql = "INSERT INTO hobby (hobby_name, user_id) VALUES (?,?)";
        jdbcTemplate.update(sql,hobby.getHobbyName(),hobby.getUser().getId());
        return hobby;
    }

    @Override
    public Optional<Hobby> findById(Integer id) {
        String sql = "SELECT * FROM hobby WHERE id = ?";
        List<Hobby> hobbies = jdbcTemplate.query(sql, new Object[]{id}, new BeanPropertyRowMapper<>(Hobby.class));
        return hobbies.isEmpty() ? Optional.empty() : Optional.of(hobbies.get(0));
    }

    @Override
    public void delete(Hobby hobby) {
        String sql = "DELETE FROM hobby where id = ?";
        jdbcTemplate.update(sql,hobby.getId());
    }

}
