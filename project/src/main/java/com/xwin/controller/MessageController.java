package com.xwin.controller;

import com.xwin.common.utils.ReturnResult;
import com.xwin.service.FollowService;
import com.xwin.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/message")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @RequestMapping(value = "/getMessageList", method = RequestMethod.GET)
    public ReturnResult getMessageList(
            @RequestParam(value = "userId") Long userId,
            @RequestParam(value = "page") int page) {
        return messageService.getMessageList(userId, page);
    }
}
