package com.thoughtworks.rslist.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.dto.Vote;
import com.thoughtworks.rslist.entity.EventEntity;
import com.thoughtworks.rslist.entity.UserEntity;
import com.thoughtworks.rslist.entity.VoteEntity;
import com.thoughtworks.rslist.repository.EventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import com.thoughtworks.rslist.repository.VoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class VoteControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    UserRepository userRepository;
    @Autowired
    EventRepository eventRepository;
    @Autowired
    VoteRepository voteRepository;

    @BeforeEach
    void setUp() {
        eventRepository.deleteAll();
        userRepository.deleteAll();
        voteRepository.deleteAll();
    }

    @Test
    void should_vote_has_associate_user() throws Exception {
        UserEntity userEntity = UserEntity.builder()
                .userName("小王")
                .age(18)
                .gender("female")
                .email("twu@tw.com")
                .phone("18812345678")
                .voteNum(10)
                .build();

        userRepository.save(userEntity);

        EventEntity eventEntity = EventEntity.builder()
                .event("哈哈")
                .keywords("娱乐")
                .user(userEntity)
                .build();
        eventRepository.save(eventEntity);
        Vote vote = Vote.builder()
                .voteNum(5)
                .userId(1)
                .voteTime(LocalTime.now())
                .build();

        ObjectMapper objectMapper = new ObjectMapper();

        String json = objectMapper.writeValueAsString(vote);


        mockMvc.perform(get("/user/query"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));

        mockMvc.perform(get("/rs/events"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
        mockMvc.perform(post("/rs/{rsEventId}/vote", eventEntity.getId())
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(get("/rs/vote/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("[0].voteNum", is(vote.getVoteNum())))
                .andExpect(jsonPath("[0].voteTime", is(vote.getVoteTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")))));

    }

    @Test
    void should_valid_vote_number_more_than_users() throws Exception {
        UserEntity userEntity = UserEntity.builder()
                .userName("小王")
                .age(18)
                .gender("female")
                .email("twu@tw.com")
                .phone("18812345678")
                .voteNum(10)
                .build();

        userRepository.save(userEntity);

        EventEntity eventEntity = EventEntity.builder()
                .event("哈哈")
                .keywords("娱乐")
                .user(userEntity)
                .build();
        eventRepository.save(eventEntity);
        Vote vote = Vote.builder()
                .voteNum(10)
                .userId(1)
                .voteTime(LocalTime.now())
                .build();

        ObjectMapper objectMapper = new ObjectMapper();

        String json = objectMapper.writeValueAsString(vote);


        mockMvc.perform(get("/user/query"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));

        mockMvc.perform(get("/rs/events"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
        mockMvc.perform(post("/rs/{rsEventId}/vote", eventEntity.getId())
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    void should_return_all_vote_record_between_start_and_end() throws Exception {
        UserEntity userEntity = UserEntity.builder()
                .userName("小王")
                .age(18)
                .gender("female")
                .email("twu@tw.com")
                .phone("18812345678")
                .voteNum(10)
                .build();

        userRepository.save(userEntity);

        EventEntity eventEntity = EventEntity.builder()
                .event("哈哈")
                .keywords("娱乐")
                .user(userEntity)
                .build();
        eventRepository.save(eventEntity);
        VoteEntity vote1 = VoteEntity.builder()
                .voteNum(1)
                .userId(1)
                .voteTime(LocalTime.now())
                .build();
        VoteEntity vote2 = VoteEntity.builder()
                .voteNum(2)
                .userId(1)
                .voteTime(LocalTime.now())
                .build();
        VoteEntity vote3 = VoteEntity.builder()
                .voteNum(3)
                .userId(1)
                .voteTime(LocalTime.now())
                .build();
        voteRepository.save(vote1);
        voteRepository.save(vote2);
        voteRepository.save(vote3);

        mockMvc.perform(post("/rs/vote/range/?start=00:00:00&end=23:59:59"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(3)))
                .andExpect(jsonPath("[0].voteNum",is(1)))
                .andExpect(jsonPath("[1].voteNum",is(2)))
                .andExpect(jsonPath("[2].voteNum",is(3)));
    }

}