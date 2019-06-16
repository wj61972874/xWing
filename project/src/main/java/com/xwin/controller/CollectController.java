package com.xwin.controller;

import com.xwin.common.utils.ReturnResult;
import com.xwin.service.CollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/collection")
public class CollectController {

    @Autowired
    private CollectService collectionService;

    @RequestMapping(value = "/abbreviation", method = RequestMethod.GET)
    public ReturnResult getUserCollection(
            @RequestParam(value = "userId") Long userId) {
        return collectionService.getUserCollection(userId);
    }

    @RequestMapping(value = "/removeCollect", method = RequestMethod.POST)
    public ReturnResult removeUserCollection(
            @RequestParam(value = "userId") Long userId,
            @RequestParam(value = "entryId") Long entryId) {
        return collectionService.removeUserCollection(userId,entryId);
    }

    @RequestMapping(value = "/abbreviation", method = RequestMethod.POST)
    public ReturnResult collectAbbr(
            @RequestParam(value = "userId") String userId,
            @RequestParam(value = "entryId") String entryId) {

        Long id = Long.parseLong(userId);
        Long abbrId = Long.parseLong(entryId);

        return collectionService.collectAbbr(id,abbrId);
    }
}
