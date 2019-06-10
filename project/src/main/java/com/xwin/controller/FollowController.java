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

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public ReturnResult removeFollow(
            @RequestParam(value = "userId") Long userId,
            @RequestParam(value = "followedUserId") Long followedUserId) {
        return followService.removeFollow(userId, followedUserId);
    }
}
