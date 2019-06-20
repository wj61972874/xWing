package com.xwin.service;

import com.xwin.pojo.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface PictureService {

	Map uploadPicture(MultipartFile uploadFile);

	public List<Image> getAllImagesByType(long type);

    public Image getImageById(Long entryId);

	public String uploadImage(String base64Data, Long abbrId, String type);
}
