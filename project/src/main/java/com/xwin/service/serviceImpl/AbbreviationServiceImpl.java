package com.xwin.service.serviceImpl;

import com.xwin.dao.daoImpl.AbbreviationDao;
import com.xwin.dao.daoImpl.PictureDao;
import com.xwin.pojo.Abbreviation;
import com.xwin.service.AbbreviationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AbbreviationServiceImpl implements AbbreviationService {
    @Autowired
    private AbbreviationDao abbreviationDao;

    @Override
    public void getHotNews() {

    }

    @Override
    public Abbreviation getAbbreviationDetail(Long entryId) {
        return abbreviationDao.getAbbreviationDetail(entryId);
    }

    @Override
    public int uploadAddr(String id, String userId, String addr, String title, String content) {

        Date date = new Date();

        Abbreviation abbreviation = new Abbreviation();
        abbreviation.setId(43l);
        abbreviation.setUserId(Long.parseLong(userId) );
        abbreviation.setAbbrName(addr);
        abbreviation.setContent(content);
        abbreviation.setFullName(title);
        abbreviation.setCreateTime(date);
        abbreviation.setLastUpdateTime(date);
        abbreviation.setType(1l);
        abbreviation.setDataStatus(1l);
        abbreviation.setCreateBy(userId);
        abbreviationDao.save(abbreviation);
        return 0;
    }
}
