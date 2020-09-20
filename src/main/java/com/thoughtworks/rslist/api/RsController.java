package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.dto.User;
import com.thoughtworks.rslist.entity.EventEntity;
import com.thoughtworks.rslist.entity.UserEntity;
import com.thoughtworks.rslist.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import com.thoughtworks.rslist.dto.Event;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class RsController {

    @Autowired
    EventService eventService;





    @GetMapping("/rs/query/{index}")
    private ResponseEntity<Event> getOneEvent(@PathVariable int index) {

            Event result = eventService.findById(index);
            return ResponseEntity.ok().body(result);

    }

    @GetMapping("/rs/event")
    private ResponseEntity<List<Event>> getStartUntilEnd
            (@RequestParam(required = false) Integer start,
             @RequestParam(required = false) Integer end) {

        if (start == null || end == null) {
            return ResponseEntity.ok().
                    body(eventService.findAllEvent());

        }

        int size = eventService.findSize();
        if (start < 0 || end > size || start > size) {
            throw new IndexOutOfBoundsException("invalid request param");
        }

        return ResponseEntity.ok().
                body(eventService.findEventBetweenStartAndEnd(start,end));

    }

    @PostMapping("/rs/eventAdd")
    private ResponseEntity addEvent (@Valid @RequestBody Event event) {
        User user = event.getUser();
        UserEntity userEntity = UserEntity.builder()
                .id(user.getId())
                .userName(user.getUserName())
                .age(user.getAge())
                .gender(user.getGender())
                .email(user.getEmail())
                .phone(user.getPhone())
                .voteNum(user.getVoteNum())
                .build();
        EventEntity eventEntity = EventEntity.builder()
                .event(event.getEvent())
                .keywords(event.getKeywords())
                .voteNum(event.getVoteNum())
                .user(userEntity)
                .build();

        ResponseEntity responseEntity=eventService.AddEvent(eventEntity);

        return responseEntity;
    }

    @PutMapping("/rs/update/{rsEventId}")
    private ResponseEntity ModifyEvent(@RequestBody Event events, @PathVariable int rsEventId)  {



        EventEntity eventEntity = EventEntity.builder()
                .id(rsEventId)
                .event(events.getEvent())
                .keywords(events.getKeywords())
                .user(UserEntity.builder()
                        .id(events.getUserId())
                        .build())
                .build();

        ResponseEntity responseEntity =  eventService.UpdateEventById(eventEntity,rsEventId);

      return   responseEntity;
    }

    @DeleteMapping("/rs/event/Delete/{index}")
    private ResponseEntity DeleteEvent(@PathVariable int index) {
            ResponseEntity responseEntity =eventService.DeleteEventById(index);
            return responseEntity;
    }
}
