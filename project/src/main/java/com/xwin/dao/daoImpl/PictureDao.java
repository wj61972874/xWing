package com.xwin.dao.daoImpl;

import com.xwin.pojo.Abbreviation;
import com.xwin.pojo.Collect;
import com.xwin.pojo.Image;
import com.xwin.pojo.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PictureDao extends JpaRepository<Image, Long> {

    @Query(value = "SELECT * FROM image WHERE  type=?1",nativeQuery = true)
    public List<Image> findAllByType(long type);

    @Query(value = "SELECT * FROM image WHERE id=?1",nativeQuery = true)
    public Image getImageById(Long entryId);

    List<Image> findByAbbreviationId(Long abbrId);
}
