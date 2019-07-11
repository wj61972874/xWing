package com.xwin.dao.daoImpl;

import com.xwin.pojo.Comment;
import com.xwin.pojo.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentDao extends JpaRepository<Comment, Long> {

    @Query(value = "select new com.xwin.pojo.CommentResult(c.id, c.userId, c.postId, c.rate, c.content, u.avatarUrl, u.nickname, c.lastUpdateTime) from Comment c left join User u on c.userId = u.id where c.postId =:abbrId and c.dataStatus = 1 order by c.createTime desc")
    Page<Comment> findByAbbrId(@Param("abbrId") Long abbrId, Pageable pageable);
}
