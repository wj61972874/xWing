package com.xwin.dao.daoImpl;

import com.xwin.pojo.Abbreviation;
import com.xwin.pojo.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PictureDao extends JpaRepository<Image, Long> {

    @Query(value = "SELECT * FROM image WHERE  type=?1 and create_time >=?2 and create_time <=?3",nativeQuery = true)
    public List<Image> findAllByType(long type, Date weekBeginTime, Date weekEndTime);

    @Query(value = "SELECT * FROM image WHERE id=?1",nativeQuery = true)
    public Image getImageById(String entryId);
}
