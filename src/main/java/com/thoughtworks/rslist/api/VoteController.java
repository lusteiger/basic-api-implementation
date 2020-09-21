package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.dto.Vote;
import com.thoughtworks.rslist.entity.VoteEntity;
import com.thoughtworks.rslist.repository.VoteRepository;
import com.thoughtworks.rslist.service.VoteService;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@ResponseBody
public class VoteController {

    @Autowired
    VoteService voteService;

    @PostMapping("/rs/{rsEventId}/vote")
    private ResponseEntity VoteEvent(@RequestBody Vote vote, @PathVariable int rsEventId) {

        VoteEntity voteEntity = VoteEntity.builder()
                .voteNum(vote.getVoteNum())
                .voteTime(vote.getVoteTime())
                .userId(vote.getUserId())
                .build();

        ResponseEntity responseEntity = voteService.saveVote(voteEntity, rsEventId);

        return responseEntity;

    }

    @PostMapping("/rs/vote/range")
    public ResponseEntity<List<Vote>> VoteRangeList(@RequestParam(required = false) String start,
                                                    @RequestParam(required = false) String end) {


        if (start.isEmpty() || end.isEmpty())
            return voteService.voteList();

        LocalTime startTime = LocalTime.parse(start, DateTimeFormatter.ofPattern("HH:mm:ss"));
        LocalTime endTime = LocalTime.parse(end, DateTimeFormatter.ofPattern("HH:mm:ss"));

        List<Vote> voteList = voteService.QueryVoteNumRange(startTime, endTime).stream()
                .map(Vote::from).collect(Collectors.toList());

        return ResponseEntity.ok().body(voteList);

    }

    @GetMapping("/rs/vote/list")
    public ResponseEntity<List<Vote>> VoteList() {


        return voteService.voteList();
    }


}