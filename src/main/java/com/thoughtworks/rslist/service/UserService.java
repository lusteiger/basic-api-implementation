package com.thoughtworks.rslist.service;

import com.thoughtworks.rslist.dto.User;
import com.thoughtworks.rslist.entity.UserEntity;
import com.thoughtworks.rslist.repository.EventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    EventRepository eventRepository;


    public void register(User user) {
        UserEntity userEntity = UserEntity.builder()
                .userName(user.getUserName())
                .age(user.getAge())
                .gender(user.getGender())
                .email(user.getEmail())
                .phone(user.getPhone())
                .voteNum(user.getVoteNum())
                .build();
        userRepository.save(userEntity);
    }

    public List<UserEntity> getUserList() {
        return userRepository.findAll();
    }

    public Optional<UserEntity> getUser(int id) {
        return userRepository.findById(id);
    }

    public void deleteById(int id) {
        userRepository.deleteById(id);
        eventRepository.deleteAllByUserId(id);
    }
}
