package iaa.paradise.paradise_server.service;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import iaa.paradise.paradise_server.models.User;
import iaa.paradise.paradise_server.repository.UserRepository;
import iaa.paradise.paradise_server.utils.validation.FieldValueExists;

@Service
public class UserService implements FieldValueExists {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public boolean fieldValueExists(String fieldName, Object value) throws UnsupportedOperationException {
        Assert.notNull(fieldName, fieldName + " must not be null.");

        if (value == null) {
            return false;
        }
        
        Method m;
        Optional<User> user;
        String methodName = "getUserBy" + StringUtils.capitalize(fieldName);

        try {
            m = this.getClass().getDeclaredMethod(methodName, String.class);
            user = (Optional<User>) m.invoke(this, value.toString());
        } catch (NoSuchMethodException e) {
            // Check if method not exists
            throw new UnsupportedOperationException(String.format("Method %s not defined.", methodName));
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new UnsupportedOperationException("Field name not supported");
        }

        return user.isPresent();
    }
}
