package com.xwin.service;

import com.xwin.common.utils.ReturnResult;

public interface FollowService {

    /**
     * 取消关注
     * @param userId 用户id
     * @param followedUserId 被关注用户id
     * @return 业务结果对象
     */
    ReturnResult removeFollow(Long userId, Long followedUserId);
}
