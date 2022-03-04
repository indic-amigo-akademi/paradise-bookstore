package com.iaa.paradise_server.Service;

import com.iaa.paradise_server.Entity.User;
import com.iaa.paradise_server.Repository.UserRepository;
import com.iaa.paradise_server.Validation.FieldValueExists;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class UserDetailsDataService implements UserDetailsService, FieldValueExists {
    @Autowired
    UserRepository usrdao;

    private static final Logger logger = LoggerFactory.getLogger(UserDetailsDataService.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u = usrdao.findByUsername(username);
        if (u != null) {
            logger.info(u.toString());
            return new UserDetailsImpl(u);
        }
        throw new UsernameNotFoundException("User not found");
    }

    @Override
    public boolean fieldValueExists(String name, Object value) throws UnsupportedOperationException {
        Assert.notNull(name, name + " must not be null");

        if (value == null) {
            return false;
        }

        if (name.equals("email")) {
            return usrdao.existsByEmail(value.toString());
        }
        if (name.equals("username")) {
            return usrdao.existsByUsername(value.toString());
        }

        throw new UnsupportedOperationException("Field name not supported");
    }
}
