package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.entity.EventEntity;
import com.thoughtworks.rslist.entity.UserEntity;
import com.thoughtworks.rslist.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import com.thoughtworks.rslist.dto.Event;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RsController {

    @Autowired
    EventService eventService;


//    @GetMapping("/rs/list")
//    public ResponseEntity<List<Event>> getAllRsEvent() {
//
//        return ResponseEntity.ok().body();
//    }
//
//
//    @GetMapping("/rs/{index}")
//    private ResponseEntity<Event> getOneEvent(@PathVariable int index) {
//
//        if (index < 0 || index >= rsList.size())
//            throw new IndexOutOfBoundsException("invalid index");
//        return ResponseEntity.ok().body(rsList.get(index - 1));
//
//    }
//
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

//    @PostMapping("/rs/eventAdd")
//    private ResponseEntity addEvent (@Valid @RequestBody Event event) {
//        EventEntity eventEntity = EventEntity.builder()
//                .event(event.getEvent())
//                .keywords(event.getKeywords())
//                .userId(event.getUserId())
//                .build();
//
//        ResponseEntity responseEntity=eventService.AddEvent(eventEntity);
//
//        return responseEntity;
//    }

    @PostMapping("/rs/{rsEventId}")
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
//
//    @DeleteMapping("/rs/eventDelete/{index}")
//    private ResponseEntity<List<Event>> DeleteEvent(@PathVariable int index) {
//        rsList.remove(index - 1);
//        return ResponseEntity.ok().body(rsList);
//    }
}
