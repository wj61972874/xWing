package com.xwin.service;

import com.xwin.pojo.Abbreviation;

public interface AbbreviationService {

    public void getHotNews();

    public Abbreviation getAbbreviationDetail(Long entryId);
    public int uploadAddr(String id , String userId , String addr, String title,String content);
}
