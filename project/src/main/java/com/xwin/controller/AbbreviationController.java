package com.xwin.controller;

import com.xwin.common.utils.ReturnResult;
import com.xwin.pojo.Abbreviation;
import com.xwin.pojo.Image;
import com.xwin.service.AbbreviationService;
import com.xwin.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
    public ReturnResult getAbbreviationDetail(String entryId){
        Abbreviation entry=abbreviationService.getAbbreviationDetail(entryId);
        Image image=pictureService.getImageById(entryId);
        entry.setImage(image);
        return ReturnResult.build(200,"success",entry);
    }
}
