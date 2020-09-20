package com.thoughtworks.rslist.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.dto.Event;
import com.thoughtworks.rslist.dto.User;
import com.thoughtworks.rslist.entity.EventEntity;
import com.thoughtworks.rslist.entity.UserEntity;
import com.thoughtworks.rslist.repository.EventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class RsControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    EventRepository eventRepository;
    @Autowired
    UserRepository userRepository;


    @BeforeEach
    void setUp() {
        eventRepository.deleteAll();
        userRepository.deleteAll();
    }

    public UserEntity getMockUserEntity() {
        UserEntity userEntity = UserEntity.builder()
                .userName("小王")
                .age(18)
                .gender("female")
                .email("twu@tw.com")
                .phone("18812345678")
                .voteNum(10)
                .build();
        return userEntity;
    }

    public EventEntity getMocEventEntity() {
        EventEntity eventEntity = EventEntity.builder()
                .event("添加一条热搜")
                .keywords("娱乐")
                .id(1)
                .build();
        return eventEntity;
    }


    @Test
    void should_get_one_rs_event() throws Exception {

        UserEntity userEntity = UserEntity.builder()
                .userName("小王")
                .age(18)
                .gender("female")
                .email("twu@tw.com")
                .phone("18812345678")
                .voteNum(10)
                .build();

        userEntity = userRepository.save(userEntity);

        EventEntity eventEntity = EventEntity.builder()
                .event("哈哈")
                .keywords("娱乐")
                .user(userEntity)
                .build();

        eventEntity = eventRepository.save(eventEntity);

        mockMvc.perform(get("/rs/event"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].event", is("哈哈")))
                .andExpect(jsonPath("$[0].keywords", is("娱乐")));


        mockMvc.perform(get("/rs/query/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("event", is("哈哈")))
                .andExpect(jsonPath("keywords", is("娱乐")));

    }

    @Test
    void should_get_start_until_end_rs_event() throws Exception {

        UserEntity userEntity = UserEntity.builder()
                .id(1)
                .userName("小王")
                .age(18)
                .gender("female")
                .email("twu@tw.com")
                .phone("18812345678")
                .voteNum(10)
                .build();

        User user = User.builder()
                .userName("小王")
                .age(18)
                .gender("female")
                .email("twu@tw.com")
                .phone("18812345678")
                .voteNum(10)
                .build();

        userRepository.save(userEntity);
        EventEntity event1 = EventEntity.builder()

                .event("哈哈3")
                .keywords("娱乐3")
                .user(userEntity)
                .build();

        EventEntity event2 = EventEntity.builder()
                .id(2)
                .event("哈哈2")
                .keywords("娱乐2")
                .user(userEntity)
                .build();

        eventRepository.save(event2);
        eventRepository.save(event1);
        mockMvc.perform(get("/rs/event"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
        mockMvc.perform(get("/rs/event?start=1&end=2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

    }

    @Test
    void should_add_rs_event_has_user() throws Exception {

        mockMvc.perform(get("/rs/event"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        UserEntity userEntity = UserEntity.builder()
                .userName("小王")
                .age(18)
                .gender("female")
                .email("twu@tw.com")
                .phone("18812345678")
                .voteNum(10)
                .build();
        userRepository.save(userEntity);
        mockMvc.perform(get("/user/query"))
                .andExpect(status().isOk());

        User user = User.builder()
                .id(userEntity.getId())
                .userName("小王")
                .age(18)
                .gender("female")
                .email("twu@tw.com")
                .phone("18812345678")
                .voteNum(10)
                .build();

        Event event = Event.builder()
                .event("哈哈")
                .keywords("娱乐")
                .user(user)
                .userId(user.getId())
                .build();


        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(event);
        mockMvc.perform(post("/rs/eventAdd").content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(get("/rs/event"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].event", is("哈哈")))
                .andExpect(jsonPath("$[0].keywords", is("娱乐")))
                .andExpect(jsonPath("$[0].userId", is(1)));
    }

    @Test
    void should_add_rs_event_without_user() throws Exception {

        mockMvc.perform(get("/rs/event"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        User user = User.builder()
                .userName("小王")
                .age(18)
                .gender("female")
                .email("twu@tw.com")
                .phone("18812345678")
                .voteNum(10)
                .build();

        Event event = Event.builder()
                .event("哈哈")
                .keywords("娱乐")
                .user(user)
                .build();
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(event);
        mockMvc.perform(post("/rs/eventAdd").content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }


    @Test
    void should_delete_one_rs_event() throws Exception {
        UserEntity userEntity = UserEntity.builder()
                .userName("小王")
                .age(18)
                .gender("female")
                .email("twu@tw.com")
                .phone("18812345678")
                .voteNum(10)
                .build();
        userEntity = userRepository.save(userEntity);

        EventEntity eventEntity = EventEntity.builder()
                .event("哈哈")
                .keywords("娱乐")
                .user(userEntity)
                .build();
        eventEntity = eventRepository.save(eventEntity);
        mockMvc.perform(get("/rs/event"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
        mockMvc.perform(delete("/rs/event/Delete/{index}",eventEntity.getId()))
                .andExpect(status().isOk());
        mockMvc.perform(get("/rs/event"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));


    }

    @Test
    void update_event_has_associate_user() throws Exception {
        UserEntity userEntity = UserEntity.builder()
                .userName("小王")
                .age(18)
                .gender("female")
                .email("twu@tw.com")
                .phone("18812345678")
                .voteNum(10)
                .build();

        userEntity = userRepository.save(userEntity);

        EventEntity eventEntity = EventEntity.builder()
                .event("哈哈")
                .keywords("娱乐")
                .user(userEntity)
                .build();

        eventEntity = eventRepository.save(eventEntity);

        Event updateEvent = Event.builder()
                .event("芯片")
                .keywords("科技")
                .userId(userEntity.getId())
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(updateEvent);
        mockMvc.perform(get("/user/query"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
        mockMvc.perform(get("/rs/event"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
        mockMvc.perform(put("/rs/update/" + eventEntity.getId()).content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform((get("/rs/event")))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("[0].id", is(eventEntity.getId())))
                .andExpect(jsonPath("[0].event", is("芯片")))
                .andExpect(jsonPath("[0].keywords", is("科技")))
                .andExpect(jsonPath("[0].userId", is(eventEntity.getUser().getId())))
                .andExpect(status().isOk());


    }

    @Test
    void update_event_only_event_name() throws Exception {
        UserEntity userEntity = UserEntity.builder()
                .userName("小王")
                .age(18)
                .gender("female")
                .email("twu@tw.com")
                .phone("18812345678")
                .voteNum(10)
                .build();

        userEntity = userRepository.save(userEntity);

        EventEntity eventEntity = EventEntity.builder()
                .event("哈哈")
                .keywords("娱乐")
                .user(userEntity)
                .build();

        eventEntity = eventRepository.save(eventEntity);

        Event updateEvent = Event.builder()
                .event("芯片")
                .keywords("娱乐")
                .userId(userEntity.getId())
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(updateEvent);
        mockMvc.perform(get("/user/query"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
        mockMvc.perform(get("/rs/event"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
        mockMvc.perform(put("/rs/update/" + eventEntity.getId()).content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform((get("/rs/event")))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("[0].id", is(eventEntity.getId())))
                .andExpect(jsonPath("[0].event", is("芯片")))
                .andExpect(jsonPath("[0].keywords", is("娱乐")))
                .andExpect(jsonPath("[0].userId", is(eventEntity.getUser().getId())))
                .andExpect(status().isOk());
    }

    @Test
    void update_event_only_keywords() throws Exception {
        UserEntity userEntity = UserEntity.builder()
                .userName("小王")
                .age(18)
                .gender("female")
                .email("twu@tw.com")
                .phone("18812345678")
                .voteNum(10)
                .build();

        userEntity = userRepository.save(userEntity);

        EventEntity eventEntity = EventEntity.builder()
                .event("哈哈")
                .keywords("娱乐")
                .user(userEntity)
                .build();

        eventEntity = eventRepository.save(eventEntity);

        Event updateEvent = Event.builder()
                .event("哈哈")
                .keywords("哈哈")
                .userId(userEntity.getId())
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(updateEvent);
        mockMvc.perform(get("/user/query"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
        mockMvc.perform(get("/rs/event"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
        mockMvc.perform(put("/rs/update/" + eventEntity.getId()).content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform((get("/rs/event")))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("[0].id", is(eventEntity.getId())))
                .andExpect(jsonPath("[0].event", is("哈哈")))
                .andExpect(jsonPath("[0].keywords", is("哈哈")))
                .andExpect(jsonPath("[0].userId", is(eventEntity.getUser().getId())))
                .andExpect(status().isOk());
    }
}
