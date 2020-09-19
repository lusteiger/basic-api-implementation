package com.thoughtworks.rslist.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.dto.User;
import com.thoughtworks.rslist.entity.EventEntity;
import com.thoughtworks.rslist.entity.UserEntity;
import com.thoughtworks.rslist.repository.EventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import org.assertj.core.condition.DoesNotHave;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class UserControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserRepository userRepository;

    @Autowired
    EventRepository eventRepository;

    @BeforeEach
    void setUp() {
        ObjectMapper objectMapper = new ObjectMapper();
        userRepository.deleteAll();
        userRepository.deleteAll();
//        eventRepository.save(getMockeventEntity());
    }


    private User getMockUser() {
        User user = new User("小王", 18, "female",
                "twu@tw.com", "18812345678");
        return user;
    }

//    private UserEntity getMockUserEntity() {
//        UserEntity userEntity = new UserEntity(1, "小王", 18, "female",
//                "twu@tw.com", "18812345678", 10);
//        return userEntity;
//    }

//    private EventEntity getMockeventEntity(){
//        EventEntity eventEntity = EventEntity.builder()
//                .event("哈哈")
//                .keywords("娱乐")
//                .userId(1)
//                .build();
//        return eventEntity;
//    }


    @Test
    void should_register_user() throws Exception {

        User user = getMockUser();
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(user);

        mockMvc.perform(get("/user/query"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        mockMvc.perform(post("/user/register")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/user/query"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].userName", is("小王")));
        assertEquals("小王", user.getUserName());
        assertEquals(18, user.getAge());
        assertEquals("female", user.getGender());
        assertEquals("twu@tw.com", user.getEmail());
        assertEquals("18812345678", user.getPhone());
        assertEquals(10, user.getVoteNum());

    }


    @Test
    void should_invalid_when_user_name_length_more_then_8() throws Exception {

        User user = new User(1,"123456789", 18, "female", "twu@tw.com", "18812345678", 10);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(user);

        mockMvc.perform(get("/user/query"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
        mockMvc.perform(post("/user/register")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    void should_invalid_when_user_name_is_empty() throws Exception {

        User user = new User(1,"", 18, "female", "twu@tw.com", "18812345678", 10);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(user);

        mockMvc.perform(get("/user/query"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
        mockMvc.perform(post("/user/register")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }


    @Test
    void should_invalid_when_user_gender_is_empty() throws Exception {

        User user = new User(1,"asdasd", 18, "", "twu@tw.com", "18812345678", 10);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(user);

        mockMvc.perform(get("/user/query"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
        mockMvc.perform(post("/user/register")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }


    @Test
    void should_invalid_when_user_age_is_more_then_100() throws Exception {

        User user = new User(1,"asdasd", 101, "female", "twu@tw.com", "18812345678", 10);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(user);

        mockMvc.perform(get("/user/query"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
        mockMvc.perform(post("/user/register")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    void should_invalid_when_user_age_is_less_then_18() throws Exception {

        User user = new User(1,"asdasd", 17, "female", "twu@tw.com", "18812345678", 10);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(user);

        mockMvc.perform(get("/user/query"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
        mockMvc.perform(post("/user/register")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    void should_invalid_when_user_email_is_not_valid() throws Exception {

        User user = new User(1,"asdasd", 18, "female", "twutw.com", "18812345678", 10);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(user);

        mockMvc.perform(get("/user/query"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
        mockMvc.perform(post("/user/register")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    void should_invalid_when_user_phone_is_more_then_11() throws Exception {

        User user = new User(1,"asdasd", 18, "female", "twutw.com", "188123456781", 10);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(user);

        mockMvc.perform(get("/user/query"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
        mockMvc.perform(post("/user/register")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void should_invalid_when_user_phone_is_less_then_11() throws Exception {

        User user = new User(1,"asdasd", 18, "female", "twutw.com", "2", 10);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(user);

        mockMvc.perform(get("/user/query"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
        mockMvc.perform(post("/user/register")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void should_invalid_when_user_phone_start_1() throws Exception {

        User user = new User(1,"asdasd", 18, "female", "twutw.com", "88812345678", 10);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(user);

        mockMvc.perform(get("/user/query"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
        mockMvc.perform(post("/user/register")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void should_return_list_using_jsonProperty() throws Exception {


        mockMvc.perform(get("/user/query"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));


    }

//    @Test
//    void should_return_user_entity_when_query_with_index() throws Exception {
//        User user = getMockUser();
//        UserEntity userEntity = getMockUserEntity();
//        ObjectMapper objectMapper = new ObjectMapper();
//        String json = objectMapper.writeValueAsString(user);
//        mockMvc.perform(post("/user/register")
//                .content(json)
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isCreated());
//        mockMvc.perform(get("/user/query/1"))
//                .andExpect(status().isOk());
//        assertEquals(1, userEntity.getId());
//        assertEquals("小王", userEntity.getUserName());
//        assertEquals(18, userEntity.getAge());
//        assertEquals("female", userEntity.getGender());
//        assertEquals("twu@tw.com", userEntity.getEmail());
//        assertEquals("18812345678", userEntity.getPhone());
//        assertEquals(10, userEntity.getVoteNum());

//    }

    @Test
    void should_not_return_when_delete_with_index() throws Exception {
        UserEntity userEntity =UserEntity.builder()
                .userName("小王")
                .age(18)
                .gender("female")
                .email("twu@tw.com")
                .phone("18812345678")
                .voteNum(10)
                .build();
        EventEntity eventEntity1 = EventEntity.builder()
                .event("哈哈")
                .keywords("娱乐")
                .user(userEntity)
                .build();
        EventEntity eventEntity2 = EventEntity.builder()
                .event("ss")
                .keywords("娱乐")
                .user(userEntity)
                .build();
        userRepository.save(userEntity);
        eventRepository.save(eventEntity1);
        eventRepository.save(eventEntity2);
        mockMvc.perform(get("/user/query"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
        mockMvc.perform(get("/rs/event"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
        mockMvc.perform((delete("/user/delete/1")))
                .andExpect(status().isOk());
        mockMvc.perform((get("/user/query")))
                .andExpect(jsonPath("$", hasSize(0)))
                .andExpect(status().isOk());
        mockMvc.perform((get("/rs/event")))
                .andExpect(jsonPath("$", hasSize(0)))
                .andExpect(status().isOk());


    }

}