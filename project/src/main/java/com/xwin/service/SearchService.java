package com.xwin.service;

import com.xwin.pojo.HotSearch;

import java.util.List;

public interface SearchService {

    public List<HotSearch> getHotSearch();

    public boolean insertSearchRecord(String keyWords);
}
