package com.xwin.service;

import com.xwin.common.utils.ReturnResult;

public interface CollectService {

    /**
     * 获取用户收藏
     *
     * @param userId 用户id
     * @return 业务结果对象
     */
    ReturnResult getUserCollection(Long userId);

    /**
     * 取消用户收藏
     *
     * @param userId 用户id
     * @param userId 词条id
     * @return 业务结果对象
     */
    ReturnResult removeUserCollection(Long userId, Long entryId);

    /**
     * 收藏词条
     *
     * @param userId 用户id
     * @param abbrId 词条id
     * @return 业务结果对象
     */
    ReturnResult collectAbbr(Long userId, Long abbrId);
}
