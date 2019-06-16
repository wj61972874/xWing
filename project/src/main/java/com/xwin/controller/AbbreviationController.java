package com.xwin.controller;

import com.xwin.common.utils.ReturnResult;
import com.xwin.pojo.Abbreviation;
import com.xwin.pojo.Image;
import com.xwin.service.AbbreviationService;
import com.xwin.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/abbreviation")
public class AbbreviationController {

    @Autowired
    private AbbreviationService abbreviationService;

    @Autowired
    private PictureService pictureService;

    @RequestMapping(value = "/getHotEntry",method = RequestMethod.GET)
    public ReturnResult getHotNews(){
        abbreviationService.getHotNews();
        return null;
    }

    @RequestMapping(value = "/getOneEntryDetail",method = RequestMethod.POST)
    public ReturnResult getAbbreviationDetail(Long entryId){
        Abbreviation entry=abbreviationService.getAbbreviationDetail(entryId);
        Image image=pictureService.getImageById(entryId);
        entry.setImage(image);
        return ReturnResult.build(200,"success",entry);
    }

    @RequestMapping(value = "/like",method = RequestMethod.POST)
    public ReturnResult likeAbbr(
            @RequestParam(value = "userId") String userId,
            @RequestParam(value = "entryId") String entryId){

        Long id = Long.parseLong(userId);
        Long abbrId = Long.parseLong(entryId);
        return abbreviationService.likeAbbr(id, abbrId);
    }

    @RequestMapping(value = "/removeLike",method = RequestMethod.POST)
    public ReturnResult removeLikeAbbr(
            @RequestParam(value = "userId") String userId,
            @RequestParam(value = "entryId") String entryId){

        Long id = Long.parseLong(userId);
        Long abbrId = Long.parseLong(entryId);
        return abbreviationService.removeLikeAbbr(id, abbrId);
    }

}
