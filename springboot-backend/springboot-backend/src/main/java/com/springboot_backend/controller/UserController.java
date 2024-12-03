package com.springboot_backend.controller;

import com.springboot_backend.Response;
import com.springboot_backend.dao.User;
import com.springboot_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/query/")
    public Response<User> userQuery(@RequestParam("userID") int id){
        try {
            User user = userService.getUserById(id);
            return Response.newSuccess(user);
        } catch (Exception e) {
            // 处理异常
            return Response.newFail(e.getMessage());
        }
    }

    @PostMapping("/login/")
    public Response<Integer> userLogin(@RequestBody Map<String, String> loginData){
        try {
            String user_nameOrEmail = loginData.get("user_nameOrEmail");
            String password = loginData.get("password");
            return Response.newSuccess(userService.userLogin(user_nameOrEmail, password));
        } catch (Exception e) {
            // 处理异常
            return Response.newFail(e.getMessage());
        }
    }

    @PostMapping("/register/")
    public Response<Integer> userRegister(@RequestBody User user){
        try{
            return Response.newSuccess(userService.addNewUser(user));
        } catch(Exception e) {
            return Response.newFail(e.getMessage());
        }
    }

    @PostMapping("/update/")
    public Response<User> userUpdate(@RequestBody User user){
        try {
            int id = user.getId();
            String name = user.getName();
            String email = user.getEmail();
            String phone = user.getPhone();
            String password = user.getPassword();
            return Response.newSuccess(userService.updateUserById(id, name, email, phone, password));
        }catch (Exception e) {
            return Response.newFail(e.getMessage());
        }
    }
}
