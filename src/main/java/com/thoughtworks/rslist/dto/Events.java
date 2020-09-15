package com.thoughtworks.rslist.dto;

public class Events {
    String event;
    String keywords;

    public Events() {
    }

    public Events(String event, String keywords) {
        this.event = event;
        this.keywords = keywords;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }
}
