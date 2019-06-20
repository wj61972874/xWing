package com.xwin.service;

import com.xwin.common.utils.ReturnResult;
import com.xwin.pojo.Abbreviation;
import org.apache.solr.client.solrj.SolrServerException;

import java.io.IOException;


public interface AbbreviationService {

    public void getHotNews();

    public ReturnResult getAbbreviationDetail(Long entryId, Long userId);
    public ReturnResult getRecommendedEntryList();
    /**
     * 点赞词条
     *
     * @param userId 用户id
     * @param abbrId 词条id
     * @return 业务结果对象
     */
    ReturnResult likeAbbr(Long userId, Long abbrId);

    public ReturnResult getHotSearchResults();


    /**
     * 取消词条点赞
     *
     * @param userId 用户id
     * @param abbrId 词条id
     * @return 业务结果对象
     */
    ReturnResult removeLikeAbbr(Long userId, Long abbrId);
    public int uploadAddr(String id , String userId , String addr, String title,String content,String type, String image1, String image2, String image3) throws IOException, SolrServerException;

    public void insertToSolr(Abbreviation abbreviation) throws IOException, SolrServerException;
}
