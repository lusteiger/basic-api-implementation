package com.thoughtworks.rslist.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("user_name")
    private String userName;
    @Max(100)
    @Min(18)
    @NotNull
    @JsonProperty("user_age")
    private int age;
    @NotEmpty
    @JsonProperty("user_gender")
    private String gender;
    @Email
    @JsonProperty("user_email")
    private String email;
    @JsonProperty("user_phone")
    @Pattern(regexp = "^1\\d{10}$")
    private String phone;
}
