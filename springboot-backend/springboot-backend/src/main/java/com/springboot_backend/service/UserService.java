package com.springboot_backend.service;

import com.springboot_backend.dao.User;

public interface UserService {
    User getUserById(int user_id);
    Integer userLogin(String user_nameOrEmail, String password);
    Integer addNewUser(User user);
    User updateUserById(int user_id, String user_name, String email, String phone, String password);
}
