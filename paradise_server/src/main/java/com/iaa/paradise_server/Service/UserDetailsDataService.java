package com.iaa.paradise_server.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.iaa.paradise_server.Entity.User;
import com.iaa.paradise_server.Repository.UserRepository;
import com.iaa.paradise_server.Validation.FieldValueExists;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
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

class UserSpecification implements Specification<User> {
    private final String fieldName;
    private final Object fieldValue;

    public UserSpecification(String fieldName, Object fieldValue) {
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    @Override
    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        return builder.equal(root.get(fieldName), fieldValue);
    }
}