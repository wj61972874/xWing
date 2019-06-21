package com.xwin.controller;

import com.xwin.common.utils.RetCode;
import com.xwin.common.utils.ReturnResult;
import com.xwin.pojo.Abbreviation;
import com.xwin.pojo.HotSearch;
import com.xwin.service.AbbreviationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/search")
public class SearchController {
    @Autowired
    private AbbreviationService abbreviationService;

    @RequestMapping(value = "/searchAbbreviation",method = RequestMethod.POST)
    public ReturnResult searchAbbreviation(@RequestParam(value = "keyWords") String keyWords){
        List<Abbreviation> abbreviationList= abbreviationService.getAbbreviationByKeyWords(keyWords);
        if(abbreviationList!=null){
           return ReturnResult.build(RetCode.SUCCESS,"success",abbreviationList);
        }else{
         return  ReturnResult.build(RetCode.FAIL,"this is no records");
        }
    }

    public List<HotSearch> hotSearch(){

        return null;
    }
}
