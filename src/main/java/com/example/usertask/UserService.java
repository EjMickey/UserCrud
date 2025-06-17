package com.example.usertask;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public Iterable<User> getUsers(){
        return userRepository.findAll();
    }
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User with email="+email+" not found"));
    }
    public User getUserByName(String name) {
        return userRepository.findByName(name)
                .orElseThrow(() -> new UserNotFoundException("User with name="+name+"not found"));
    }
    public long countUsers(){
        return userRepository.count();
    }
    public User getUserById(Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id="+id+"not found"));
    }

    public Iterable<User> getAdults(){
        return userRepository.findAllAdults();
    }

    public void saveUser(User user){
        userRepository.save(user);
    }
    public void saveUsers(Iterable<User> users){userRepository.saveAll(users);};

}
