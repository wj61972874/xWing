package com.xwin.dao.daoImpl;

import com.xwin.pojo.Collect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollectDao extends JpaRepository<Collect, Long> {

    @Query(value = "select c from Collect c where c.userId = :userId and c.dataStatus = 1 order by c.createTime desc")
    List<Collect> findByUserId(@Param("userId") Long userId);

    @Query(value = "select c from Collect c where c.userId = :userId and c.entryId =:entryId order by c.createTime desc")
    Collect findByUserIdAndEntryId(@Param("userId") Long userId,@Param("entryId") Long entryId);

}
