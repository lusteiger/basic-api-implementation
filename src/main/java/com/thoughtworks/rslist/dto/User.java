package com.thoughtworks.rslist.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.thoughtworks.rslist.entity.UserEntity;
import lombok.*;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @JsonProperty("user_id")
    private int  id;
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
    @JsonProperty("user_voteNum")
    private int voteNum = 10;

    public User(@NotEmpty @Size(max = 8) String userName, @Max(100) @Min(18) @NotNull int age, @NotEmpty String gender, @Email String email, @Pattern(regexp = "^1\\d{10}$") String phone) {
        this.userName = userName;
        this.age = age;
        this.gender = gender;
        this.email = email;
        this.phone = phone;
    }




    public static User from(UserEntity userEntity) {
        return User.builder()
                .id(userEntity.getId())
                .userName(userEntity.getUserName())
                .gender(userEntity.getGender())
                .email(userEntity.getEmail())
                .phone(userEntity.getPhone())
                .age(userEntity.getAge())
                .build();
    }
}
