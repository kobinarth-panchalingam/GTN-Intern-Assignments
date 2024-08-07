package gtn.kobi.backend.service.impl;

import gtn.kobi.backend.model.Users;
import gtn.kobi.backend.repository.UserRepository;
import gtn.kobi.backend.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Users signUp(Users user) {
        //encoding the user password before saving it to the db
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public Users signIn(String userName, String password) {
            Users user = userRepository.findByUsername(userName).orElse(null);
            if (user != null && passwordEncoder.matches(password, user.getPassword())) {
                return user;
            }

            return null;
    }

    @Override
    public Users findByUsername(String username) {
       return userRepository.findByUsername(username).orElse(null);
    }

}
