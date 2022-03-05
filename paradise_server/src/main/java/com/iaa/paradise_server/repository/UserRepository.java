package com.iaa.paradise_server.repository;

import com.iaa.paradise_server.entity.User;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

    public User findByUsername(String username);

    public boolean existsByEmail(String email);
    public boolean existsByUsername(String username);
}