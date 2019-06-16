package com.xwin.service.serviceImpl;

import com.xwin.common.utils.IDUtils;
import com.xwin.common.utils.RetCode;
import com.xwin.common.utils.ReturnResult;
import com.xwin.dao.daoImpl.*;
import com.xwin.pojo.Abbreviation;
import com.xwin.pojo.Collect;
import com.xwin.pojo.Image;
import com.xwin.pojo.User;
import com.xwin.service.CollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CollectServiceImpl implements CollectService {

    @Autowired
    private CollectDao collectDao;

    @Autowired
    private AbbreviationDao abbreviationDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private PictureDao pictureDao;

    @Override
    public ReturnResult getUserCollection(Long userId) {

        // 获取用户
        Optional<User> userById = userDao.findById(userId);
        if (userById.equals(Optional.empty())) {
            return ReturnResult.build(RetCode.FAIL, "用户不存在");
        }

        // 获取用户收藏
        List<Collect> collections = collectDao.findByUserId(userId);

        // 创建返回list
        List<Map<String, Object>> result = new ArrayList<>(collections.size());

        for (Collect collection : collections) {
            Map<String, Object> map = new HashMap<>(16, .75f);

            // 根据词条id获取词条
            Abbreviation abbreviation = abbreviationDao.findById(collection.getEntryId()).get();

            // 获取词条发布作者
            User user = userDao.findById(abbreviation.getUserId()).get();

            // 加入map
            map.put("item_id", abbreviation.getId());
            map.put("item_name", abbreviation.getAbbrName());
            map.put("item_content", abbreviation.getContent());
            map.put("collect_author", user.getNickname());
            map.put("collect_time", collection.getCreateTime());

            // 获取词条图片url
            List<Image> imagesList = pictureDao.findByAbbreviationId(abbreviation.getId());

            if (!imagesList.isEmpty()) {
                Image image = imagesList.get(0);
                map.put("item_image", image.getPath());
            }

            result.add(map);
        }

        return ReturnResult.build(RetCode.SUCCESS, "success", result);
    }

    @Override
    public ReturnResult removeUserCollection(Long userId, Long entryId) {

        // 用户不存在返回错误信息
        Optional<User> userById = userDao.findById(userId);
        if (userById.equals(Optional.empty())) {
            return ReturnResult.build(RetCode.FAIL, "用户不存在");
        }

        // 词条不存在返回错误信息
        Optional<Abbreviation> abbr = abbreviationDao.findById(entryId);
        if (abbr.equals(Optional.empty())) {
            return ReturnResult.build(RetCode.FAIL, "词条不存在");
        }

        // 获取搜藏记录
        Collect collection = collectDao.findByUserIdAndEntryId(userId, entryId);

        // 没有收藏过此词条返回错误信息
        if (collection == null) {
            return ReturnResult.build(RetCode.FAIL, "没有收藏过此词条");
        }

        // 已经取消收藏返回错误信息
        if (collection.getDataStatus() == 0L) {
            return ReturnResult.build(RetCode.FAIL, "已经取消收藏此词条");
        }

        // 数据持久化
        Date now = new Date();
        collection.setDataStatus(0L);
        collection.setLastUpdateTime(now);
        collectDao.save(collection);

        return ReturnResult.build(RetCode.SUCCESS, "success", "success");
    }

    @Override
    public ReturnResult collectAbbr(Long userId, Long abbrId) {

        // 用户不存在返回错误信息
        Optional<User> userById = userDao.findById(userId);
        if (userById.equals(Optional.empty())) {
            return ReturnResult.build(RetCode.FAIL, "用户不存在");
        }

        // 词条不存在返回错误信息
        Optional<Abbreviation> abbr = abbreviationDao.findById(abbrId);
        if (abbr.equals(Optional.empty())) {
            return ReturnResult.build(RetCode.FAIL, "词条不存在");
        }


        // 已经收藏成功返回错误信息
        Collect collection = collectDao.findByUserIdAndEntryId(userId, abbrId);
        if (collection != null && collection.getDataStatus() == 1L) {
            return ReturnResult.build(RetCode.FAIL, "已经收藏过此词条");
        }

        Date now = new Date();

        // 初始化收藏
        if (collection == null) {
            collection = new Collect();
            collection.setId(IDUtils.genItemId());
            collection.setCreateTime(now);
        }


        // 数据持久化
        collection.setDataStatus(1L);
        collection.setUserId(userId);
        collection.setEntryId(abbrId);
        collection.setLastUpdateTime(now);
        collection.setCreateTime(now);
        collectDao.save(collection);

        return ReturnResult.build(RetCode.SUCCESS, "success");
    }

}
