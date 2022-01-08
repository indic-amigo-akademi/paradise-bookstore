package com.iaa.paradise_server;

import com.iaa.paradise_server.Entity.User;
import com.iaa.paradise_server.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Map;
@Service
public class UsrDetSvc implements UserDetailsService {
    @Autowired
    UserRepository usrdao;
    @Override
    public UserDetails loadUserByUsername(String username){
        Map<String, User> m=usrdao.getAll();
        System.out.println("Here");
        System.out.println("My name is"+username);
        String str="";
        for (User key: m.values()) {
            if(key.getUserName().equals(username)){
                return new UserDetailsImpl(key);
            }
        }
        return null;
    }
}
