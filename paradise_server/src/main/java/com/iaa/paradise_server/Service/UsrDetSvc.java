package com.iaa.paradise_server.Service;

import com.iaa.paradise_server.Entity.User;
import com.iaa.paradise_server.Repository.UserRepository;
import com.iaa.paradise_server.Service.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsrDetSvc implements UserDetailsService {
    @Autowired
    UserRepository usrdao;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u=usrdao.findByuserName(username);
        System.out.println(u.toString());
        if(u!=null){
            return new UserDetailsImpl(u);
        }
        throw new UsernameNotFoundException("User not found");
    }
}
