package com.xwin.dao.daoImpl;

import com.xwin.pojo.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowDao extends JpaRepository<Follow, Long> {

    @Query(value = "select f from Follow f where f.userId = :userId and f.dataStatus = 1 order by f.lastUpdateTime desc ")
    List<Follow> getUserFollow(@Param("userId") Long userId);

    Follow findByUserIdAndFollowedUserId(Long userId, Long followedUserId);
}
