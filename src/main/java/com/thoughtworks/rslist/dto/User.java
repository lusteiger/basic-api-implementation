package com.thoughtworks.rslist.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class User {
    private String userName;
    private  int age;
    private String gender;
    private String email;
    private String phone;
}
