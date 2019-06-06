package com.xwin.dao.daoImpl;

import com.xwin.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, Long> {


}
