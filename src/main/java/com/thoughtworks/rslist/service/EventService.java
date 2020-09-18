package com.thoughtworks.rslist.service;

import com.thoughtworks.rslist.entity.EventEntity;
import com.thoughtworks.rslist.exceptions.NotFoundUserException;
import com.thoughtworks.rslist.repository.EventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.swing.text.html.parser.Entity;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {
    @Autowired
    EventRepository eventRepository;
    @Autowired
    UserRepository userRepository;

    public List<EventEntity> findAllEvent() {
        return eventRepository.findAll();
    }

    public List<EventEntity> findEventBetweenStartAndEnd(int start, int end) {
        return eventRepository.findByIdBetween(start, end);
    }

    public ResponseEntity AddEvent(EventEntity eventEntity) {

        int id = eventEntity.getUserId();
        if (userRepository.findById(id).equals(Optional.empty()))
            return ResponseEntity.status(400).body("the user dose not register!");

        eventRepository.save(eventEntity);
        return ResponseEntity.ok().build();
    }

    public int findSize() {
        List<EventEntity> entityList = eventRepository.findAll();
        return entityList.size();
    }


}
