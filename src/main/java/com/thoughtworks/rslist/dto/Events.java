package com.thoughtworks.rslist.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;

import javax.validation.Valid;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Events {

    public interface WithoutUser{};
    public interface WithUser extends WithoutUser{};
    @JsonView(Events.WithoutUser.class)
    private String event;
    @JsonView(Events.WithoutUser.class)
    private String keywords;
    @Valid
    @JsonView(WithUser.class)
    private User user;


    public String toJsonWith() throws JsonProcessingException{
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writerWithView(WithUser.class)
                .writeValueAsString(this);
    }



}
