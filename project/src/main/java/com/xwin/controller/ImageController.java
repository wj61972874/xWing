package com.xwin.controller;

import com.xwin.common.Base64DecodeMultipartFile;
import com.xwin.common.utils.JsonUtils;
import com.xwin.pojo.Image;
import com.xwin.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/image")
public class ImageController {

    @Autowired
    private PictureService pictureService;

    //图片上传方法
    @RequestMapping(value = "/upload/img", method = RequestMethod.POST)
    @ResponseBody
//    public String updateImage(@PathVariable MultipartFile uploadFile){
    public String updateImage(@RequestParam(value = "uploadFile") String uploadFile) {

        MultipartFile multiFile = Base64DecodeMultipartFile.base64Convert(uploadFile);
        Map result = pictureService.uploadPicture(multiFile);
        //为了保证浏览器兼容性，需要将result转换成json格式的字符串
        String json = JsonUtils.objectToJson(result);
        return json;
    }

    @RequestMapping(value = "getImages/{imageType}", method = RequestMethod.GET)
    public List<Image> getAllImages(@PathVariable Long imageType) {
        List<Image> imageList = pictureService.getAllImagesByType(imageType);
        return imageList;
    }

    @RequestMapping(value = "uploadTest", method = RequestMethod.POST)
    public String uploadTest(String base64data, Long abbrId, String type) {
        return pictureService.uploadImage(base64data, abbrId, type);
    }

}
