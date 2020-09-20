package com.thoughtworks.rslist.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.org.glassfish.gmbal.NameValue;
import lombok.*;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "user")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user")
    private String userName;

    private int age;
    private String gender;
    private String email;
    private String phone;
    private int voteNum;


    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "user")
    private List<EventEntity> eventEntities;
}
