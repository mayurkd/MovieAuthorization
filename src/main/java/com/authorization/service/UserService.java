package com.authorization.service;

import com.authorization.model.User;

import java.util.List;

public interface UserService {
    public User addUser(User user);// user registration

    public boolean loginUser(String loginid, String password);// login

    public List<User> getAllUsers();// will be visible only if you are logged in

    public Object passwordReset(String userId, User user);
}
