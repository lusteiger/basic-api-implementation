package com.thoughtworks.rslist.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.dto.Events;
import com.thoughtworks.rslist.dto.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

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

    @Test
    void should_get_one_rs_event() throws Exception {
        mockMvc.perform(get("/rs/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("第一条事件"));
    }

    @Test
    void should_get_start_until_end_rs_event() throws Exception {
        mockMvc.perform(get("/rs/event?start=1&end=3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].event", is("第一条事件")))
                .andExpect(jsonPath("$[0].keywords", is("无主题")))
                .andExpect(jsonPath("$[1].event", is("第二条事件")))
                .andExpect(jsonPath("$[1].keywords", is("无主题")))
                .andExpect(jsonPath("$[2].event", is("第三条事件")))
                .andExpect(jsonPath("$[2].keywords", is("无主题")));
    }

    @Test
    void should_get_all_rs_event() throws Exception {
        mockMvc.perform(get("/rs/event"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].event", is("第一条事件")))
                .andExpect(jsonPath("$[0].keywords", is("无主题")))
                .andExpect(jsonPath("$[1].event", is("第二条事件")))
                .andExpect(jsonPath("$[1].keywords", is("无主题")))
                .andExpect(jsonPath("$[2].event", is("第三条事件")))
                .andExpect(jsonPath("$[2].keywords", is("无主题")));
    }

    @Test
    void should_get_rs_list() throws Exception {
        mockMvc.perform(get("/rs/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].event", is("第一条事件")))
                .andExpect(jsonPath("$[0].keywords", is("无主题")))
                .andExpect(jsonPath("$[1].event", is("第二条事件")))
                .andExpect(jsonPath("$[1].keywords", is("无主题")))
                .andExpect(jsonPath("$[2].event", is("第三条事件")))
                .andExpect(jsonPath("$[2].keywords", is("无主题")));
    }

    @Test
    void should_add_rs_event() throws Exception {
        mockMvc.perform(get("/rs/event"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));

        User user = new User("xiaowang",19,"female","a@thoughtworks.com","18888888888");
        Events events = new Events("添加一条热搜", "娱乐",user);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(events);
        mockMvc.perform(post("/rs/eventAdd").content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(get("/rs/event"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].event", is("第一条事件")))
                .andExpect(jsonPath("$[0].keywords", is("无主题")))
                .andExpect(jsonPath("$[1].event", is("第二条事件")))
                .andExpect(jsonPath("$[1].keywords", is("无主题")))
                .andExpect(jsonPath("$[2].event", is("第三条事件")))
                .andExpect(jsonPath("$[2].keywords", is("无主题")))
                .andExpect(jsonPath("$[3].event", is("添加一条热搜")))
                .andExpect(jsonPath("$[3].keywords", is("娱乐")))
                .andExpect(jsonPath("$[3].user.userName",is("xiaowang")))
                .andExpect(jsonPath("$[3].user.age",is(19)))
                .andExpect(jsonPath("$[3].user.gender",is("female")))
                .andExpect(jsonPath("$[3].user.email",is("a@thoughtworks.com")))
                .andExpect(jsonPath("$[3].user.phone",is("18888888888")));

    }

    @Test
    void should_modify_rs_eventname() throws Exception {
        mockMvc.perform(get("/rs/event"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
        Events events = new Events("比特币", null,null);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(events);
        mockMvc.perform(put("/rs/eventModify/1").content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(get("/rs/event"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].event", is("比特币")))
                .andExpect(jsonPath("$[0].keywords", is("无主题")))
                .andExpect(jsonPath("$[1].event", is("第二条事件")))
                .andExpect(jsonPath("$[1].keywords", is("无主题")))
                .andExpect(jsonPath("$[2].event", is("第三条事件")))
                .andExpect(jsonPath("$[2].keywords", is("无主题")));

    }

    @Test
    void should_modify_rs_keywords() throws Exception {
        mockMvc.perform(get("/rs/event"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
        Events events = new Events(null, "娱乐",null);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(events);
        mockMvc.perform(put("/rs/eventModify/2").content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(get("/rs/event"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].event", is("第一条事件")))
                .andExpect(jsonPath("$[0].keywords", is("无主题")))
                .andExpect(jsonPath("$[1].event", is("第二条事件")))
                .andExpect(jsonPath("$[1].keywords", is("娱乐")))
                .andExpect(jsonPath("$[2].event", is("第三条事件")))
                .andExpect(jsonPath("$[2].keywords", is("无主题")));

    }

    @Test
    void should_modify_rs_Event() throws Exception {
        mockMvc.perform(get("/rs/event"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
        Events events = new Events("世界杯", "体育",null);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(events);
        mockMvc.perform(put("/rs/eventModify/3").content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(get("/rs/event"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].event", is("第一条事件")))
                .andExpect(jsonPath("$[0].keywords", is("无主题")))
                .andExpect(jsonPath("$[1].event", is("第二条事件")))
                .andExpect(jsonPath("$[1].keywords", is("无主题")))
                .andExpect(jsonPath("$[2].event", is("世界杯")))
                .andExpect(jsonPath("$[2].keywords", is("体育")));

    }

    @Test
    void should_delete_one_rs_event() throws Exception {
        mockMvc.perform(get("/rs/event"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
        mockMvc.perform(delete("/rs/eventDelete/1"))
                .andExpect(status().isOk());
        mockMvc.perform(get("/rs/event"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].event", is("第二条事件")))
                .andExpect(jsonPath("$[0].keywords", is("无主题")))
                .andExpect(jsonPath("$[1].event", is("第三条事件")))
                .andExpect(jsonPath("$[1].keywords", is("无主题")));

    }


}
