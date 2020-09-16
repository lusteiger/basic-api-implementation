package com.thoughtworks.rslist.dto;

import lombok.*;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class User {
    @NotEmpty
    @Size(max = 8)
    private String userName;
    @Max(100)
    @Min(18)
    @NotNull
    private int age;
    @NotEmpty
    private String gender;
    @Email
    private String email;
    private String phone;
}
