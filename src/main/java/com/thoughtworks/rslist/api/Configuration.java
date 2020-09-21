//package com.thoughtworks.rslist.api;
//
//import com.thoughtworks.rslist.repository.EventRepository;
//import com.thoughtworks.rslist.repository.UserRepository;
//import com.thoughtworks.rslist.repository.VoteRepository;
//import com.thoughtworks.rslist.service.EventService;
//import org.springframework.boot.autoconfigure.rsocket.RSocketProperties;
//import org.springframework.context.annotation.Bean;
//
//public class Configuration {
//
//    private final UserRepository userRepository;
//    private final EventRepository eventRepository;
//
//    public Configuration(UserRepository userRepository, EventRepository eventRepository, VoteRepository voteRepository) {
//        this.userRepository = userRepository;
//        this.eventRepository = eventRepository;
//        this.voteRepository = voteRepository;
//    }
//
//    private final VoteRepository voteRepository;
//
//    @Bean(name = "EventService")
//    public EventService getEventService(){
//        return ;
//    }
//}
