package com.xwin.service;

import com.xwin.common.utils.ReturnResult;

public interface PublishService {

    /**
     * 获取用户发布
     *
     * @param userId 用户id
     * @return 业务结果对象
     */
    ReturnResult getAllPublish(Long userId);
}
