package com.thoughtworks.rslist.repository;

import com.thoughtworks.rslist.entity.EventEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends CrudRepository<EventEntity,Integer>{

    List<EventEntity> findAll();
    List<EventEntity> findByIdBetween(int start , int end);
    void deleteAllByUserId(int userid);



}
