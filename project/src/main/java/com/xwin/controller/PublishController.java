package com.xwin.controller;

import com.xwin.common.utils.ReturnResult;
import com.xwin.service.PublishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/userPublish")
public class PublishController {
    /**
     *
     */
    @Autowired
    private PublishService publishService;

    @RequestMapping(value = "/getAllPublish",method = RequestMethod.GET)
    public ReturnResult getHotNews( @RequestParam(value = "userId") Long userId){
        return publishService.getAllPublish(userId);
    }
}
