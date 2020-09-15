package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.dto.Events;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class RsController {


    public List<Events> initList() {
        List<Events> initlist = new ArrayList<>();
        initlist.add(new Events("第一条事件", "无主题"));
        initlist.add(new Events("第二条事件", "无主题"));
        initlist.add(new Events("第三条事件", "无主题"));

        return initlist;
    }
    private List<Events> rsList = initList();


    @GetMapping("/rs/{index}")
    private String getOneEvent(@PathVariable int index) {
        return rsList.get(index - 1).getEvent().toString();
    }

    @GetMapping("/rs/event")
    private List<Events> getStartUntilEnd(@RequestParam(required = false) Integer start,
                                    @RequestParam(required = false) Integer end) {
        if (start == null ||end == null) {
            return rsList;
        }
        return rsList.subList(start-1,end);
    }

    @PostMapping("/rs/event")
    private void addEvent(@RequestBody Events events){
        rsList.add(events);
    }
}
