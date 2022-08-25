package com.ekos.ekosAi.repository;

import com.ekos.ekosAi.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User,String> {

}
