package com.xwin.controller;

import com.xwin.common.utils.RetCode;
import com.xwin.common.utils.ReturnResult;
import com.xwin.pojo.Login;
import com.xwin.pojo.User;
import com.xwin.service.PictureService;
import com.xwin.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.xwin.common.GetPhoneMessage;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/user")
@Api("Sundae相关的User Api")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PictureService pictureService;

    Login loginInfo=new Login();

    @ApiOperation(value = "发送手机号获取验证码", notes = "发送手机号获取验证码1")
    @ApiImplicitParam(name = "phoneNumber", value = "手机号", paramType = "path", required = true, dataType = "String")
    @RequestMapping(value = "/getPhoneMessage/{phoneNumber}",method = RequestMethod.POST)
    public String getPhoneMessage(@PathVariable() String phoneNumber ){

        loginInfo.setSendRandNum(GetPhoneMessage.randNum);
        //sendRandNum= userService.getPhoneMessage(phoneNumber);
        loginInfo.setPhoneNumber(phoneNumber);
        return loginInfo.getSendRandNum();
    }

    @RequestMapping(value = "/login/{identifyingCode}",method = RequestMethod.POST)
public ReturnResult userLogin(@PathVariable String identifyingCode,String phoneNum){
        Boolean loginAction=userService.userLogin(identifyingCode,phoneNum,loginInfo);
        System.out.println(loginAction);
        if(loginAction){
            User user=new User();
            user.setUsername(loginInfo.getPhoneNumber());
            user.setNickname(loginInfo.getPhoneNumber());
            user=userService.insertUser(user);
            return ReturnResult.build(RetCode.SUCCESS,"success",user);
        }else{
            return ReturnResult.build(RetCode.FAIL,"failure",null);
        }
    }


    @RequestMapping(value = "/updateUser",method = RequestMethod.POST)
    public ReturnResult updateUser(User user, MultipartFile uploadFile){
        pictureService.uploadPicture(uploadFile);
        return null;
    }
}
