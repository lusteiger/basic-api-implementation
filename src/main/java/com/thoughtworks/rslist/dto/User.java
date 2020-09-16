package com.thoughtworks.rslist.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class User {
    @NotEmpty
    @Size(max = 8)
    private String userName;
    private int age;
    private String gender;
    private String email;
    private String phone;
}
