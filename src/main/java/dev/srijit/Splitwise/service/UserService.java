package dev.srijit.Splitwise.service;

import dev.srijit.Splitwise.entity.User;

public interface UserService {
    User signup(String name, String email, String password);
    User login(String email, String password);
}
