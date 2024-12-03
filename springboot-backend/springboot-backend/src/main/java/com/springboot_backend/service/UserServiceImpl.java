package com.springboot_backend.service;

import com.springboot_backend.dao.User;
import com.springboot_backend.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service

public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public User getUserById(int id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("用户不存在"));
    }

    @Override
    public Integer userLogin(String user_nameOrEmail, String password) {
        // 传入用户名或者邮箱
        List<User> userByName = userRepository.findByName(user_nameOrEmail);
        // 首先默认是用户名登录，检查
        if(!userByName.isEmpty()){
            if(userByName.get(0).getPassword().equals(password)){
                return userByName.get(0).getId();
            }
            // 密码错误
            throw new IllegalArgumentException("密码错误");
        }
        // 否则在邮箱中查找
        else{
            List<User> userByEmail = userRepository.findByEmail(user_nameOrEmail);
            if(!userByEmail.isEmpty()){
                if(userByEmail.get(0).getPassword().equals(password)){
                    return userByEmail.get(0).getId();
                }
                throw new IllegalArgumentException("密码错误");
            }
            // 否则用户名或者邮箱不存在
            else {
                throw new IllegalArgumentException("用户名或邮箱不存在");
            }
        }
    }

    @Override
    public Integer addNewUser(User user) {
        // 判断用户名不能重复
        List<User> usersByName = userRepository.findByName(user.getName());
        if(!usersByName.isEmpty()) {
            throw new IllegalArgumentException("用户名已存在");
        }
        List<User> usersByEmail = userRepository.findByEmail(user.getEmail());
        if(!usersByEmail.isEmpty()) {
            throw new IllegalArgumentException("邮箱已存在");
        }
        User newUser = userRepository.save(user);
        return newUser.getId();
    }

    @Override
    @Transactional
    public User updateUserById(int id, String name, String email, String phone, String password) {
        User userInDB = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("用户不存在"));
        // 检查修改后的用户名独立性
        List<User> usersByName = userRepository.findByName(userInDB.getName());
        if(!usersByName.isEmpty() && usersByName.get(0).getId() != id) {
            throw new IllegalArgumentException("用户名已存在");
        }
        // 检查修改后的email独立性
        List<User> usersByEmail = userRepository.findByEmail(userInDB.getEmail());
        if(!usersByEmail.isEmpty() && usersByEmail.get(0).getId() != id) {
            throw new IllegalArgumentException("邮箱已存在");
        }

        // 合法，修改信息
        userInDB.setName(name);
        userInDB.setEmail(email);
        userInDB.setPhone(phone);
        userInDB.setPassword(password);
        return userRepository.save(userInDB);
    }
}
