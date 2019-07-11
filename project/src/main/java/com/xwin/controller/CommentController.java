package com.xwin.controller;

import com.xwin.common.utils.RetCode;
import com.xwin.common.utils.ReturnResult;
import com.xwin.service.CommentService;
import com.xwin.service.FollowService;
import com.xwin.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @RequestMapping(value = "/getCommentList", method = RequestMethod.GET)
    public ReturnResult getCommentList(
            @RequestParam(value = "abbrId") Long abbrId,
            @RequestParam(value = "page") int page) {
        return commentService.getCommentList(abbrId, page);
    }

    @RequestMapping(value = "/getCommentListTwo", method = RequestMethod.GET)
    public ReturnResult getCommentListTwo(
            @RequestParam(value = "abbrId") Long abbrId) {
        return commentService.getCommentListTwo(abbrId);
    }

    @RequestMapping(value = "/post", method = RequestMethod.POST)
    public ReturnResult postComment(
            @RequestParam(value = "userId") Long userId,
            @RequestParam(value = "abbrId") Long abbrId,
            @RequestParam(value = "rate") Float rate,
            @RequestParam(value = "content") String content) {
        return commentService.post(userId, abbrId, rate, content);
    }
}
