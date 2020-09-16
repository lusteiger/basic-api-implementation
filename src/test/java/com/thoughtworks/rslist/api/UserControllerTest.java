package com.thoughtworks.rslist.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.dto.User;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class UserControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    void should_register_user() throws Exception {

        User user = new User("小王", 18, "female", "twu@tw.com", "18812345678");
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(user);

        mockMvc.perform(get("/user/query"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].userName", is("小张")))
                .andExpect(jsonPath("$[0].age", is(23)))
                .andExpect(jsonPath("$[0].gender", is("male")))
                .andExpect(jsonPath("$[0].email", is("twuc@thoughtworks.com")))
                .andExpect(jsonPath("$[0].phone", is("11234567890")));
        mockMvc.perform(post("/user/register")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(get("/user/query"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].userName", is("小张")))
                .andExpect(jsonPath("$[0].age", is(23)))
                .andExpect(jsonPath("$[0].gender", is("male")))
                .andExpect(jsonPath("$[0].email", is("twuc@thoughtworks.com")))
                .andExpect(jsonPath("$[0].phone", is("11234567890")))
                .andExpect(jsonPath("$[1].userName", is("小王")))
                .andExpect(jsonPath("$[1].age", is(18)))
                .andExpect(jsonPath("$[1].gender", is("female")))
                .andExpect(jsonPath("$[1].email", is("twu@tw.com")))
                .andExpect(jsonPath("$[1].phone", is("18812345678")));


    }


    @Test
    void should_invalid_when_username_length_more_then_8() throws Exception {

        User user = new User("123456789", 18, "female", "twu@tw.com", "18812345678");
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(user);

        mockMvc.perform(get("/user/query"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].userName", is("小张")))
                .andExpect(jsonPath("$[0].age", is(23)))
                .andExpect(jsonPath("$[0].gender", is("male")))
                .andExpect(jsonPath("$[0].email", is("twuc@thoughtworks.com")))
                .andExpect(jsonPath("$[0].phone", is("11234567890")));
        mockMvc.perform(post("/user/register")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    void should_invalid_when_username_is_empty() throws Exception {

        User user = new User("", 18, "female", "twu@tw.com", "18812345678");
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(user);

        mockMvc.perform(get("/user/query"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].userName", is("小张")))
                .andExpect(jsonPath("$[0].age", is(23)))
                .andExpect(jsonPath("$[0].gender", is("male")))
                .andExpect(jsonPath("$[0].email", is("twuc@thoughtworks.com")))
                .andExpect(jsonPath("$[0].phone", is("11234567890")));
        mockMvc.perform(post("/user/register")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }


    @Test
    void should_invalid_when_gender_is_empty() throws Exception {

        User user = new User("asdasd", 18, "", "twu@tw.com", "18812345678");
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(user);

        mockMvc.perform(get("/user/query"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].userName", is("小张")))
                .andExpect(jsonPath("$[0].age", is(23)))
                .andExpect(jsonPath("$[0].gender", is("male")))
                .andExpect(jsonPath("$[0].email", is("twuc@thoughtworks.com")))
                .andExpect(jsonPath("$[0].phone", is("11234567890")));
        mockMvc.perform(post("/user/register")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }


    @Test
    void should_invalid_when_age_is_more_then_100() throws Exception {

        User user = new User("asdasd",101 , "female", "twu@tw.com", "18812345678");
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(user);

        mockMvc.perform(get("/user/query"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].userName", is("小张")))
                .andExpect(jsonPath("$[0].age", is(23)))
                .andExpect(jsonPath("$[0].gender", is("male")))
                .andExpect(jsonPath("$[0].email", is("twuc@thoughtworks.com")))
                .andExpect(jsonPath("$[0].phone", is("11234567890")));
        mockMvc.perform(post("/user/register")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    void should_invalid_when_age_is_less_then_18() throws Exception {

        User user = new User("asdasd",17 , "female", "twu@tw.com", "18812345678");
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(user);

        mockMvc.perform(get("/user/query"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].userName", is("小张")))
                .andExpect(jsonPath("$[0].age", is(23)))
                .andExpect(jsonPath("$[0].gender", is("male")))
                .andExpect(jsonPath("$[0].email", is("twuc@thoughtworks.com")))
                .andExpect(jsonPath("$[0].phone", is("11234567890")));
        mockMvc.perform(post("/user/register")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }


}