package com.xwin.dao.daoImpl;

import com.xwin.pojo.Collect;
import com.xwin.pojo.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikesDao extends JpaRepository<Likes, String> {

    int countByLikeId(Long postId);

    Likes findByUserIdAndLikeId(Long userId, Long abbrId);

    @Query(value = "select l from Likes l where l.userId = :userId order by l.createTime desc")
    List<Likes> findByUserId(@Param("userId") Long userId);
}
