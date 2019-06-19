package com.xwin.dao.daoImpl;

import com.xwin.pojo.Likes;
import com.xwin.pojo.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository
public interface MessageDao extends JpaRepository<Message, String> {
    @Query(value = "select m from Message m where m.userId = :userId order by m.createTime desc")
//    List<Message> findByUserId(@Param("userId") Long userId);
    Page<Message> findByUserId(@Param("userId") Long userId, Pageable pageable);
}

