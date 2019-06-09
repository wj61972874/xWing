package com.xwin.service.serviceImpl;

import com.xwin.common.GetPhoneMessage;
import com.xwin.common.utils.ModelUtils;
import com.xwin.common.utils.ObjectUtil;
import com.xwin.common.utils.RetCode;
import com.xwin.common.utils.ReturnResult;
import com.xwin.dao.daoImpl.AbbreviationDao;
import com.xwin.dao.daoImpl.FollowDao;
import com.xwin.dao.daoImpl.LikesDao;
import com.xwin.dao.daoImpl.UserDao;
import com.xwin.pojo.Abbreviation;
import com.xwin.pojo.Follow;
import com.xwin.pojo.Login;
import com.xwin.pojo.User;
import com.xwin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.math.BigInteger;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private AbbreviationDao abbreviationDao;

    @Autowired
    private LikesDao likesDao;

    @Autowired
    private FollowDao followDao;

    @Override
    public ReturnResult getPhoneMessage(String phone) {

//        String sendResult = GetPhoneMessage.getPram(phoneNumber);
        String randNum = GetPhoneMessage.randNum;
        User user = userDao.findByUsername(phone);
        if (user == null) {
            user = new User();
            Date now = new Date();
            user.setNickname(now.getTime() + "");
            user.setCreateTime(now);
            user.setDataStatus(1L);
            user.setLastUpdateTime(now);
            user.setUsername(phone);
            user.setVerificationCode(randNum);
            userDao.save(user);
        } else {
            user.setVerificationCode(randNum);
            userDao.save(user);
        }

        Map<String, Object> resultMap = new HashMap<>(1,1);
        resultMap.put("code", randNum);
        return ReturnResult.build(RetCode.SUCCESS, "success", resultMap);
    }

    @Override
    public ReturnResult userLogin(String verificationCode, String phone) {

        //  获取用户
        User user = userDao.findByUsername(phone);

        // 获取用户验证码
        String code = user.getVerificationCode();

        // 验证码为空返回错误信息
        if (verificationCode == null) {
            return ReturnResult.build(RetCode.FAIL, "此用户没有发送验证码");
        }

        // 验证码验证成功
        if (code.equals(verificationCode)) {
            Date now = new Date();
            user.setLastLoginTime(now);
            user.setLastUpdateTime(now);
            return ReturnResult.build(RetCode.SUCCESS, "success", user);
        }

        return ReturnResult.build(RetCode.FAIL, "验证码输入错误");
    }

    public User insertUser(User user) {
        user.setLastLoginTime(new Date());
        user.setCreateTime(new Date());
        List<User> userList = userDao.findAll();
        User loginUser = new User();
        List usernameList = new ArrayList();
        int index = -1;
        if (userList.size() == 0) {
            userDao.save(user);
            loginUser = user;
        } else {
            for (int i = 0; i < userList.size(); i++) {
                usernameList.add(userList.get(i).getUsername());
                if (usernameList.contains(user.getUsername())) {
                    index = i;
                }
            }
            if (index != -1) {
                loginUser = userList.get(index);
            } else {
                User user1 = userDao.save(user);
                loginUser = user1;
            }
        }
        return loginUser;
    }

    @Override
    public ReturnResult modifyUserInfo(String userId, String nickname, String gender, String region, String profile, String avatar) {

        // 获取用户
        Optional<User> userById = userDao.findById(userId);
        if (userById.equals(Optional.empty())) {
            return ReturnResult.build(RetCode.FAIL, "用户不存在");
        }
        User user = userById.get();

        // 修改用户信息
        if (nickname != null) {
            user.setNickname(nickname);
        }

        if (gender != null) {
            user.setGender(gender);
        }

        if (region != null) {
            user.setRegion(region);
        }

        if (profile != null) {
            user.setProfile(profile);
        }

        if (avatar != null) {
            user.setAvatarUrl(avatar);
        }

        // 数据持久化
        userDao.save(user);

        return ReturnResult.build(RetCode.SUCCESS, "success",user);
    }

    @Override
    public ReturnResult getUserFollow(String userId) {

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

            // 转map
//            Map<String, Object> map = ModelUtils.toMap(followedUser, true);
            Map<String, Object> map = new HashMap<>();

            map.put("id", followedUser.getId());
            map.put("nickanme", followedUser.getNickname());
            map.put("username", followedUser.getUsername());
            map.put("avatar", followedUser.getAvatarUrl());

            // 获取被关注用户发布post数量
            int count = abbreviationDao.countByUserId(follow.getFollowedUserId());
            map.put("sentCounts", count);

            // 获取被关注人发布的post
            List<Abbreviation> abbs = abbreviationDao.findByUserId(follow.getFollowedUserId());

            // 点赞数
            int praisedCount = 0;
            for (Abbreviation abb : abbs) {
                praisedCount =  praisedCount + likesDao.countByLikeId(abb.getId());
            }
            map.put("praisedCount", praisedCount);

            result.add(map);
        }

        return ReturnResult.build(RetCode.SUCCESS, "success", result);
    }
}
