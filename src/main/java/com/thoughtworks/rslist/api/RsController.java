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



    public List<Events> InitList() {
        List<Events> initList = new ArrayList<>();
        initList.add(new Events("第一条事件", "无主题",
                new User("小钱", 18, "female", "twuc@thoughtworks.com","11234567890")));
        initList.add(new Events("第二条事件", "无主题",
                new User("小李", 30, "male", "twuc@thoughtworks.com","11234567890")));
        initList.add(new Events("第三条事件", "无主题",
                new User("小张", 23, "male", "twuc@thoughtworks.com","11234567890")));

        return initList;
    }

    private List<Events> rsList = InitList();

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
        Boolean register = false;
        for (int i = 0; i < rsList.size(); i++) {
            if (events.getUser().equals(rsList.get(i).getUser())){
                register = true;
                break;
            }
        }
        if (register){
            rsList.add(events);
        }
        else {
            rsList.add(events);
            new UserController().register(events.getUser());
        }

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
