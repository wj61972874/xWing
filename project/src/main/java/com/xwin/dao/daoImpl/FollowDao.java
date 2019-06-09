package com.xwin.dao.daoImpl;

import com.xwin.pojo.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowDao extends JpaRepository<Follow, String> {

    @Query(value = "select f from Follow f where f.userId = :userId order by f.createTime desc ")
    List<Follow> getUserFollow(@Param("userId") String userId);
}
