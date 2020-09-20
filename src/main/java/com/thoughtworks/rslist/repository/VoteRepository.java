package com.thoughtworks.rslist.repository;

import com.thoughtworks.rslist.entity.UserEntity;
import com.thoughtworks.rslist.entity.VoteEntity;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalTime;
import java.util.List;

public interface VoteRepository extends CrudRepository<VoteEntity,Integer> {

    List<VoteEntity> findAll();

    List<VoteEntity> findAllByVoteTimeBetween(LocalTime start, LocalTime end);
}
