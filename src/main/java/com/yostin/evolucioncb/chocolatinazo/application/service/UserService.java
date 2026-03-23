package com.yostin.evolucioncb.chocolatinazo.application.service;

import com.yostin.evolucioncb.chocolatinazo.domain.models.User;
import com.yostin.evolucioncb.chocolatinazo.domain.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User createUser(User user){
        return userRepository.save(user);
    }
}
