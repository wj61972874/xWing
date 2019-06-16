package com.xwin.dao.daoImpl;

import com.xwin.pojo.Abbreviation;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AbbreviationDao extends JpaRepository<Abbreviation, Long> {

    @Query(value = "select abb from Abbreviation abb where abb.id=?1")
    public Abbreviation getAbbreviationDetail(Long id);

    int countByUserId(Long userId);

    @Query(value = "select abbr from Abbreviation abbr where abbr.userId = :userId and abbr.dataStatus = 1 order by abbr.createTime desc")
    List<Abbreviation> getAbbreviationByUserId(Long userId);

    List<Abbreviation> findByUserId(Long userId);
}
