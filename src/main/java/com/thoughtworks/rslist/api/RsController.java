package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.dto.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.dto.Events;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class RsController {



    public List<Events> initList() {
        List<Events> initlist = new ArrayList<>();
        initlist.add(new Events("第一条事件", "无主题",
                new User("小王", 18, "female", "twuc@thoughtworks.com","11234567890")));
        initlist.add(new Events("第二条事件", "无主题",new User("小李", 30, "male", "twuc@thoughtworks.com","11234567890")));
        initlist.add(new Events("第三条事件", "无主题",new User("小张", 12, "female", "twuc@thoughtworks.com","11234567890")));

        return initlist;
    }

    private List<Events> rsList = initList();

    @GetMapping("/rs/list")
    public List<Events> getAllRsEvent() {
        return rsList;
    }


    @GetMapping("/rs/{index}")
    private String getOneEvent(@PathVariable int index) {
        return rsList.get(index - 1).getEvent().toString();
    }

    @GetMapping("/rs/event")
    private List<Events> getStartUntilEnd(@RequestParam(required = false) Integer start,
                                          @RequestParam(required = false) Integer end) {
        if (start == null || end == null) {
            return rsList;
        }
        return rsList.subList(start - 1, end);
    }

    @PostMapping("/rs/eventAdd")
    private void addEvent(@RequestBody Events events) {
        rsList.add(events);
    }

    @PutMapping("/rs/eventModify/{index}")
    private void ModifyEvent(@RequestBody String events, @PathVariable int index) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Events modifyEvent = objectMapper.readValue(events, Events.class);
        if (modifyEvent.getEvent() == null) {
            modifyEvent.setEvent(rsList.get(index - 1).getEvent());
        }
        if (modifyEvent.getKeywords() == null) {
            modifyEvent.setKeywords(rsList.get(index - 1).getKeywords());
        }


        rsList.set(index - 1, modifyEvent);
    }

    @DeleteMapping("/rs/eventDelete/{index}")
    private void DeleteEvent(@PathVariable int index) {
        rsList.remove(index - 1);
    }
}
