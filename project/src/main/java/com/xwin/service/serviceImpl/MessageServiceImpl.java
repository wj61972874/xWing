package com.xwin.service.serviceImpl;

import com.xwin.common.utils.RetCode;
import com.xwin.common.utils.ReturnResult;
import com.xwin.dao.daoImpl.MessageDao;
import com.xwin.dao.daoImpl.UserDao;
import com.xwin.pojo.Follow;
import com.xwin.pojo.Message;
import com.xwin.pojo.User;
import com.xwin.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private MessageDao messageDao;

    @Override
    public ReturnResult removeMessage(Long userId, Long messageId) {
        Optional<User> userById = userDao.findById(userId);

        if (userById.equals(Optional.empty())) {
            return ReturnResult.build(RetCode.FAIL, "用户不存在");
        }

        Message message = messageDao.findById(messageId).get();
        if (!message.getUserId().equals(userId)) {
            return ReturnResult.build(RetCode.PARAMS_INVALID, "Invalid user to operate", "");
        }
        message.setDataStatus(0l);
        message.setLastUpdateTime(new Date());
        messageDao.save(message);
        return ReturnResult.build(RetCode.SUCCESS, "success", message);
    }

    @Override
    public ReturnResult getMessageList(Long userId, int page) {
        // 获取用户
        Optional<User> userById = userDao.findById(userId);
        if (userById.equals(Optional.empty())) {
            return ReturnResult.build(RetCode.FAIL, "用户不存在");
        }
        Pageable pageable = PageRequest.of(page,10);
        // 获取用户的消息集合
        Page<Message> messageList = messageDao.findByUserId(userId, pageable);
        return ReturnResult.build(RetCode.SUCCESS, "success", messageList);
    }

    @Override
    public ReturnResult readMessage(Long userId, Long messageId) {
        Optional<User> userById = userDao.findById(userId);

        if (userById.equals(Optional.empty())) {
            return ReturnResult.build(RetCode.FAIL, "用户不存在");
        }

        Message message = messageDao.findById(messageId).get();
        if (!message.getUserId().equals(userId)) {
            return ReturnResult.build(RetCode.PARAMS_INVALID, "Invalid user to operate", "");
        }
        message.setReadFlag(1l);
        message.setLastUpdateTime(new Date());
        messageDao.save(message);
        return ReturnResult.build(RetCode.SUCCESS, "success", message);
    }

    @Override
    public Message createMessage(Long userId, Long type, String content) {
        Message message = new Message();

        message.setDataStatus(1l);
        message.setReadFlag(0l);
        message.setCreateTime(new Date());
        message.setLastUpdateTime(new Date());
        message.setType(type);
        message.setContent(content);
        message.setUserId(userId);
        messageDao.save(message);
        return message;
    }
}
