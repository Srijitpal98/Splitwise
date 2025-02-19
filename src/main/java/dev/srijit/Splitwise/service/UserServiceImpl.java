package dev.srijit.Splitwise.service;

import dev.srijit.Splitwise.entity.User;
import dev.srijit.Splitwise.expection.InvalidCredentialException;
import dev.srijit.Splitwise.expection.UserDoesNotExistException;
import dev.srijit.Splitwise.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User signup(String name, String email, String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(encoder.encode(password));
        return userRepository.save(user);
    }

    @Override
    public User login(String email, String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User savedUser = userRepository.findUserByEmail(email);
        if(savedUser == null) {
            throw new UserDoesNotExistException("User not found");
        }
        if(encoder.matches(password, savedUser.getPassword())) {
            return savedUser;
        } else {
            throw new InvalidCredentialException("Invalid credentials");
        }
    }


}
