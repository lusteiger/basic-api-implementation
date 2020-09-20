package com.thoughtworks.rslist.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.thoughtworks.rslist.entity.UserEntity;
import com.thoughtworks.rslist.entity.VoteEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Vote {
   private int id;
   private int voteNum;
   private int userId;
   @JsonSerialize(using = LocalTimeSerializer.class)
   private LocalTime voteTime;


   public static Vote from(VoteEntity voteEntity) {
      return Vote.builder()
              .id(voteEntity.getId())
               .userId(voteEntity.getUserId())
              .voteNum(voteEntity.getVoteNum())
              .voteTime(voteEntity.getVoteTime())
              .build();
   }

}
