package com.xwin.service.serviceImpl;

import com.xwin.common.utils.DateUtils;
import com.xwin.common.utils.FtpUtil;
import com.xwin.common.utils.IDUtils;
import com.xwin.dao.daoImpl.AbbreviationDao;
import com.xwin.dao.daoImpl.PictureDao;
import com.xwin.dao.daoImpl.UserDao;
import com.xwin.pojo.Abbreviation;
import com.xwin.pojo.Image;
import com.xwin.pojo.User;
import com.xwin.service.PictureService;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class PictureServiceImpl implements PictureService {

    @Autowired
    private PictureDao pictureDao;

    @Autowired
    private AbbreviationDao abbreviationDao;

    @Autowired
    private UserDao userDao;

    @Value("${FTP_ADDRESS}")
    private String FTP_ADDRESS;
    @Value("${FTP_PORT}")
    private Integer FTP_PORT;
    @Value("${FTP_USERNAME}")
    private String FTP_USERNAME;
    @Value("${FTP_PASSWORD}")
    private String FTP_PASSWORD;
    @Value("${FTP_BASE_PATH}")
    private String FTP_BASE_PATH;
    @Value("${IMAGE_BASE_URL}")
    private String IMAGE_BASE_URL;
    @Value("${IMAGE_LOCAL_URL}")
    private String IMAGE_LOCAL_URL;

    public Map uploadPicture(MultipartFile uploadFile) {
        Map resultMap = new HashMap<Object, Object>();
        try {
            //生成一个新的文件名
            //取原始文件名
            String oldName = uploadFile.getOriginalFilename();
            //生成新文件名
            //UUID.randomUUID();
            String newName = IDUtils.genImageName();
            newName = newName + oldName.substring(oldName.lastIndexOf("."));
            //图片上传
            String imagePath = new DateTime().toString("/yyyy/MM/dd");
            boolean result = FtpUtil.uploadFile(FTP_ADDRESS, FTP_PORT, FTP_USERNAME, FTP_PASSWORD,
                    FTP_BASE_PATH, imagePath, newName, uploadFile.getInputStream());
            //返回结果
            if (!result) {
                resultMap.put("error", 1);
                resultMap.put("message", "文件上传失败");
                return resultMap;
            }
            resultMap.put("error", 0);
            resultMap.put("url", IMAGE_BASE_URL + imagePath + "/" + newName);
            return resultMap;

        } catch (Exception e) {
            resultMap.put("error", 1);
            resultMap.put("message", "文件上传发生异常");
            return resultMap;
        }
    }

    @Override
    public List<Image> getAllImagesByType(long type) {
        Date weekBeginTime = DateUtils.getBeginDayOfWeek();
        Date weekEndTime = DateUtils.getEndDayOfWeek();
        return pictureDao.findAllByType(type);
    }

    @Override
    public Image getImageById(Long entryId) {

        return pictureDao.getImageById(entryId);
    }

    public String uploadImage(String base64Data, Long abbrId, String type) {
        try {
            String dataPrix = "";
            String data = "";
            if (base64Data == null || "".equals(base64Data)) {
                throw new Exception("上传失败，上传图片数据为空");
            } else {
                String[] d = base64Data.split("base64,");
                if (d != null && d.length == 2) {
                    dataPrix = d[0];
                    data = d[1];
                } else {
//                    throw new Exception("上传失败，数据不合法");
                    data = d[0];
                }
            }
            String tempFileName = UUID.randomUUID().toString() + ".jpg";
            String parentPath = IMAGE_LOCAL_URL + type + "/" + abbrId;
            byte[] bs = Base64.decodeBase64(data);
            try {
                //使用apache提供的工具类操作流
                FileUtils.writeByteArrayToFile(new File(parentPath, tempFileName), bs);
            } catch (Exception ee) {
                throw new Exception("上传失败，写入文件失败，" + ee.getMessage());
            }
            Date date = new Date();
            String imageServerUrl = IMAGE_BASE_URL;
            String imgUrl = imageServerUrl + type + "/" + abbrId + "/" + tempFileName;
            Image image = new Image();
            image.setPath(imgUrl);

            if (type == "abbr") {
                image.setAbbreviationId(abbreviationDao.findById(abbrId).get());
                image.setType(1L);
            } else {
                User user = userDao.findById(abbrId).get();
                String url = IMAGE_LOCAL_URL.replace("/images/", "") + user.getAvatarUrl();

                File file = new File(url);
                if (file.exists() && file.isFile())
                    file.delete();

                image.setType(2L);
            }

            image.setCreateTime(date);
            pictureDao.save(image);
            return imgUrl;
        } catch (Exception e) {
            e.printStackTrace();
            return "failure";
        }
    }
}
