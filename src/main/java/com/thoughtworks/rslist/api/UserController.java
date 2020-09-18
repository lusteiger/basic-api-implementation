package com.thoughtworks.rslist.api;


import com.thoughtworks.rslist.dto.User;
import com.thoughtworks.rslist.entity.UserEntity;
import com.thoughtworks.rslist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @PostMapping("/user/register")
    public ResponseEntity register(@Valid @RequestBody User user) {

        UserEntity userEntity = UserEntity.builder()
                .userName(user.getUserName())
                .age(user.getAge())
                .gender(user.getGender())
                .email(user.getEmail())
                .phone(user.getPhone())
                .voteNum(user.getVoteNum())
                .build();
        userRepository.save(userEntity);
        return ResponseEntity.created(null).build();
    }

    @GetMapping("/user/query")
    public List<UserEntity> QueryAllUser() {
        return  userRepository.findAll();
    }

    @GetMapping("/user/query/{index}")
    public Optional<UserEntity> QueryUser(@PathVariable int index){
        return userRepository.findById(index-1);
    }

}
