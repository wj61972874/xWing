package com.xwin.service;

import com.xwin.common.utils.ReturnResult;

public interface MessageService {
    /**
     * 删除消息
     *
     * @param userId         用户id
     * @param messageId      消息id
     * @return 业务结果对象
     */
    ReturnResult removeMessage(Long userId, Long messageId);

    /**
     * 个人消息列表
     *
     * @param userId         用户id
     * @param page           页码
     * @return 业务结果对象
     */
    ReturnResult getMessageList(Long userId, int page);

    /**
     * 标记消息已读
     *
     * @param userId         用户id
     * @param messageId      消息id
     * @return 业务结果对象
     */
    ReturnResult readMessage(Long userId, Long messageId);

    /**
     * 创建消息
     *
     * @param userId         用户id
     * @param type         消息类型
     * @return 业务结果对象
     */
    ReturnResult createMessage(Long userId, String type);

}
