package com.xwin.service.serviceImpl;

import com.xwin.common.utils.DateUtils;
import com.xwin.dao.daoImpl.NewsDao;
import com.xwin.pojo.BannerNews;
import com.xwin.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {

    @Autowired
    private NewsDao newsDao;

    @Override
    public List<BannerNews> getLastestNews() {

        List<BannerNews> newsList=newsDao.getWeekNews(DateUtils.getBeginDayOfWeek(),DateUtils.getEndDayOfWeek());
        return newsList;
    }
}
