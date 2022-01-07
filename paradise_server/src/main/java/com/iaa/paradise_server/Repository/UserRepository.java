package com.iaa.paradise_server.Repository;

import com.iaa.paradise_server.Entity.User;
import com.iaa.paradise_server.Dao.UserDao;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class UserRepository implements UserDao {


    private HashOperations hashOperations;

    public UserRepository(RedisTemplate redisTemplate) {
        this.hashOperations = redisTemplate.opsForHash();
    }
    @Override
    public void create(User user) {
        hashOperations.put("USER", user.getUserId(), user);

    }
    @Override
    public User get(String userId) {
        return (User) hashOperations.get("USER", userId);
    }

    public Map<String, User> getAll(){
        return hashOperations.entries("USER");
    }
    @Override
    public void update(User user) {
        hashOperations.put("USER", user.getUserId(), user);

    }
    @Override
    public void delete(String userId) {
        hashOperations.delete("USER", userId);
    }
}