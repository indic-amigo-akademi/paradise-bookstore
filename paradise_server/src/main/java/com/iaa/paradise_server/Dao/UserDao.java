package com.iaa.paradise_server.Dao;
import com.iaa.paradise_server.Entity.User;

public interface UserDao {

    void create(User emp);
    User get(String id);
    void update(User emp);
    void delete(String id);


}
