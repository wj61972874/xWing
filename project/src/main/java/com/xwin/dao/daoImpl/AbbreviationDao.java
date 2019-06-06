package com.xwin.dao.daoImpl;

import com.xwin.pojo.Abbreviation;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AbbreviationDao extends JpaRepository<Abbreviation, String> {

    @Query(value = "select abb from Abbreviation abb where abb.id=?1")
    public Abbreviation getAbbreviationDetail(String id);
}
