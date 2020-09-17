package com.thoughtworks.rslist.exceptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.dto.Events;
import com.thoughtworks.rslist.dto.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class CommonExceptionTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    void should_return_exception_start_and_end_out_of_index() throws Exception {
        mockMvc.perform(get("/rs/event?start=1&end=10"))
                .andExpect(status().is(400))
                .andExpect(jsonPath("error", is("invalid request param")));
    }

    @Test
    void should_return_exception_user_of_event() throws Exception {
        User user = new User("小张", 200, "male",
                "twuc@thoughtworks.com", "11234567890");
        Events events = new Events("添加一条热搜", "娱乐", user);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(events);
        mockMvc.perform(post("/rs/eventAdd").content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400))
                .andExpect(jsonPath("error", is("invalid request param")));
    }

    @Test
    void should_return_exception_event() throws Exception {
        User user = new User("小张", 22, "male",
                "twuc@thoughtworks.com", "11234567890");
        Events events = new Events("", "娱乐", user);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(events);
        mockMvc.perform(post("/rs/eventAdd").content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400))
                .andExpect(jsonPath("error", is("invalid request param")));
    }

    @Test
    void should_return_exception_user() throws Exception {
        User user = new User("小张", 22, "male",
                "ghtworks.com", "11234567890");

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/user/register").content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400))
                .andExpect(jsonPath("error", is("invalid request param")));
    }
}