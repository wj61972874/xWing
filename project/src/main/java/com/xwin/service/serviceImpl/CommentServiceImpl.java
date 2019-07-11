package com.xwin.service.serviceImpl;

import com.xwin.common.utils.Constant;
import com.xwin.common.utils.RetCode;
import com.xwin.common.utils.ReturnResult;
import com.xwin.dao.daoImpl.AbbreviationDao;
import com.xwin.dao.daoImpl.CommentDao;
import com.xwin.dao.daoImpl.UserDao;
import com.xwin.pojo.Abbreviation;
import com.xwin.pojo.Comment;
import com.xwin.pojo.Message;
import com.xwin.pojo.User;
import com.xwin.service.CommentService;
import com.xwin.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private AbbreviationDao abbreviationDao;

    @Override
    public ReturnResult getCommentList(Long abbrId, int page) {
        Pageable pageable = PageRequest.of(page,10);
        // 获取用户的消息集合
        Page<Comment> commentList = commentDao.findByAbbrId(abbrId, pageable);
        return ReturnResult.build(RetCode.SUCCESS, "success", commentList);
    }

    @Override
    public ReturnResult getCommentListTwo(Long abbrId) {
        Pageable pageable = PageRequest.of(0,2);
        // 获取用户的消息集合
        Page<Comment> commentList = commentDao.findByAbbrId(abbrId, pageable);
        return ReturnResult.build(RetCode.SUCCESS, "success", commentList);
    }

    @Override
    public ReturnResult post(Long userId, Long abbrId, Float rate, String content) {

        // 用户不存在返回错误信息
        Optional<User> userById = userDao.findById(userId);
        if (userById.equals(Optional.empty())) {
            return ReturnResult.build(RetCode.FAIL, "用户不存在");
        }

        Optional<Abbreviation> abbr = abbreviationDao.findById(abbrId);
        if (abbr.equals(Optional.empty())) {
            return ReturnResult.build(RetCode.FAIL, "词条不存在");
        }

        Comment comment = new Comment();
        comment.setDataStatus(1l);
        comment.setCreateTime(new Date());
        comment.setLastUpdateTime(new Date());
        comment.setContent(content);
        comment.setUserId(userId);
        comment.setRate(rate);
        comment.setPostId(abbrId);
        commentDao.save(comment);

        String username = userDao.findById(userId).get().getNickname();
        String op = "评论了你的大作";

        if (userId.equals(abbr.get().getUserId())) {
            op = "竟然评论了自己的大作";
        }

        String abbrName = abbr.get().getAbbrName();
        String messageContent = username + op + abbrName;

        messageService.createMessage(abbr.get().getUserId(), Constant.MASSAGE_TYPE_COMMENT, messageContent);
        return ReturnResult.build(RetCode.SUCCESS, "success", comment);
    }
}
