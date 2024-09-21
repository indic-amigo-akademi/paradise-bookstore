package iaa.paradise.paradise_server.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import iaa.paradise.paradise_server.models.User;

public interface UserRepository extends MongoRepository<User, Long> {
    List<User> findByName(String name);
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findByPhone(String phone);
}
