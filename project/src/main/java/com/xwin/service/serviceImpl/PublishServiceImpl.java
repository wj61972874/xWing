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
            abbrMap.put("createTime", abbreviation.getCreateTime());
            abbrMap.put("type", abbreviation.getType());
            abbrMap.put("title", abbreviation.getAbbrName());
            abbrMap.put("content", abbreviation.getContent());
            abbrMap.put("likedCount", abbreviation.getLikedCount());
            abbrMap.put("fullName", abbreviation.getFullName());

            // 获取词条图片url
            List<Image> imagesList = pictureDao.findByAbbreviationId(abbreviation.getId());
            List<Map<String, Object>> images = new ArrayList<>(imagesList.size());
            for (Image image : imagesList) {
                Map<String, Object> imageMap = new HashMap<>();
                imageMap.put("imageUrl", image.getPath());
                images.add(imageMap);
            }
            abbrMap.put("image", images);
            abbrList.add(abbrMap);
        }

        return ReturnResult.build(RetCode.SUCCESS, "success", abbrList);
    }
}
