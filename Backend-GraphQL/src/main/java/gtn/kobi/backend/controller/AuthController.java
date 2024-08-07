package gtn.kobi.backend.controller;

import gtn.kobi.backend.model.Users;
import gtn.kobi.backend.service.UserService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Controller
public class AuthController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

<<<<<<< HEAD
    @MutationMapping
    public Users signUp(@Argument Users userInput) {
        System.out.println(userInput);
        return userService.signUp(userInput);
=======
    @PostMapping("/signup")
    public ResponseEntity<String> signup (@RequestBody Users user) {
        //check if username is already taken
        if (userService.findByUsername(user.getUsername()) != null){
           return  new ResponseEntity<>("Username is already taken", HttpStatus.BAD_REQUEST);
        };

        userService.signUp(user);
        return new ResponseEntity<>("User registereed successfully", HttpStatus.CREATED);
    };

    @PostMapping("/signin")
    public ResponseEntity<Users> signin (@RequestBody Users user) {
        Users authenticatedUser = userService.signIn(user.getUsername(), user.getPassword());
        if (authenticatedUser != null) {
            return ResponseEntity.ok(authenticatedUser);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

>>>>>>> e5dc6f0 (hobby function added successfully)
    }

    @MutationMapping
    public Users signIn(@Argument String username, @Argument String password) {
        return userService.signIn(username, password);
    }

}
