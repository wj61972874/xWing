package com.xwin.service.serviceImpl;

import com.xwin.common.utils.RetCode;
import com.xwin.common.utils.ReturnResult;
import com.xwin.dao.daoImpl.AbbreviationDao;
import com.xwin.dao.daoImpl.PictureDao;
import com.xwin.dao.daoImpl.UserDao;
import com.xwin.pojo.Abbreviation;
import com.xwin.pojo.Image;
import com.xwin.pojo.User;
import com.xwin.service.PublishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PublishServiceImpl implements PublishService {

    @Autowired
    private AbbreviationDao abbreviationDao;

    @Autowired
    private PictureDao pictureDao;

    @Autowired
    private UserDao userDao;

    @Override
    public ReturnResult getAllPublish(Long userId) {

        // 获取用户
        Optional<User> userById = userDao.findById(userId);
        if (userById.equals(Optional.empty())) {
            return ReturnResult.build(RetCode.FAIL, "用户不存在");
        }

        // 获取用户发布的词条
        List<Abbreviation> abbreviations = abbreviationDao.getAbbreviationByUserId(userId);

        // 存入返回list
        List<Map<String, Object>> abbrList = new ArrayList<>(abbreviations.size());
        for (Abbreviation abbreviation : abbreviations) {
            Map<String, Object> abbrMap = new HashMap<>();
            String create_time_sub = abbreviation.getCreateTime().toString().substring(0,10);
            abbrMap.put("item_id", abbreviation.getId());
            abbrMap.put("abb_create_time", create_time_sub);
            abbrMap.put("abb_type", abbreviation.getType());
            abbrMap.put("item_name", abbreviation.getAbbrName());
            abbrMap.put("item_content", abbreviation.getContent());
            abbrMap.put("abb_likedCount", abbreviation.getLikedCount());
            abbrMap.put("abb_fullName", abbreviation.getFullName());

            // 获取词条图片url
            List<Image> imagesList = abbreviation.getImageList();
            Object o =
                    imagesList.isEmpty() ? abbrMap.put("item_image", null) : abbrMap.put("item_image", imagesList.get(0).getPath());

            abbrList.add(abbrMap);
        }

        return ReturnResult.build(RetCode.SUCCESS, "success", abbrList);
    }
}
