package com.xwin.dao.daoImpl;

import com.xwin.pojo.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikesDao extends JpaRepository<Likes, String> {

    int countByLikeId(Long postId);

    Likes findByUserIdAndLikeId(Long userId, Long abbrId);
}
