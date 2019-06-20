package com.xwin.dao.daoImpl;

import com.xwin.pojo.HotSearch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SearchDao extends JpaRepository<HotSearch, Long> {
}
