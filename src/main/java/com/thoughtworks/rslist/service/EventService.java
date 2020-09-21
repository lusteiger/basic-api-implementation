package com.thoughtworks.rslist.service;

import com.thoughtworks.rslist.dto.Event;
import com.thoughtworks.rslist.entity.EventEntity;
import com.thoughtworks.rslist.repository.EventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventService {
    @Autowired
    EventRepository eventRepository;
    @Autowired
    UserRepository userRepository;


    public List<Event> findAllEvent() {
        return eventRepository.findAll().stream().map(Event::from).collect(Collectors.toList());
    }


    public List<Event> findEventBetweenStartAndEnd(int start, int end) {
        return eventRepository.findByIdBetween(start, end).stream()
                .map(Event::from).collect(Collectors.toList());
    }


    public ResponseEntity AddEvent(EventEntity eventEntity) {

        int id = eventEntity.getUser().getId();

        if (userRepository.findById(id).equals(Optional.empty()))
            return ResponseEntity.status(400).body("the user dose not register!");

        eventRepository.save(eventEntity);
        return ResponseEntity.ok().build();
    }


    public int findSize() {
        List<EventEntity> entityList = eventRepository.findAll();
        return entityList.size();
    }


    public ResponseEntity UpdateEventById(EventEntity eventEntity, int id) {

        if (!userRepository.findById(eventEntity.getUser().getId()).isPresent())
            return ResponseEntity.status(400).build();

        Optional<EventEntity> entityOptional = eventRepository.findById(id);
        if (!entityOptional.isPresent())
            throw new RuntimeException();

        EventEntity record = entityOptional.get();
        if (!eventEntity.getEvent().isEmpty())
            record.setEvent(eventEntity.getEvent());
        if (!eventEntity.getKeywords().isEmpty())
            record.setKeywords(eventEntity.getKeywords());

        record.setUser(eventEntity.getUser());
        eventRepository.save(record);
        return ResponseEntity.ok().build();
    }


    public Event findById(int id) {
        Optional<EventEntity> eventEntity = eventRepository.findById(id);
        if (!eventEntity.isPresent()) {
            throw new RuntimeException();
        }
        EventEntity event = eventEntity.get();

        Event result = Event.builder()
                .event(event.getEvent())
                .keywords(event.getKeywords())
                .userId(event.getUser().getId())
                .voteNum(event.getVoteNum())
                .build();
        return result;
    }


    public ResponseEntity DeleteEventById(int id) {
        eventRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
