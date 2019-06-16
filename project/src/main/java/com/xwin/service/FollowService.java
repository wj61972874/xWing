package com.xwin.service;

import com.xwin.common.utils.ReturnResult;

public interface FollowService {

    /**
     * 取消关注
     *
     * @param userId         用户id
     * @param followedUserId 被关注用户id
     * @return 业务结果对象
     */
    ReturnResult removeFollow(Long userId, Long followedUserId);

    /**
     * 获取用户关注信息
     *
     * @param userId 用户id
     * @return 业务结果对象
     */
    ReturnResult getUserFollow(Long userId);

    /**
     * 关注用户
     *
     * @param userId         用户id
     * @param followedUserId 被关注用户id
     * @return 业务结果对象
     */
    ReturnResult followUser(Long userId, Long followedUserId);
}
