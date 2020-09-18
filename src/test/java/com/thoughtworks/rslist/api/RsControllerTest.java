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
    void setUp(){
        eventRepository.deleteAll();
        userRepository.deleteAll();
        userRepository.save(getMockUserEntity());
    }

    public UserEntity getMockUserEntity(){
        UserEntity userEntity = UserEntity.builder()

                .userName("小王")
                .age(18)
                .email("twuc@thoughtworks.com")
                .gender("female")
                .phone("11234567890")
                .voteNum(10)
                .build();
        return userEntity;
    }

    public EventEntity getMocEventEntity(){
        EventEntity eventEntity = EventEntity.builder()
                .event("添加一条热搜")
                .keywords("娱乐")
                .id(1)
                .build();
        return eventEntity;
    }





//    @Test
//    void should_get_one_rs_event() throws Exception {
//        mockMvc.perform(get("/rs/1"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("event", is("第一条事件")))
//                .andExpect(jsonPath("keywords", is("无主题")))
//                .andExpect(jsonPath("user").doesNotExist());
//    }
//
//    @Test
//    void should_get_start_until_end_rs_event() throws Exception {
//        mockMvc.perform(get("/rs/event?start=1&end=3"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(3)))
//                .andExpect(jsonPath("$[0].event", is("第一条事件")))
//                .andExpect(jsonPath("$[0].keywords", is("无主题")))
//                .andExpect(jsonPath("$[1].event", is("第二条事件")))
//                .andExpect(jsonPath("$[1].keywords", is("无主题")))
//                .andExpect(jsonPath("$[2].event", is("第三条事件")))
//                .andExpect(jsonPath("$[2].keywords", is("无主题")));
//    }
//
//    @Test
//    void should_get_all_rs_event() throws Exception {
//        mockMvc.perform(get("/rs/event"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(3)))
//                .andExpect(jsonPath("$[0].event", is("第一条事件")))
//                .andExpect(jsonPath("$[0].keywords", is("无主题")))
//                .andExpect(jsonPath("$[1].event", is("第二条事件")))
//                .andExpect(jsonPath("$[1].keywords", is("无主题")))
//                .andExpect(jsonPath("$[2].event", is("第三条事件")))
//                .andExpect(jsonPath("$[2].keywords", is("无主题")));
//    }
//
//    @Test
//    void should_get_rs_list() throws Exception {
//        mockMvc.perform(get("/rs/list"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(3)))
//                .andExpect(jsonPath("$[0].event", is("第一条事件")))
//                .andExpect(jsonPath("$[0].keywords", is("无主题")))
//                .andExpect(jsonPath("$[0]user").doesNotExist())
//                .andExpect(jsonPath("$[1].event", is("第二条事件")))
//                .andExpect(jsonPath("$[1].keywords", is("无主题")))
//                .andExpect(jsonPath("$[1]user").doesNotExist())
//                .andExpect(jsonPath("$[2].event", is("第三条事件")))
//                .andExpect(jsonPath("$[2]user").doesNotExist())
//                .andExpect(jsonPath("$[2].keywords", is("无主题")));
//    }
//
//    @Test
//    void should_add_rs_event_when_user_is_exist() throws Exception {
//
//        mockMvc.perform(get("/user/query"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(1)))
//                .andExpect(jsonPath("$[0].user_name", is("小张")))
//                .andExpect(jsonPath("$[0].user_age", is(23)))
//                .andExpect(jsonPath("$[0].user_gender", is("male")))
//                .andExpect(jsonPath("$[0].user_email", is("twuc@thoughtworks.com")))
//                .andExpect(jsonPath("$[0].user_phone", is("11234567890")));
//        mockMvc.perform(get("/rs/event"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(3)))
//                .andExpect(jsonPath("$[0].event", is("第一条事件")))
//                .andExpect(jsonPath("$[0].keywords", is("无主题")))
//                .andExpect(jsonPath("$[0].user.user_name", is("小钱")))
//                .andExpect(jsonPath("$[0].user.user_age", is(18)))
//                .andExpect(jsonPath("$[0].user.user_gender", is("female")))
//                .andExpect(jsonPath("$[0].user.user_email", is("twuc@thoughtworks.com")))
//                .andExpect(jsonPath("$[0].user.user_phone", is("11234567890")))
//                .andExpect(jsonPath("$[1].event", is("第二条事件")))
//                .andExpect(jsonPath("$[1].keywords", is("无主题")))
//                .andExpect(jsonPath("$[1].user.user_name", is("小李")))
//                .andExpect(jsonPath("$[1].user.user_age", is(30)))
//                .andExpect(jsonPath("$[1].user.user_gender", is("male")))
//                .andExpect(jsonPath("$[1].user.user_email", is("twuc@thoughtworks.com")))
//                .andExpect(jsonPath("$[1].user.user_phone", is("11234567890")))
//                .andExpect(jsonPath("$[2].event", is("第三条事件")))
//                .andExpect(jsonPath("$[2].keywords", is("无主题")))
//                .andExpect(jsonPath("$[2].user.user_name", is("小张")))
//                .andExpect(jsonPath("$[2].user.user_age", is(23)))
//                .andExpect(jsonPath("$[2].user.user_gender", is("male")))
//                .andExpect(jsonPath("$[2].user.user_email", is("twuc@thoughtworks.com")))
//                .andExpect(jsonPath("$[2].user.user_phone", is("11234567890")));
//
//
//        User user = new User("小张", 23, "male",
//                "twuc@thoughtworks.com", "11234567890",10);
//        Event events = new Event("添加一条热搜", "娱乐", 1);
//        ObjectMapper objectMapper = new ObjectMapper();
//        String json = objectMapper.writeValueAsString(events);
//        mockMvc.perform(post("/rs/eventAdd").content(json).contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().is(201))
//                .andExpect(header().string("index","3"));
//        mockMvc.perform(get("/rs/event"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(4)))
//                .andExpect(jsonPath("$[0].event", is("第一条事件")))
//                .andExpect(jsonPath("$[0].keywords", is("无主题")))
//                .andExpect(jsonPath("$[0].user.user_name", is("小钱")))
//                .andExpect(jsonPath("$[0].user.user_age", is(18)))
//                .andExpect(jsonPath("$[0].user.user_gender", is("female")))
//                .andExpect(jsonPath("$[0].user.user_email", is("twuc@thoughtworks.com")))
//                .andExpect(jsonPath("$[0].user.user_phone", is("11234567890")))
//                .andExpect(jsonPath("$[1].event", is("第二条事件")))
//                .andExpect(jsonPath("$[1].keywords", is("无主题")))
//                .andExpect(jsonPath("$[1].user.user_name", is("小李")))
//                .andExpect(jsonPath("$[1].user.user_age", is(30)))
//                .andExpect(jsonPath("$[1].user.user_gender", is("male")))
//                .andExpect(jsonPath("$[1].user.user_email", is("twuc@thoughtworks.com")))
//                .andExpect(jsonPath("$[1].user.user_phone", is("11234567890")))
//                .andExpect(jsonPath("$[2].event", is("第三条事件")))
//                .andExpect(jsonPath("$[2].keywords", is("无主题")))
//                .andExpect(jsonPath("$[2].user.user_name", is("小张")))
//                .andExpect(jsonPath("$[2].user.user_age", is(23)))
//                .andExpect(jsonPath("$[2].user.user_gender", is("male")))
//                .andExpect(jsonPath("$[2].user.user_email", is("twuc@thoughtworks.com")))
//                .andExpect(jsonPath("$[2].user.user_phone", is("11234567890")))
//                .andExpect(jsonPath("$[3].event", is("添加一条热搜")))
//                .andExpect(jsonPath("$[3].keywords", is("娱乐")))
//                .andExpect(jsonPath("$[3].user.user_name", is("小张")))
//                .andExpect(jsonPath("$[3].user.user_age", is(23)))
//                .andExpect(jsonPath("$[3].user.user_gender", is("male")))
//                .andExpect(jsonPath("$[3].user.user_email", is("twuc@thoughtworks.com")))
//                .andExpect(jsonPath("$[3].user.user_phone", is("11234567890")));
//
//
//        mockMvc.perform(get("/user/query"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(1)))
//                .andExpect(jsonPath("$[0].user_name", is("小张")))
//                .andExpect(jsonPath("$[0].user_age", is(23)))
//                .andExpect(jsonPath("$[0].user_gender", is("male")))
//                .andExpect(jsonPath("$[0].user_email", is("twuc@thoughtworks.com")))
//                .andExpect(jsonPath("$[0].user_phone", is("11234567890")));
//
//    }
//
//
//    @Test
//    void should_add_rs_event_when_user_is_not_exist() throws Exception {
//        mockMvc.perform(get("/rs/event"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(3)))
//                .andExpect(jsonPath("$[0].event", is("第一条事件")))
//                .andExpect(jsonPath("$[0].keywords", is("无主题")))
//                .andExpect(jsonPath("$[0].user.user_name", is("小钱")))
//                .andExpect(jsonPath("$[0].user.user_age", is(18)))
//                .andExpect(jsonPath("$[0].user.user_gender", is("female")))
//                .andExpect(jsonPath("$[0].user.user_email", is("twuc@thoughtworks.com")))
//                .andExpect(jsonPath("$[0].user.user_phone", is("11234567890")))
//                .andExpect(jsonPath("$[1].event", is("第二条事件")))
//                .andExpect(jsonPath("$[1].keywords", is("无主题")))
//                .andExpect(jsonPath("$[1].user.user_name", is("小李")))
//                .andExpect(jsonPath("$[1].user.user_age", is(30)))
//                .andExpect(jsonPath("$[1].user.user_gender", is("male")))
//                .andExpect(jsonPath("$[1].user.user_email", is("twuc@thoughtworks.com")))
//                .andExpect(jsonPath("$[1].user.user_phone", is("11234567890")))
//                .andExpect(jsonPath("$[2].event", is("第三条事件")))
//                .andExpect(jsonPath("$[2].keywords", is("无主题")))
//                .andExpect(jsonPath("$[2].user.user_name", is("小张")))
//                .andExpect(jsonPath("$[2].user.user_age", is(23)))
//                .andExpect(jsonPath("$[2].user.user_gender", is("male")))
//                .andExpect(jsonPath("$[2].user.user_email", is("twuc@thoughtworks.com")))
//                .andExpect(jsonPath("$[2].user.user_phone", is("11234567890")));
//
//
//        User user = new User("老李", 80, "male",
//                "twuc@thoughtworks.com", "11234567890",10);
//        Event events = new Event("添加一条热搜", "娱乐", 1);
//        ObjectMapper objectMapper = new ObjectMapper();
//        String EventJson = objectMapper.writeValueAsString(events);
//        String UserJson = objectMapper.writeValueAsString(user);
//        mockMvc.perform(post("/rs/eventAdd").content(EventJson).contentType(MediaType.APPLICATION_JSON))
//                .andExpect(header().string("index","3"))
//                .andExpect(status().is(201));
//        mockMvc.perform(get("/rs/event"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(4)))
//                .andExpect(jsonPath("$[0].event", is("第一条事件")))
//                .andExpect(jsonPath("$[0].keywords", is("无主题")))
//                .andExpect(jsonPath("$[0].user.user_name", is("小钱")))
//                .andExpect(jsonPath("$[0].user.user_age", is(18)))
//                .andExpect(jsonPath("$[0].user.user_gender", is("female")))
//                .andExpect(jsonPath("$[0].user.user_email", is("twuc@thoughtworks.com")))
//                .andExpect(jsonPath("$[0].user.user_phone", is("11234567890")))
//                .andExpect(jsonPath("$[1].event", is("第二条事件")))
//                .andExpect(jsonPath("$[1].keywords", is("无主题")))
//                .andExpect(jsonPath("$[1].user.user_name", is("小李")))
//                .andExpect(jsonPath("$[1].user.user_age", is(30)))
//                .andExpect(jsonPath("$[1].user.user_gender", is("male")))
//                .andExpect(jsonPath("$[1].user.user_email", is("twuc@thoughtworks.com")))
//                .andExpect(jsonPath("$[1].user.user_phone", is("11234567890")))
//                .andExpect(jsonPath("$[2].event", is("第三条事件")))
//                .andExpect(jsonPath("$[2].keywords", is("无主题")))
//                .andExpect(jsonPath("$[2].user.user_name", is("小张")))
//                .andExpect(jsonPath("$[2].user.user_age", is(23)))
//                .andExpect(jsonPath("$[2].user.user_gender", is("male")))
//                .andExpect(jsonPath("$[2].user.user_email", is("twuc@thoughtworks.com")))
//                .andExpect(jsonPath("$[2].user.user_phone", is("11234567890")))
//                .andExpect(jsonPath("$[3].event", is("添加一条热搜")))
//                .andExpect(jsonPath("$[3].keywords", is("娱乐")))
//                .andExpect(jsonPath("$[3].user.user_name", is("老李")))
//                .andExpect(jsonPath("$[3].user.user_age", is(80)))
//                .andExpect(jsonPath("$[3].user.user_gender", is("male")))
//                .andExpect(jsonPath("$[3].user.user_email", is("twuc@thoughtworks.com")))
//                .andExpect(jsonPath("$[3].user.user_phone", is("11234567890")));
//
//        mockMvc.perform(get("/user/query"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(1)))
//                .andExpect(jsonPath("$[0].user_name", is("小张")))
//                .andExpect(jsonPath("$[0].user_age", is(23)))
//                .andExpect(jsonPath("$[0].user_gender", is("male")))
//                .andExpect(jsonPath("$[0].user_email", is("twuc@thoughtworks.com")))
//                .andExpect(jsonPath("$[0].user_phone", is("11234567890")));
//        mockMvc.perform(post("/user/register")
//                .content(UserJson)
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//        mockMvc.perform(get("/user/query"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(2)))
//                .andExpect(jsonPath("$[0].user_name", is("小张")))
//                .andExpect(jsonPath("$[0].user_age", is(23)))
//                .andExpect(jsonPath("$[0].user_gender", is("male")))
//                .andExpect(jsonPath("$[0].user_email", is("twuc@thoughtworks.com")))
//                .andExpect(jsonPath("$[0].user_phone", is("11234567890")))
//                .andExpect(jsonPath("$[1].user_name", is("老李")))
//                .andExpect(jsonPath("$[1].user_age", is(80)))
//                .andExpect(jsonPath("$[1].user_gender", is("male")))
//                .andExpect(jsonPath("$[1].user_email", is("twuc@thoughtworks.com")))
//                .andExpect(jsonPath("$[1].user_phone", is("11234567890")));
//
//
//    }


    @Test
    void should_add_rs_event_has_user() throws Exception {

        mockMvc.perform(get("/rs/event"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        Event events = new Event("添加一条热搜", "娱乐", 1);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(events);
        mockMvc.perform(post("/rs/eventAdd").content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(get("/rs/event"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].event", is("添加一条热搜")))
                .andExpect(jsonPath("$[0].keywords", is("娱乐")))
                .andExpect(jsonPath("$[0].userId", is(1)));
    }

    @Test
    void should_add_rs_event_without_user() throws Exception {

        mockMvc.perform(get("/rs/event"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        Event events = new Event("添加一条热搜", "娱乐", 2);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(events);
        mockMvc.perform(post("/rs/eventAdd").content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());



    }




//    @Test
//    void should_modify_rs_eventname() throws Exception {
//        mockMvc.perform(get("/rs/event"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(3)));
//        Event events = new Event("比特币", null, null);
//        ObjectMapper objectMapper = new ObjectMapper();
//        String json = objectMapper.writeValueAsString(events);
//        mockMvc.perform(put("/rs/eventModify/1").content(json).contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//        mockMvc.perform(get("/rs/event"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].event", is("比特币")))
//                .andExpect(jsonPath("$[0].keywords", is("无主题")))
//                .andExpect(jsonPath("$[1].event", is("第二条事件")))
//                .andExpect(jsonPath("$[1].keywords", is("无主题")))
//                .andExpect(jsonPath("$[2].event", is("第三条事件")))
//                .andExpect(jsonPath("$[2].keywords", is("无主题")));
//
//    }
//
//    @Test
//    void should_modify_rs_keywords() throws Exception {
//        mockMvc.perform(get("/rs/event"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(3)));
//        Event events = new Event(null, "娱乐", null);
//        ObjectMapper objectMapper = new ObjectMapper();
//        String json = objectMapper.writeValueAsString(events);
//        mockMvc.perform(put("/rs/eventModify/2").content(json).contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//        mockMvc.perform(get("/rs/event"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].event", is("第一条事件")))
//                .andExpect(jsonPath("$[0].keywords", is("无主题")))
//                .andExpect(jsonPath("$[1].event", is("第二条事件")))
//                .andExpect(jsonPath("$[1].keywords", is("娱乐")))
//                .andExpect(jsonPath("$[2].event", is("第三条事件")))
//                .andExpect(jsonPath("$[2].keywords", is("无主题")));
//
//    }
//
//    @Test
//    void should_modify_rs_Event() throws Exception {
//        mockMvc.perform(get("/rs/event"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(3)));
//        Event events = new Event("世界杯", "体育", null);
//        ObjectMapper objectMapper = new ObjectMapper();
//        String json = objectMapper.writeValueAsString(events);
//        mockMvc.perform(put("/rs/eventModify/3").content(json).contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//        mockMvc.perform(get("/rs/event"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(3)))
//                .andExpect(jsonPath("$[0].event", is("第一条事件")))
//                .andExpect(jsonPath("$[0].keywords", is("无主题")))
//                .andExpect(jsonPath("$[1].event", is("第二条事件")))
//                .andExpect(jsonPath("$[1].keywords", is("无主题")))
//                .andExpect(jsonPath("$[2].event", is("世界杯")))
//                .andExpect(jsonPath("$[2].keywords", is("体育")));
//
//    }
//
//    @Test
//    void should_delete_one_rs_event() throws Exception {
//        mockMvc.perform(get("/rs/event"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(3)));
//        mockMvc.perform(delete("/rs/eventDelete/1"))
//                .andExpect(status().isOk());
//        mockMvc.perform(get("/rs/event"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(2)))
//                .andExpect(jsonPath("$[0].event", is("第二条事件")))
//                .andExpect(jsonPath("$[0].keywords", is("无主题")))
//                .andExpect(jsonPath("$[1].event", is("第三条事件")))
//                .andExpect(jsonPath("$[1].keywords", is("无主题")));
//
//    }


}
