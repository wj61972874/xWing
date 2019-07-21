package com.xwin.dao.daoImpl;

import com.xwin.pojo.Likes;
import com.xwin.pojo.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

@Repository
public interface MessageDao extends JpaRepository<Message, Long> {
    @Query(value = "select m from Message m where m.userId = :userId and m.dataStatus = 1 order by m.createTime desc")
//    List<Message> findByUserId(@Param("userId") Long userId);
    Page<Message> findByUserId(@Param("userId") Long userId, Pageable pageable);

    @Query(value = "select * from message where user_id =:userId  and data_status = 1 order by create_time desc limit :pos,1", nativeQuery = true)
    Message findByPosition(@Param("userId") Long userId, @Param("pos") Long pos);

    @Transactional
    @Modifying
    @Query(value = "Update message set read_flag = 1 where read_flag =0 and data_status=1 and user_id = :userId", nativeQuery = true)
    int readAllMessage(@Param("userId") Long userId);

    @Query(value = "select count(*) from message where user_id = :userId  and data_status = 1 and read_flag = 0", nativeQuery = true)
    int checkHasUnread(@Param("userId") Long userId);
}

