package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.dto.Vote;
import com.thoughtworks.rslist.entity.VoteEntity;
import com.thoughtworks.rslist.repository.VoteRepository;
import com.thoughtworks.rslist.service.VoteService;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class VoteController {

    @Autowired
    VoteService voteService;

    @PostMapping("/rs/vote/{rsEventId}")
    private ResponseEntity VoteEvent(@RequestBody Vote vote, @PathVariable int rsEventId) {

        VoteEntity voteEntity = VoteEntity.builder()
                .voteNum(vote.getVoteNum())
                .voteTime(vote.getVoteTime())
                .userId(vote.getUserId())
                .build();

        ResponseEntity responseEntity = voteService.saveVote(voteEntity, rsEventId);

        return ResponseEntity.ok().body(responseEntity);

    }

    @GetMapping("/rs/vote/list")
    public ResponseEntity<List<Vote>> VoteList() {

        List<Vote> voteList = voteService.voteList();

        return ResponseEntity.ok().body(voteList);

    }
}