package com.thoughtworks.rslist.api;

import com.fasterxml.jackson.annotation.JsonView;
import com.thoughtworks.rslist.dto.User;
import com.thoughtworks.rslist.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.dto.Events;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class RsController {

    @Autowired
    UserService userService;

    public List<Events> InitList() {
        List<Events> initList = new ArrayList<>();
        initList.add(new Events("第一条事件", "无主题",
                new User("小钱", 18, "female", "twuc@thoughtworks.com", "11234567890")));
        initList.add(new Events("第二条事件", "无主题",
                new User("小李", 30, "male", "twuc@thoughtworks.com", "11234567890")));
        initList.add(new Events("第三条事件", "无主题",
                new User("小张", 23, "male", "twuc@thoughtworks.com", "11234567890")));

        return initList;
    }

    private List<Events> rsList = InitList();

    @JsonView(Events.WithoutUser.class)
    @GetMapping("/rs/list")
    public ResponseEntity<List<Events>> getAllRsEvent() {
        return ResponseEntity.ok().body(rsList);
    }

    @JsonView(Events.WithoutUser.class)
    @GetMapping("/rs/{index}")
    private ResponseEntity<Events> getOneEvent(@PathVariable int index) {

        if (index < 0 || index >= rsList.size())
            throw new IndexOutOfBoundsException("invalid index");
        return ResponseEntity.ok().body(rsList.get(index - 1));

    }

    @GetMapping("/rs/event")
    private ResponseEntity<List<Events>> getStartUntilEnd
            (@RequestParam(required = false) Integer start,
             @RequestParam(required = false) Integer end) {
        if (start == null || end == null) {
            return ResponseEntity.ok().body(rsList);
        }


        if (start < 1 || end > rsList.size() || start > rsList.size()) {
            throw new IndexOutOfBoundsException("invalid request param");
        }
        return ResponseEntity.ok().body(rsList.subList(start - 1, end));
    }

    @PostMapping("/rs/eventAdd")
    private ResponseEntity<List<Events>> addEvent (@Valid @RequestBody Events events) {
        Boolean register = false;

        for (int i = 0; i < rsList.size(); i++) {
            if (events.getUser().equals(rsList.get(i).getUser())) {
                register = true;
                break;
            }
        }
        if (!register) {
            userService.register(events.getUser());
        }
        rsList.add(events);
        return ResponseEntity.status(201).header("index", String.valueOf(rsList.size() - 1)).body(rsList);
    }

    @PutMapping("/rs/eventModify/{index}")
    private ResponseEntity<List<Events>> ModifyEvent(@RequestBody String events, @PathVariable int index) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Events modifyEvent = objectMapper.readValue(events, Events.class);
        if (modifyEvent.getEvent() == null) {
            modifyEvent.setEvent(rsList.get(index - 1).getEvent());
        }
        if (modifyEvent.getKeywords() == null) {
            modifyEvent.setKeywords(rsList.get(index - 1).getKeywords());
        }
        rsList.set(index - 1, modifyEvent);
        return ResponseEntity.ok().body(rsList);
    }

    @DeleteMapping("/rs/eventDelete/{index}")
    private ResponseEntity<List<Events>> DeleteEvent(@PathVariable int index) {
        rsList.remove(index - 1);
        return ResponseEntity.ok().body(rsList);
    }
}
