package com.xwin.dao.daoImpl;

import com.xwin.pojo.BannerNews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface NewsDao extends JpaRepository<BannerNews, Long> {

    @Query(value = "select *  from banner_news where create_time >=?1 and create_time <=?2",nativeQuery = true)
    public List<BannerNews> getWeekNews(Date weekBeginTime, Date weekEndTime);
}
