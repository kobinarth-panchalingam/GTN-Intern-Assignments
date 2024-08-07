package gtn.kobi.backend.service;

import gtn.kobi.backend.model.Hobby;

import java.util.List;

public interface HobbyService {
    Hobby addHobby (Hobby hobby);
    void deleteHobby(Integer id );
<<<<<<< HEAD
    Hobby findHobbyById(Integer id);
=======

>>>>>>> e5dc6f0 (hobby function added successfully)
    List<Hobby> getHobbiesByUserId(Integer id);
}
