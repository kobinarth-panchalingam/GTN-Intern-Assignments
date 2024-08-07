package gtn.kobi.backend.service;

import gtn.kobi.backend.model.Hobby;

import java.util.List;

public interface HobbyService {
    Hobby addHobby (Hobby hobby);
    void deleteHobby(Integer id );

    List<Hobby> getHobbiesByUserId(Integer id);
}
