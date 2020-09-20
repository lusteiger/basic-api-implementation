package com.thoughtworks.rslist.api;


import com.thoughtworks.rslist.dto.User;
import com.thoughtworks.rslist.entity.UserEntity;
import com.thoughtworks.rslist.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@ResponseBody
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/user/register")
    public ResponseEntity register(@Valid @RequestBody User user) {

        userService.register(user);
        return ResponseEntity.created(null).build();
    }

    @GetMapping("/user/query")
    public ResponseEntity<List<User>> QueryAllUser() {

        List<User> userList = userService.getUserList();


       return  ResponseEntity.ok( userList);
    }

    @GetMapping("/user/query/{id}")
    public ResponseEntity<Optional<UserEntity>> QueryUser(@PathVariable int id){
        return ResponseEntity.ok().body( userService.getUser(id));
    }


    @DeleteMapping("/user/delete/{id}")
    public void DeleteUser(@PathVariable int id){
        userService.deleteById(id);
    }

}
