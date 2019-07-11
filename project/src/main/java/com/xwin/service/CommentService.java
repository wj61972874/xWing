package com.xwin.service;

import com.xwin.common.utils.ReturnResult;

public interface CommentService {
    ReturnResult getCommentList(Long abbrId, int page);

    ReturnResult getCommentListTwo(Long abbrId);

    ReturnResult post(Long userId, Long abbrId, Float rate, String content);
}