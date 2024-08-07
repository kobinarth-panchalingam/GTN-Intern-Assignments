package gtn.kobi.backend.controller;

import gtn.kobi.backend.model.Hobby;
import gtn.kobi.backend.service.HobbyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hobby")
public class HobbyController {
    @Autowired
    private HobbyService hobbyService;
    @GetMapping("/{userId}")
    public ResponseEntity<List<Hobby>> getHobbiesByUserId(@PathVariable Integer userId) {
        return ResponseEntity.ok(hobbyService.getHobbiesByUserId(userId));
    }

    @PostMapping("/add")
    public ResponseEntity<Hobby> addHobby(@RequestBody Hobby hobby) {
        return ResponseEntity.ok(hobbyService.addHobby(hobby));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteHobby(@PathVariable Integer id) {
        hobbyService.deleteHobby(id);
        return ResponseEntity.ok().build();
    }
}
