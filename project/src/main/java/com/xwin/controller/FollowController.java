package com.xwin.controller;

import com.xwin.common.utils.ReturnResult;
import com.xwin.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/follow")
public class FollowController {

    @Autowired
    private FollowService followService;

    @RequestMapping(value = "/follow", method = RequestMethod.GET)
    public ReturnResult getUserFollow(
            @RequestParam(value = "userId") String userId) {
        Long id = Long.parseLong(userId);
        return followService.getUserFollow(id);
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public ReturnResult removeFollow(
            @RequestParam(value = "userId") String userId,
            @RequestParam(value = "followedUserId") String followedUserId) {
        Long id = Long.parseLong(userId);
        Long followed_id= Long.parseLong(followedUserId);
        return followService.removeFollow(id, followed_id);
    }

    @RequestMapping(value = "/follow", method = RequestMethod.POST)
    public ReturnResult followUser(
            @RequestParam(value = "userId") String userId,
            @RequestParam(value = "followedUserId") String followedUserId) {
        Long id = Long.parseLong(userId);
        Long followedId = Long.parseLong(followedUserId);

        return followService.followUser(id, followedId);
    }
}
