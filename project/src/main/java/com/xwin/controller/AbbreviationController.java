package com.xwin.controller;

import com.xwin.common.utils.RetCode;
import com.xwin.common.utils.ReturnResult;
import com.xwin.pojo.Abbreviation;
import com.xwin.service.AbbreviationService;
import com.xwin.service.PictureService;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
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
    private String baseUrl = "http://localhost:8888/solr/sundae";

    @Autowired
    private AbbreviationService abbreviationService;

    @Autowired
    private PictureService pictureService;

    @RequestMapping(value = "/getHotEntry", method = RequestMethod.GET)
    public ReturnResult getHotNews() {
        abbreviationService.getHotNews();
        return null;
    }

    @RequestMapping(value = "/getHotSearchResults", method = RequestMethod.GET)
    public ReturnResult getHotSearchResults() {
        return abbreviationService.getHotSearchResults();
    }

    @RequestMapping(value = "/getOneEntryDetail", method = RequestMethod.GET)
    public ReturnResult getAbbreviationDetail(
            @RequestParam Long entryId,
            @RequestParam Long userId) {
        return abbreviationService.getAbbreviationDetail(entryId, userId);
    }

    @RequestMapping(value = "/getRecommendedEntryList", method = RequestMethod.GET)
    public ReturnResult getRecommendedEntryList() {
        return abbreviationService.getRecommendedEntryList();
    }

    @RequestMapping(value = "/like", method = RequestMethod.POST)
    public ReturnResult likeAbbr(
            @RequestParam(value = "userId") String userId,
            @RequestParam(value = "entryId") String entryId) {

        Long id = Long.parseLong(userId);
        Long abbrId = Long.parseLong(entryId);
        return abbreviationService.likeAbbr(id, abbrId);
    }

    @RequestMapping(value = "/removeLike", method = RequestMethod.POST)
    public ReturnResult removeLikeAbbr(
            @RequestParam(value = "userId") String userId,
            @RequestParam(value = "entryId") String entryId) {

        Long id = Long.parseLong(userId);
        Long abbrId = Long.parseLong(entryId);
        return abbreviationService.removeLikeAbbr(id, abbrId);
    }


    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ResponseEntity enrollMessage(@RequestParam Map<String, String> map) throws IOException, SolrServerException {
        Map<String, String> response = new HashMap<>();
        // System.out.println(map.toString());
        String userId = map.get("userId");
        String abbrId = map.get("title1");
        String title = map.get("title2");
        String content = map.get("content");
        String type = map.get("type");
        String image1 = map.get("backgroundImage");
        String image2 = map.get("backgroundImage2");
        String image3 = map.get("backgroundImage3");

        int status = abbreviationService.uploadAddr("", userId, abbrId, title, content, type, image1, image2, image3);
        if (status == 0) {
            response.put("status", "success");
            SolrServer solrServer = new HttpSolrServer(baseUrl);
            Abbreviation testBean = new Abbreviation();
            testBean.setId(10000L);
            testBean.setFullName("test");
            testBean.setAbbrName("testData is testData");

            //solrServer.addBean(abbreviation);
        } else {
            response.put("status", "error");
        }

        System.out.println((map.get("backgroundImage")).length());

        return new ResponseEntity(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/searchAbbreviation", method = RequestMethod.POST)
    public ReturnResult searchAbbreviation(@RequestParam(value = "keyWords") String keyWords) throws SolrServerException, IOException {

        SolrServer solrServer = new HttpSolrServer(baseUrl);
        SolrQuery solrQuery = new SolrQuery();

        solrQuery.set("q", keyWords);

        QueryResponse response = solrServer.query(solrQuery);
        SolrDocumentList solrDocuments = response.getResults();
        //abbreviationService.insertToSolr();
        if (solrDocuments.getNumFound() == 0) {
            return ReturnResult.build(RetCode.FAIL, "there are no record");
        } else {
            return ReturnResult.build(RetCode.SUCCESS, "success", solrDocuments);
        }
    }
}
