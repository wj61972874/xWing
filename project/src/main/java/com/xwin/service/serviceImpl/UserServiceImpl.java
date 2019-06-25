package com.xwin.service.serviceImpl;

import com.xwin.common.GetPhoneMessage;
import com.xwin.common.utils.*;
import com.xwin.dao.daoImpl.*;
import com.xwin.pojo.*;
import com.xwin.service.MessageService;
import com.xwin.service.PictureService;
import com.xwin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private FollowDao followDao;

    @Autowired
    private AbbreviationDao abbreviationDao;

    @Autowired
    private LikesDao likesDao;

    @Autowired
    private CollectDao collectDao;

    @Autowired
    private PictureDao pictureDao;

    @Autowired
    private PictureService pictureService;

    @Autowired
    private MessageService messageService;

    @Value("${IMAGE_LOCAL_URL}")
    private String IMAGE_LOCAL_URL;

    @Override
    public ReturnResult getPhoneMessage(String phone) {

//        String sendResult = GetPhoneMessage.getPram(phoneNumber);
//        String randNum = GetPhoneMessage.randNum;
        String randNum = "111111";
        User user = userDao.findByUsername(phone);
        if (user == null) {
            user = new User();
            Date now = new Date();
            user.setId(IDUtils.genItemId());
            user.setNickname(phone + "_" + now.getYear());
            user.setCreateTime(now);
            user.setDataStatus(1L);
            user.setLastUpdateTime(now);
            user.setUsername(phone);
            user.setVerificationCode(randNum);
            userDao.save(user);

            String username = userDao.findById(user.getId()).get().getNickname();
            String op = "亲爱的" + username + "，欢迎来到Sundae，有你的加入我们的故事一定会更精彩哒！";
            String messageContent = op;

            Message result = messageService.createMessage(user.getId(), Constant.MASSAGE_TYPE_SYSTEM, messageContent);

            if (result != null) {
                return ReturnResult.build(RetCode.SUCCESS, "success");
            } else {
                return ReturnResult.build(RetCode.FAIL, "failure");
            }
        } else {
            user.setVerificationCode(randNum);
            userDao.save(user);
        }

        Map<String, Object> resultMap = new HashMap<>(1, 1);
        resultMap.put("code", randNum);
        return ReturnResult.build(RetCode.SUCCESS, "success", resultMap);
    }

    @Override
    public ReturnResult userLogin(String verificationCode, String phone) {

        //  获取用户
        User user = userDao.findByUsername(phone);

        // 获取用户验证码
        String code = user.getVerificationCode();

        // 验证码为空返回错误信息
        if (verificationCode == null) {
            return ReturnResult.build(RetCode.FAIL, "此用户没有发送验证码");
        }

        // 验证码验证成功
        if (code.equals(verificationCode)) {
            Date now = new Date();
            user.setLastLoginTime(now);
            user.setLastUpdateTime(now);
            return ReturnResult.build(RetCode.SUCCESS, "success", user);
        }

        return ReturnResult.build(RetCode.FAIL, "验证码输入错误");
    }

    public void addAbbToMap(Abbreviation abb, Explore explore, String desc, User followedUser, Date eventTime) {
        explore.setEvent_desc(desc);
        explore.setItem_name(abb.getAbbrName());
        explore.setItem_content(abb.getContent());
        explore.setId(abb.getId());
        List<Image> imagesList = abb.getImageList();

        if (!imagesList.isEmpty()) {
            Image image = imagesList.get(0);
            explore.setItem_image(image.getPath());
        }

        String convertedDate = RelativeDateFormat.format(eventTime);
        explore.setEvent_time(convertedDate);
        explore.setItem_date(eventTime);

        explore.setItem_username(followedUser.getNickname());
        explore.setItem_avatar(followedUser.getAvatarUrl());
    }

    @Override
    public ReturnResult getExploreList(Long userId) {

        Optional<User> userById = userDao.findById(userId);
        if (userById.equals(Optional.empty())) {
            return ReturnResult.build(RetCode.FAIL, "用户不存在");
        }

        List<Follow> follows = followDao.getUserFollow(userId);

        List<Explore> result = new ArrayList<>();

        for (Follow follow : follows) {
            // 被关注用户
            User followedUser = userDao.findById(follow.getFollowedUserId()).get();

            Map<String, Object> unitMap;

            // 获取被关注人发布的post
            List<Abbreviation> abbs = abbreviationDao.findByUserId(follow.getFollowedUserId());
            List<Likes> likes = likesDao.findByUserId(follow.getFollowedUserId());
            List<Collect> collects = collectDao.findByUserId(follow.getFollowedUserId());

            String abbrType = "";
            for (Abbreviation abb : abbs) {
                Explore explore = new Explore();
                if (abb.getType() == 0l) {
                    abbrType = "词条";
                } else if (abb.getType() == 1l) {
                    abbrType = "心得";
                }
                addAbbToMap(abb, explore, "发布了新的" + abbrType + "，快来学习一下吧。", followedUser, abb.getCreateTime());
                result.add(explore);
            }

            for (Likes like : likes) {
                Explore explore = new Explore();
                Abbreviation abb = abbreviationDao.findById(like.getLikeId()).get();
                if (abb.getType() == 0l) {
                    abbrType = "词条";
                } else if (abb.getType() == 1l) {
                    abbrType = "心得";
                }
                addAbbToMap(abb, explore, "点赞了这个" + abbrType + "，一起来看看。", followedUser, like.getCreateTime());
                result.add(explore);
            }

            for (Collect collect : collects) {
                Explore explore = new Explore();
                Abbreviation abb = abbreviationDao.findById(collect.getEntryId()).get();
                if (abb.getType() == 0l) {
                    abbrType = "词条";
                } else if (abb.getType() == 1l) {
                    abbrType = "心得";
                }
                addAbbToMap(abb, explore, "收藏了一个" + abbrType + "，到底有什么秘密？", followedUser, collect.getCreateTime());
                result.add(explore);
            }
            result.sort(new Comparator<Explore>() {
                public int compare(Explore o1, Explore o2) {
                    Date i1 = o1.getItem_date();
                    Date i2 = o2.getItem_date();
                    return i2.compareTo(i1);
                }
            });
        }
        return ReturnResult.build(RetCode.SUCCESS, "success", result);
    }

    public User insertUser(User user) {
        user.setLastLoginTime(new Date());
        user.setCreateTime(new Date());
        List<User> userList = userDao.findAll();
        User loginUser = new User();
        List usernameList = new ArrayList();
        int index = -1;
        if (userList.size() == 0) {
            userDao.save(user);
            loginUser = user;
        } else {
            for (int i = 0; i < userList.size(); i++) {
                usernameList.add(userList.get(i).getUsername());
                if (usernameList.contains(user.getUsername())) {
                    index = i;
                }
            }
            if (index != -1) {
                loginUser = userList.get(index);
            } else {
                User user1 = userDao.save(user);
                loginUser = user1;
            }
        }
        return loginUser;
    }

    @Override
    public ReturnResult modifyUserInfo(Long userId, String nickname, String gender, String region, String profile, String avatar) {

        // 获取用户
        Optional<User> userById = userDao.findById(userId);
        if (userById.equals(Optional.empty())) {
            return ReturnResult.build(RetCode.FAIL, "用户不存在");
        }
        User user = userById.get();

        // 修改用户信息
        if (nickname != null) {
            user.setNickname(nickname);
        }

        if (gender != null) {
            user.setGender(gender);
        }

        if (region != null) {
            user.setRegion(region);
        }

        if (profile != null) {
            user.setProfile(profile);
        }

        if (avatar != null) {
            String url = pictureService.uploadImage(avatar, userId, "user");
            user.setAvatarUrl(url);
        }

        // 数据持久化
        userDao.save(user);

        return ReturnResult.build(RetCode.SUCCESS, "success", user);
    }


}
