package com.xwin.service.serviceImpl;

import com.xwin.common.GetPhoneMessage;
import com.xwin.common.utils.*;
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


import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public ReturnResult getPhoneMessage(String phone) {

//        String sendResult = GetPhoneMessage.getPram(phoneNumber);
//        String randNum = GetPhoneMessage.randNum;
        String randNum = "111111";
        User user = userDao.findByUsername(phone);
        if (user == null) {
            user = new User();
            Date now = new Date();
            user.setId(IDUtils.genItemId());
            user.setNickname(phone+"_"+now.getYear());
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
    public ReturnResult modifyUserInfo(Long userId, String nickname, String gender, String region, String profile, String avatar) {

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


}
