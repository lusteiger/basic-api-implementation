package com.thoughtworks.rslist.dto;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Events {

    private String event;
    private String keywords;
    private User user;



}
