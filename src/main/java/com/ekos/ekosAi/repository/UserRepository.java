package com.ekos.ekosAi.repository;

import com.ekos.ekosAi.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User,String> {

    Optional<User> findOneByEmail(String email);


}
