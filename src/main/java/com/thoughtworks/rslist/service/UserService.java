package com.thoughtworks.rslist.service;

import com.thoughtworks.rslist.dto.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserService {
    private List<User> userList = new ArrayList<>();

    public void register(User user){
        userList.add(user);
    }

    public List<User> getUserList(){
        return userList;
    }
}
