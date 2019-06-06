package com.xwin.service.serviceImpl;

import com.xwin.common.GetPhoneMessage;
import com.xwin.common.utils.ObjectUtil;
import com.xwin.dao.daoImpl.UserDao;
import com.xwin.pojo.Login;
import com.xwin.pojo.User;
import com.xwin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public String getPhoneMessage(String phoneNumber) {

        String sendResult = GetPhoneMessage.getPram(phoneNumber);
        System.out.println(sendResult);
        return GetPhoneMessage.randNum;
    }

    @Override
    public boolean userLogin(String identifyingCode, String phoneNum, Login loginInfo) {
        int randNum = Integer.parseInt(loginInfo.getSendRandNum());
        int code = Integer.parseInt(identifyingCode);
        if (randNum == code && phoneNum.compareTo(loginInfo.getPhoneNumber())==0) {
            return true;
        } else {
            return false;
        }
    }

    public User insertUser(User user) {
        user.setLastLoginTime(new Date());
        user.setCreateTime(new Date());
        List<User> userList = userDao.findAll();
        User loginUser = new User();
        List usernameList = new ArrayList();
        int index=-1;
        if (userList.size() == 0) {
            userDao.save(user);
            loginUser = user;
        }else{
            for (int i = 0; i < userList.size(); i++) {
                usernameList.add(userList.get(i).getUsername());
                if(usernameList.contains(user.getUsername())){
                    index=i;
                }
            }
            if (index!=-1) {
                loginUser=userList.get(index);
            } else {
                User user1=userDao.save(user);
                loginUser=user1;
            }
        }
        return loginUser;
    }
}
