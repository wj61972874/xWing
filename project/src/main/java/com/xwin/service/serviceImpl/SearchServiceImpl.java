package com.xwin.service.serviceImpl;

import com.xwin.common.utils.IDUtils;
import com.xwin.dao.daoImpl.SearchDao;
import com.xwin.pojo.HotSearch;
import com.xwin.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private SearchDao searchDao;

    @Override
    public List<HotSearch> getHotSearch() {
        return searchDao.findAll();
    }

    @Override
    public boolean insertSearchRecord(String keyWords) {


//        HotSearch hotSearch=new HotSearch();
//        hotSearch.setId(IDUtils.genItemId());
//        hotSearch.setContent(keyWords);
//        hotSearch.setCount();
//        return searchDao.save();
        return true;
    }
}
