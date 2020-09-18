package com.thoughtworks.rslist.api;


import com.thoughtworks.rslist.dto.User;
import com.thoughtworks.rslist.entity.UserEntity;
import com.thoughtworks.rslist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public ResponseEntity<List<UserEntity>> QueryAllUser() {
       return  ResponseEntity.ok( userRepository.findAll());
    }

    @GetMapping("/user/query/{id}")
    public Optional<UserEntity> QueryUser(@PathVariable int id){
        return userRepository.findById(id);
    }


    @DeleteMapping("/user/delete/{id}")
    public void DeleteUser(@PathVariable int id){
        userRepository.deleteById(id);
    }

}
