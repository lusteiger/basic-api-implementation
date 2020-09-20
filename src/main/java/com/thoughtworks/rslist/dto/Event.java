package com.thoughtworks.rslist.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.entity.EventEntity;
import com.thoughtworks.rslist.entity.UserEntity;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Event {

    private int id;
    @NotEmpty
    private String event;

    @NotEmpty
    private String keywords;

    @NotNull
    private Integer userId;

    private User user;

    private int voteNum;

    public static Event from(EventEntity eventEntity) {
        return Event.builder()
                .id(eventEntity.getId())
                .event(eventEntity.getEvent())
                .keywords(eventEntity.getKeywords())
                .userId(eventEntity.getUser().getId())
                .voteNum(eventEntity.getVoteNum())
                .build();
    }
}
