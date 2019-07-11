package com.xwin.pojo;

import java.util.Date;

public class CommentResult {
    Long id;
    Long userId;
    Long postId;
    float item_rate;
    String item_content;
    String item_avatar;
    String username;
    Date item_date;

    public CommentResult(Long id, Long userId, Long postId, float item_rate, String item_content, String item_avatar, String username, Date item_date) {
        this.id = id;
        this.userId = userId;
        this.postId = postId;
        this.item_rate = item_rate;
        this.item_content = item_content;
        this.item_avatar = item_avatar;
        this.username = username;
        this.item_date = item_date;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public float getItem_rate() {
        return item_rate;
    }

    public void setItem_rate(float item_rate) {
        this.item_rate = item_rate;
    }

    public String getItem_content() {
        return item_content;
    }

    public void setItem_content(String item_content) {
        this.item_content = item_content;
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
