package com.xwin.service;

import com.xwin.common.utils.ReturnResult;
import com.xwin.pojo.Abbreviation;


public interface AbbreviationService {

    public void getHotNews();

    public Abbreviation getAbbreviationDetail(Long entryId);
    public ReturnResult getRecommendedEntryList();
    /**
     * 点赞词条
     *
     * @param userId 用户id
     * @param abbrId 词条id
     * @return 业务结果对象
     */
    ReturnResult likeAbbr(Long userId, Long abbrId);


    /**
     * 取消词条点赞
     *
     * @param userId 用户id
     * @param abbrId 词条id
     * @return 业务结果对象
     */
    ReturnResult removeLikeAbbr(Long userId, Long abbrId);
    public int uploadAddr(String id , String userId , String addr, String title,String content);
}
