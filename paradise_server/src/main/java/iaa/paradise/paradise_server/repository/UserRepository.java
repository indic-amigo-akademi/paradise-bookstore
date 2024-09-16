package iaa.paradise.paradise_server.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import iaa.paradise.paradise_server.model.User;

public interface UserRepository extends MongoRepository<User, Long> {
    
}
