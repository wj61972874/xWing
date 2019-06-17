package com.xwin.service.serviceImpl;

import com.xwin.common.utils.IDUtils;
import com.xwin.common.utils.RetCode;
import com.xwin.common.utils.ReturnResult;
import com.xwin.dao.daoImpl.AbbreviationDao;
import com.xwin.dao.daoImpl.FollowDao;
import com.xwin.dao.daoImpl.LikesDao;
import com.xwin.dao.daoImpl.UserDao;
import com.xwin.pojo.Abbreviation;
import com.xwin.pojo.Follow;
import com.xwin.pojo.User;
import com.xwin.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FollowServiceImpl implements FollowService {

    @Autowired
    private FollowDao followDao;

    @Autowired
    private AbbreviationDao abbreviationDao;

    @Autowired
    private LikesDao likesDao;

    @Autowired
    private UserDao userDao;

    @Override
    public ReturnResult removeFollow(Long userId, Long followedUserId) {

        // 获取关注
        Follow follow = followDao.findByUserIdAndFollowedUserId(userId, followedUserId);

        if (follow == null || follow.getDataStatus() == 0L) {
            return ReturnResult.build(RetCode.FAIL,"还没有关注此用户");
        }

        // 数据持久化
        follow.setDataStatus(0L);
        follow.setLastUpdateTime(new Date());
        followDao.save(follow);
        return ReturnResult.build(RetCode.SUCCESS, "success");
    }

    @Override
    public ReturnResult getUserFollow(Long userId) {

        // 获取用户
        Optional<User> userById = userDao.findById(userId);
        if (userById.equals(Optional.empty())) {
            return ReturnResult.build(RetCode.FAIL, "用户不存在");
        }

        // 获取用户的关注集合
        List<Follow> follows = followDao.getUserFollow(userId);

        // 返回结果map
        List<Map<String, Object>> result = new ArrayList<>();

        // 遍历
        for (Follow follow : follows) {

            // 被关注用户
            User followedUser = userDao.findById(follow.getFollowedUserId()).get();

            // 返回数据存入map
            Map<String, Object> map = new HashMap<>();
            map.put("follow_user_id", followedUser.getId());
            map.put("follow_username", followedUser.getNickname());
            map.put("follow_avatarUrl", followedUser.getAvatarUrl());

            String create_time_sub = follow.getLastUpdateTime().toString().substring(0,10);
            map.put("followTime", create_time_sub);

            // 获取被关注用户发布词条数量
            int count = abbreviationDao.countByUserId(follow.getFollowedUserId());
            map.put("sentCounts", count);

            // 获取被关注人发布的post
            List<Abbreviation> abbs = abbreviationDao.findByUserId(follow.getFollowedUserId());

            // 点赞数
            int praisedCount = 0;
            for (Abbreviation abb : abbs) {
                praisedCount = praisedCount + likesDao.countByLikeId(abb.getId());
            }
            map.put("praisedCount", praisedCount);

            result.add(map);
        }

        return ReturnResult.build(RetCode.SUCCESS, "success", result);
    }

    @Override
    public ReturnResult followUser(Long userId, Long followedUserId) {

        // 用户不存在返回错误信息
        Optional<User> userById = userDao.findById(userId);
        if (userById.equals(Optional.empty())) {
            return ReturnResult.build(RetCode.FAIL, "用户不存在");
        }

        // 被关注用户不存在返回错误信息
        Optional<User> followedUserById = userDao.findById(followedUserId);
        if (followedUserById.equals(Optional.empty())) {
            return ReturnResult.build(RetCode.FAIL, "被关注用户不存在");
        }


        // 如果关注用户之前取消关注过，直接在之前记录操作，否则新插入数据
        Follow follow = followDao.findByUserIdAndFollowedUserId(userId, followedUserId);

        // 已经关注此用户返回错误结果信息
        if (follow != null && follow.getDataStatus() == 1L) {
            return ReturnResult.build(RetCode.FAIL, "已经关注此用户");
        }

        // Date
        Date now = new Date();
        if (follow == null) {
            follow = new Follow();
            follow.setId(IDUtils.genItemId());
            follow.setCreateTime(now);
        }

        // 数据持久化
        follow.setUserId(userId);
        follow.setFollowedUserId(followedUserId);
        follow.setDataStatus(1L);
        follow.setLastUpdateTime(now);
        followDao.save(follow);

        return ReturnResult.build(RetCode.SUCCESS, "success");
    }
}
