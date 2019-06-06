package com.xwin.service.serviceImpl;

import com.xwin.dao.daoImpl.AbbreviationDao;
import com.xwin.dao.daoImpl.PictureDao;
import com.xwin.pojo.Abbreviation;
import com.xwin.service.AbbreviationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AbbreviationServiceImpl implements AbbreviationService {
    @Autowired
    private AbbreviationDao abbreviationDao;

    @Override
    public void getHotNews() {

    }

    @Override
    public Abbreviation getAbbreviationDetail(String entryId) {
        return abbreviationDao.getAbbreviationDetail(entryId);
    }
}
