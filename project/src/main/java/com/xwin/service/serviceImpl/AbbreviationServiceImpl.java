package com.xwin.service.serviceImpl;

import com.xwin.common.utils.Constant;
import com.xwin.common.utils.IDUtils;
import com.xwin.common.utils.RetCode;
import com.xwin.common.utils.ReturnResult;
import com.xwin.dao.daoImpl.*;
import com.xwin.pojo.*;
import com.xwin.service.AbbreviationService;
import com.xwin.service.MessageService;
import com.xwin.service.PictureService;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

import java.util.Date;

@Service
public class AbbreviationServiceImpl implements AbbreviationService {
    private String baseUrl = "http://localhost:8888/solr/sundae";

    @Autowired
    private AbbreviationDao abbreviationDao;

    @Autowired
    private PictureDao pictureDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private LikesDao likesDao;

    @Autowired
    private CollectDao collectDao;

    @Autowired
    private FollowDao followDao;

    @Autowired
    private PictureService pictureService;

    @Autowired
    private MessageService messageService;

    @Override
    public void getHotNews() {

    }

    @Override
    public ReturnResult getHotSearchResults() {
        List<Abbreviation> list = abbreviationDao.getHotSearchResults();
        return ReturnResult.build(200, "success", list);
    }

    @Override
    public ReturnResult getRecommendedEntryList() {
        List list = new ArrayList();
        list = abbreviationDao.getAllAbbreviation();
//        Abbreviation abbreviation = abbreviationDao.findById(1L).get();
//        List imagelist = abbreviation.getImageList();
//        System.out.println(imagelist);
        return ReturnResult.build(200, "success", list);
    }

    @Override
    public ReturnResult getAbbreviationDetail(Long entryId, Long userId) {

        Abbreviation abbr = abbreviationDao.getAbbreviationDetail(entryId);
        Map<String, Object> map = new HashMap<>(16, .75f);
        map.put("abbreviation", abbr);

        Long authorId = abbr.getUserId();
        User author = userDao.findById(authorId).get();
        map.put("author", author.getNickname());
        map.put("avatar", author.getAvatarUrl());

        if (userId == null) {
            map.put("collect", false);
            map.put("like", false);
            map.put("followed", false);
        } else {
            Optional<User> userById = userDao.findById(userId);

            if (userById.equals(Optional.empty())) {
                return ReturnResult.build(RetCode.FAIL, "用户不存在");
            }

            abbr.getUserId();
            Collect collect = collectDao.findByUserIdAndEntryId(userId, entryId);
            Likes like = likesDao.findByUserIdAndLikeId(userId, entryId);
            Follow follow = followDao.findByUserIdAndFollowedUserId(userId, abbr.getUserId());

            if (collect != null && collect.getDataStatus() == 1L) {
                map.put("collect", true);
            } else {
                map.put("collect", false);
            }

            if (like != null && like.getDataStatus() == 1L) {
                map.put("like", true);
            } else {
                map.put("like", false);
            }

            if (follow != null && follow.getDataStatus() == 1L) {
                map.put("follow", true);
            } else {
                map.put("follow", false);
            }
        }

        return ReturnResult.build(RetCode.SUCCESS, "success", map);
    }

    @Override
    public ReturnResult likeAbbr(Long userId, Long abbrId) {

        // 用户不存在返回错误信息
        Optional<User> userById = userDao.findById(userId);
        if (userById.equals(Optional.empty())) {
            return ReturnResult.build(RetCode.FAIL, "用户不存在");
        }

        // 词条不存在返回错误信息
        Optional<Abbreviation> abbr = abbreviationDao.findById(abbrId);
        if (abbr.equals(Optional.empty())) {
            return ReturnResult.build(RetCode.FAIL, "词条不存在");
        }

        // 获取点赞
        Likes like = likesDao.findByUserIdAndLikeId(userId, abbrId);

        // 已经点赞返回错误结果信息
        if (like != null && like.getDataStatus() == 1L) {
            return ReturnResult.build(RetCode.FAIL, "已经点赞此词条");
        }

        // date
        Date now = new Date();

        // 数据持久化
        if (like == null) {
            like = new Likes();
            like.setId(IDUtils.genItemId());
            like.setCreateTime(now);
            like.setLikeId(abbrId);
            like.setUserId(userId);
        }

        like.setLastUpdateTime(now);
        like.setDataStatus(1L);
        likesDao.save(like);

        // 修改词条点赞数,持久化
        Abbreviation abbreviation = abbr.get();
        abbreviation.setLastUpdateTime(now);
        abbreviation.setLikedCount(abbreviation.getLikedCount() + 1);
        abbreviationDao.save(abbreviation);

        String username = userDao.findById(userId).get().getNickname();
        String op = "爱上了你的分享";
        String abbrName = abbreviationDao.findById(abbrId).get().getAbbrName();
        String messageContent = username + op + abbrName;

        Message result = messageService.createMessage(abbreviation.getUserId(), Constant.MASSAGE_TYPE_LIKE, messageContent);

        if (result != null) {
            return ReturnResult.build(RetCode.SUCCESS, "success");
        } else {
            return ReturnResult.build(RetCode.FAIL, "failure");
        }
    }

    @Override
    public ReturnResult removeLikeAbbr(Long userId, Long abbrId) {

        // 用户不存在返回错误信息
        Optional<User> userById = userDao.findById(userId);
        if (userById.equals(Optional.empty())) {
            return ReturnResult.build(RetCode.FAIL, "用户不存在");
        }

        // 词条不存在返回错误信息
        Optional<Abbreviation> abbr = abbreviationDao.findById(abbrId);
        if (abbr.equals(Optional.empty())) {
            return ReturnResult.build(RetCode.FAIL, "词条不存在");
        }

        // 获取点赞
        Likes like = likesDao.findByUserIdAndLikeId(userId, abbrId);

        if (like == null) {
            return ReturnResult.build(RetCode.FAIL, "没有点赞过此词条");
        }

        // 已经点赞返回错误结果信息
        if (like.getDataStatus() == 0L) {
            return ReturnResult.build(RetCode.FAIL, "已经取消点赞此词条");
        }

        // date
        Date now = new Date();

        // 数据持久化
        like.setLastUpdateTime(now);
        like.setDataStatus(0L);
        likesDao.save(like);

        // 修改词条点赞数,持久化
        Abbreviation abbreviation = abbr.get();
        abbreviation.setLastUpdateTime(now);
        abbreviation.setLikedCount(abbreviation.getLikedCount() - 1);
        abbreviationDao.save(abbreviation);

        return ReturnResult.build(RetCode.SUCCESS, "success");
    }


    @Override
    public int uploadAddr(String id, String userId, String addr, String title, String content, String type, String image1, String image2, String image3) throws IOException, SolrServerException {

        Date date = new Date();

        Abbreviation abbreviation = new Abbreviation();
        //  abbreviation.setId(43l);
        abbreviation.setUserId(Long.parseLong(userId));
        abbreviation.setAbbrName(addr);
        abbreviation.setContent(content);
        if (type != null && type.equals("1")) {
            abbreviation.setFullName(title);
            abbreviation.setType(0l);
        } else {
            abbreviation.setType(1l);
        }

        abbreviation.setCreateTime(date);
        abbreviation.setLastUpdateTime(date);

        abbreviation.setDataStatus(1l);
        abbreviation.setCreateBy(Long.parseLong(userId));
        abbreviation.setLikedCount(0l);
        abbreviation.setVisitedCount(0l);
        abbreviationDao.save(abbreviation);
        pictureService.uploadImage(image1, abbreviation.getId(), "abbr");
        pictureService.uploadImage(image2, abbreviation.getId(), "abbr");
        pictureService.uploadImage(image3, abbreviation.getId(), "abbr");
//        this.insertToSolr(abbreviation);
        return 0;
    }

    @Override
    public void insertToSolr(Abbreviation abbreviation) throws IOException, SolrServerException {
        SolrServer solrServer = new HttpSolrServer(baseUrl);
        SolrInputDocument input = new SolrInputDocument();
        input.addField("id", abbreviation.getId());
        input.addField("abbr_name", abbreviation.getAbbrName());
        input.addField("full_name", abbreviation.getFullName());
        input.addField("content", abbreviation.getContent());
        input.addField("create_time", abbreviation.getCreateTime());
        input.addField("create_by", abbreviation.getCreateBy());
        input.addField("last_update_time", abbreviation.getLastUpdateTime());
        solrServer.add(input);
        System.out.println("添加完成");
        solrServer.commit();
    }

    @Override
    public List<Abbreviation> getAbbreviationByKeyWords(String keyWords) {
        return abbreviationDao.getAbbreviationByKeyWords(keyWords);
    }
}
