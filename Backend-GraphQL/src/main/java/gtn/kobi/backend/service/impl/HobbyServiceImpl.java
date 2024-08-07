package gtn.kobi.backend.service.impl;

import gtn.kobi.backend.model.Hobby;
import gtn.kobi.backend.repository.HobbyRepository;
import gtn.kobi.backend.service.HobbyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HobbyServiceImpl implements HobbyService {
    @Autowired
    private HobbyRepository hobbyRepository;
    @Override
    public Hobby addHobby(Hobby hobby) {
<<<<<<< HEAD
        System.out.println("test"+hobby.getUser());
=======
>>>>>>> e5dc6f0 (hobby function added successfully)
        return hobbyRepository.save(hobby);
    }

    @Override
    public void deleteHobby(Integer id) {
        Hobby hobby = hobbyRepository.findById(id).orElse(null);
        if (hobby != null) {
            hobbyRepository.delete(hobby);
        }
    }

    @Override
<<<<<<< HEAD
    public Hobby findHobbyById(Integer id) {
        return hobbyRepository.findById(id).orElse(null);
    }

    @Override
=======
>>>>>>> e5dc6f0 (hobby function added successfully)
    public List<Hobby> getHobbiesByUserId(Integer id) {
        return hobbyRepository.findByUserId(id);
    }
}
