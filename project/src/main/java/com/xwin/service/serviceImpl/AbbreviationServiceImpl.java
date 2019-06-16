package com.xwin.service.serviceImpl;

import com.xwin.common.utils.IDUtils;
import com.xwin.common.utils.RetCode;
import com.xwin.common.utils.ReturnResult;
import com.xwin.dao.daoImpl.AbbreviationDao;
import com.xwin.dao.daoImpl.LikesDao;
import com.xwin.dao.daoImpl.PictureDao;
import com.xwin.dao.daoImpl.UserDao;
import com.xwin.pojo.Abbreviation;
import com.xwin.pojo.Image;
import com.xwin.pojo.Likes;
import com.xwin.pojo.User;
import com.xwin.service.AbbreviationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AbbreviationServiceImpl implements AbbreviationService {
    @Autowired
    private AbbreviationDao abbreviationDao;

    @Autowired
    private PictureDao pictureDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private LikesDao likesDao;

    @Override
    public void getHotNews() {

    }

    @Override
    public Abbreviation getAbbreviationDetail(Long entryId) {
        return abbreviationDao.getAbbreviationDetail(entryId);
    }

    @Override
    public ReturnResult likeAbbr(Long userId, Long abbrId) {

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

        // 获取点赞
        Likes like = likesDao.findByUserIdAndLikeId(userId, abbrId);

        // 已经点赞返回错误结果信息
        if (like != null && like.getDataStatus() == 1L) {
            return ReturnResult.build(RetCode.FAIL, "已经点赞此词条");
        }

        // date
        Date now = new Date();

        // 数据持久化
        if (like == null) {
            like = new Likes();
            like.setId(IDUtils.genItemId());
            like.setCreateTime(now);
            like.setLikeId(abbrId);
            like.setUserId(userId);
        }

        like.setLastUpdateTime(now);
        like.setDataStatus(1L);
        likesDao.save(like);

        // 修改词条点赞数,持久化
        Abbreviation abbreviation = abbr.get();
        abbreviation.setLastUpdateTime(now);
        abbreviation.setLikedCount(abbreviation.getLikedCount() + 1);
        abbreviationDao.save(abbreviation);

        return ReturnResult.build(RetCode.SUCCESS, "success");
    }

    @Override
    public ReturnResult removeLikeAbbr(Long userId, Long abbrId) {

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

        // 获取点赞
        Likes like = likesDao.findByUserIdAndLikeId(userId, abbrId);

        if (like == null) {
            return ReturnResult.build(RetCode.FAIL, "没有点赞过此词条");
        }

        // 已经点赞返回错误结果信息
        if (like.getDataStatus() == 0L) {
            return ReturnResult.build(RetCode.FAIL, "已经取消点赞此词条");
        }

        // date
        Date now = new Date();

        // 数据持久化
        like.setLastUpdateTime(now);
        like.setDataStatus(0L);
        likesDao.save(like);

        // 修改词条点赞数,持久化
        Abbreviation abbreviation = abbr.get();
        abbreviation.setLastUpdateTime(now);
        abbreviation.setLikedCount(abbreviation.getLikedCount() - 1);
        abbreviationDao.save(abbreviation);

        return ReturnResult.build(RetCode.SUCCESS, "success");
    }

}
