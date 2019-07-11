package com.xwin.service.serviceImpl;

import com.xwin.common.utils.RetCode;
import com.xwin.common.utils.ReturnResult;
import com.xwin.dao.daoImpl.CommentDao;
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

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentDao commentDao;

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

        Comment comment = new Comment();
        comment.setDataStatus(1l);
        comment.setCreateTime(new Date());
        comment.setLastUpdateTime(new Date());
        comment.setContent(content);
        comment.setUserId(userId);
        comment.setRate(rate);
        comment.setPostId(abbrId);
        commentDao.save(comment);

        return ReturnResult.build(RetCode.SUCCESS, "success", comment);
    }
}
