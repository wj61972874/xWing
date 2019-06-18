package com.xwin.controller;

import com.xwin.common.Base64ToImage;
import com.xwin.service.AbbreviationService;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class PostController {

    private String baseUrl="http://localhost:8888/solr/sundae";

    @Autowired
    AbbreviationService abbreviationService;
    @RequestMapping(value = "/post/message" ,method = RequestMethod.POST )
    public Map enrollMessage(@RequestParam Map<String, String> map){
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
            Base64ToImage.base64ToImageFile(map.get("backgroundImage"),finalImagePath+"back1.jpg");
            Base64ToImage.base64ToImageFile(map.get("backgroundImage2"),finalImagePath+"back2.jpg");
            Base64ToImage.base64ToImageFile(map.get("backgroundImage3"),finalImagePath+"back3.jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }

       int status =  abbreviationService.uploadAddr("",userId,abbrId,title,content);
        if(status==0){
            response.put("status","success");
            SolrServer solrServer=new HttpSolrServer(baseUrl);
        }else
        {
            response.put("status","error");
        }

        System.out.println((map.get("backgroundImage")).length());
        return response;
    }

    public static void main(String[] args) {
        try {
            System.out.println(ResourceUtils.getURL("classpath:").getPath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
