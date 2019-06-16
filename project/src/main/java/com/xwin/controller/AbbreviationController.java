package com.xwin.controller;

import com.xwin.common.Base64ToImage;
import com.xwin.common.utils.ReturnResult;
import com.xwin.pojo.Abbreviation;
import com.xwin.pojo.Image;
import com.xwin.service.AbbreviationService;
import com.xwin.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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

    @RequestMapping(value = "/getRecommendedEntryList",method = RequestMethod.GET)
    public ReturnResult getRecommendedEntryList(){
        return abbreviationService.getRecommendedEntryList();
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




    @RequestMapping(value = "/upload" ,method = RequestMethod.POST )
    public ResponseEntity enrollMessage(@RequestParam Map<String, String> map){
        Map<String,String> response =new HashMap<>();
        // System.out.println(map.toString());
        String userId = "12222223";
        String abbrId = map.get("title1");
        String title = map.get("title2");
        String content = map.get("content");

        try {
            UUID uuid = UUID.randomUUID();
            String resourcePath  = ResourceUtils.getURL("classpath:").getPath();

            String imagePath = resourcePath.replace("/target/classes/","/src/main/resources/images/");
            String finalImagePath = imagePath+uuid.toString()+'/';
//            Base64ToImage.base64ToImageFile(map.get("backgroundImage"),finalImagePath+"back1.jpg");
//            Base64ToImage.base64ToImageFile(map.get("backgroundImage2"),finalImagePath+"back2.jpg");
//            Base64ToImage.base64ToImageFile(map.get("backgroundImage3"),finalImagePath+"back3.jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }

        int status =  abbreviationService.uploadAddr("",userId,abbrId,title,content);
        if(status==0){
            response.put("status","success");
        }else
        {
            response.put("status","error");
        }

        System.out.println((map.get("backgroundImage")).length());

        return new ResponseEntity(response, HttpStatus.OK);
    }
}
