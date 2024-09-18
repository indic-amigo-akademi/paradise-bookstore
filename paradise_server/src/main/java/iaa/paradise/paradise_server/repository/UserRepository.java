package iaa.paradise.paradise_server.repository;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import iaa.paradise.paradise_server.models.User;

public interface UserRepository extends MongoRepository<User, Long> {
    List<User> findByName(String name);
}
