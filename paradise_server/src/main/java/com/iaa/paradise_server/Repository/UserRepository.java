package com.iaa.paradise_server.Repository;

import com.iaa.paradise_server.Entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {

    public User findByuserName(String userName);

}