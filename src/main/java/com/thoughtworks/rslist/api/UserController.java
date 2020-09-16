package com.thoughtworks.rslist.api;


import com.thoughtworks.rslist.dto.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {


    private List<User> init() {
        List<User> initList = new ArrayList<>();
        User user = new User("小虎", 30,
                "male", "twuc@thoughtworks.com", "11234567890");
        initList.add(user);
        return initList;
    }


    private List<User> userList = init();

    @PostMapping("/user/register")
    public void register(@Valid @RequestBody User user) {
        userList.add(user);
    }

    @GetMapping("/user/query")
    public List<User> QueryUser() {
        return userList;
    }
}
