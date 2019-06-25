package com.xwin.pojo;

import java.util.Date;

public class Explore {
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    Long id;
    String event_desc;
    String item_name;
    String item_content;
    String item_image;
    String event_time;
    String item_username;
    String item_avatar;
    Date item_date;

    public String getEvent_desc() {
        return event_desc;
    }

    public void setEvent_desc(String event_desc) {
        this.event_desc = event_desc;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getItem_content() {
        return item_content;
    }

    public void setItem_content(String item_content) {
        this.item_content = item_content;
    }

    public String getItem_image() {
        return item_image;
    }

    public void setItem_image(String item_image) {
        this.item_image = item_image;
    }

    public String getEvent_time() {
        return event_time;
    }

    public void setEvent_time(String event_time) {
        this.event_time = event_time;
    }

    public String getItem_username() {
        return item_username;
    }

    public void setItem_username(String item_username) {
        this.item_username = item_username;
    }

    public String getItem_avatar() {
        return item_avatar;
    }

    public void setItem_avatar(String item_avatar) {
        this.item_avatar = item_avatar;
    }

    public Date getItem_date() {
        return item_date;
    }

    public void setItem_date(Date item_date) {
        this.item_date = item_date;
    }
}
