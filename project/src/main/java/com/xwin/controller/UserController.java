package com.xwin.controller;

import com.xwin.common.utils.ReturnResult;
import com.xwin.pojo.Login;
import com.xwin.pojo.User;
import com.xwin.service.PictureService;
import com.xwin.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/user")
@Api("Sundae相关的User Api")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PictureService pictureService;

    Login loginInfo = new Login();

    @ApiOperation(value = "发送手机号获取验证码", notes = "发送手机号获取验证码1")
    @ApiImplicitParam(name = "phone", value = "手机号", paramType = "path", required = true, dataType = "String")
    @RequestMapping(value = "/getPhoneMessage", method = RequestMethod.POST)
    public ReturnResult getPhoneMessage(@RequestParam(value = "phone") String phone) {
        return userService.getPhoneMessage(phone);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ReturnResult userLogin(
            @RequestParam(value = "verificationCode") String verificationCode,
            @RequestParam(value = "phone") String phone) {
        return userService.userLogin(verificationCode, phone);
    }


    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    public ReturnResult updateUser(User user, MultipartFile uploadFile) {
        pictureService.uploadPicture(uploadFile);
        return null;
    }

    @RequestMapping(value = "/modify/{userId}", method = RequestMethod.POST)
    public ReturnResult modifyUserInfo(
            @PathVariable(value = "userId") Long userId,
            @RequestParam(value = "nickname", required = false) String nickname,
            @RequestParam(value = "gender", required = false) String gender,
            @RequestParam(value = "region", required = false) String region,
            @RequestParam(value = "profile", required = false) String profile,
            @RequestParam(value = "avatar", required = false) String avatar) {
        return userService.modifyUserInfo(userId, nickname, gender, region, profile, avatar);
    }

}
