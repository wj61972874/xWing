package com.xwin.service.serviceImpl;

import com.xwin.common.utils.RetCode;
import com.xwin.common.utils.ReturnResult;
import com.xwin.dao.daoImpl.FollowDao;
import com.xwin.pojo.Follow;
import com.xwin.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class FollowServiceImpl implements FollowService {

    @Autowired
    private FollowDao followDao;

    @Override
    public ReturnResult removeFollow(Long userId, Long followedUserId) {

        // 获取关注
        Follow follow = followDao.findByUserIdAndFollowedUserId(userId, followedUserId);
        if (follow != null) {
            follow.setDataStatus(0L);
            follow.setLastUpdateTime(new Date());
        }

        // 数据持久化
        followDao.save(follow);
        return ReturnResult.build(RetCode.SUCCESS, "success");
    }
}
