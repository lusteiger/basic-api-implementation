package com.thoughtworks.rslist.service;

import com.thoughtworks.rslist.dto.Vote;
import com.thoughtworks.rslist.entity.EventEntity;
import com.thoughtworks.rslist.entity.UserEntity;
import com.thoughtworks.rslist.entity.VoteEntity;
import com.thoughtworks.rslist.repository.EventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import com.thoughtworks.rslist.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class VoteService {

    @Autowired
    VoteRepository voteRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    EventRepository eventRepository;

    public ResponseEntity<List<Vote>> voteList(){
        List<Vote> voteList = voteRepository.findAll().stream().map(Vote::from).collect(Collectors.toList());

        return ResponseEntity.ok().body(voteList);
    }



    public ResponseEntity<Vote> saveVote(VoteEntity voteEntity, int rsEventId){

        Optional<UserEntity> userEntity = userRepository.findById(voteEntity.getUserId());
        Optional<EventEntity> eventEntity = eventRepository.findById(rsEventId);
        if (!userEntity.isPresent()||!eventEntity.isPresent())
            return ResponseEntity.status(400).build();

        EventEntity event = eventEntity.get();
        UserEntity user = userEntity.get();
        if (user.getVoteNum()>voteEntity.getVoteNum()){
            user.setVoteNum(user.getVoteNum()-voteEntity.getVoteNum());
            event.setVoteNum(event.getVoteNum()+voteEntity.getVoteNum());
        }
        else
            return ResponseEntity.status(400).build();



        VoteEntity vote = voteRepository.save(voteEntity);



        Vote result = Vote.builder()
                .id(vote.getId())
                .userId(vote.getUserId())
                .voteNum(vote.getVoteNum())
                .voteTime(vote.getVoteTime())
                .build();
        return ResponseEntity.ok().body(result);
    }

    public List<VoteEntity> QueryVoteNumRange(LocalTime start, LocalTime end){
       List<VoteEntity> voteEntityList = voteRepository.findAllByVoteTimeBetween(start, end);

       return voteEntityList;
    }

}
