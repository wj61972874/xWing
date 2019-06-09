package com.xwin.service;

import com.xwin.common.utils.ReturnResult;
import com.xwin.pojo.Login;
import com.xwin.pojo.User;

public interface UserService {

    /**
     * 获取手机验证码
     *
     * @param phone 手机号
     * @return 业务结果对象
     */
    ReturnResult getPhoneMessage(String phone);

    /**
     * 登录
     *
     * @param verificationCode 输入验证码
     * @param phone            手机号
     * @return 业务结果对象
     */
    ReturnResult userLogin(String verificationCode, String phone);

    User insertUser(User user);

    /**
     * 修改用户信息
     *
     * @param userId   用户id
     * @param nickname 昵称
     * @param gender   性别
     * @param region   地区
     * @param profile  简介
     * @param avatar   头像
     * @return 业务结果对象
     */
    ReturnResult modifyUserInfo(String userId, String nickname, String gender, String region, String profile, String avatar);

    /**
     * 获取用户关注信息
     *
     * @param userId 用户id
     * @return 业务结果对象
     */
    ReturnResult getUserFollow(String userId);

}
