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

    @RequestMapping(value = "/readMessage", method = RequestMethod.POST)
    public ReturnResult readMessage(
            @RequestParam(value = "userId") Long userId,
            @RequestParam(value = "messageId") Long messageId) {
        return messageService.readMessage(userId, messageId);
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public ReturnResult removeMessage(
            @RequestParam(value = "userId") Long userId,
            @RequestParam(value = "messageId") Long messageId,
            @RequestParam(value = "listSize") Long listSize) {
        return messageService.removeMessage(userId, messageId, listSize);
    }
}
