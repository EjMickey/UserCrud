package com.example.usertask;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByName(String name);
    @Query("SELECT u FROM User u WHERE u.age >= 18")
    Iterable<User> findAllAdults();
}
