package gtn.kobi.backend.repository.impl;

import gtn.kobi.backend.model.Users;
import gtn.kobi.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("UserRepository")
public class UserRepositoryImpl implements UserRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Override
    public Optional<Users> findByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new Object[]{username}, new BeanPropertyRowMapper<>(Users.class)));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }


    @Override
    public Users save(Users user) {
        String sql = "INSERT INTO users(first_name, last_name, username, password) VALUES (?,?,?,?)";
        jdbcTemplate.update(sql,user.getFirstName(),user.getLastName(),user.getUsername(),user.getPassword());
        return user;
    }
}
