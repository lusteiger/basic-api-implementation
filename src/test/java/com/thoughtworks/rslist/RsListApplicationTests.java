package com.thoughtworks.rslist;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.dto.Events;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class RsListApplicationTests {
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
    void should_add_rs_event() throws Exception {
        mockMvc.perform(get("/rs/event"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));

        Events events = new Events("猪肉涨价了", "经济");
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(events);
        mockMvc.perform(post("/rs/event").content(json).contentType(MediaType.APPLICATION_JSON))
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
                .andExpect(jsonPath("$[3].event", is("猪肉涨价了")))
                .andExpect(jsonPath("$[3].keywords", is("经济")));

    }

    @Test
    void should_modify_rs_event() throws Exception {
        mockMvc.perform(get("/rs/event"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }

}
