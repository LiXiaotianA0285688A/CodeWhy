package com.example.coderwhy.controller;

import com.example.coderwhy.entity.User;
import com.example.coderwhy.service.UserService;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @GetMapping("/index")
    public String index() {
        return "TEST";
    }

    @GetMapping("/user/getall")
    public List<User> getUserList(){
        return userService.getAllUsers();
    }

    @PostMapping("/user/getfil")
    public List<User> getUsersByFilters(@RequestBody Map<String, Object> request){
        return userService.getUsers(requestToUser(request));
    }

    @PostMapping("/user/create")
    public User createUser(@RequestBody Map<String, Object> request){
        return userService.createUser(requestToUser(request));
    }

    @PostMapping("/user/update")
    public UpdateResult updateUser(@RequestBody Map<String, Object> request){
        return userService.updateUser(requestToUser(request));
    }

    @GetMapping("/user/delete/{user_id}")
    public DeleteResult deleteUser(@PathVariable String user_id){
        return userService.deleteUser(new ObjectId(user_id));
    }


    private User requestToUser(Map<String, Object> request){
        User user = new User();
        if (request.get("_id") != null)
            user.set_id(new ObjectId((String) request.get("_id")));
        if (request.get("username") != null)
            user.setUsername((String) request.get("username"));
        if (request.get("password") != null)
            user.setPassword((String) request.get("password"));
        if (request.get("role") != null)
            user.setRole((String) request.get("role"));
        return user;
    }
}
