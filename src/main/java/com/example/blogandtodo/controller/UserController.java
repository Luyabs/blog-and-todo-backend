package com.example.blogandtodo.controller;

import com.example.blogandtodo.common.Result;
import com.example.blogandtodo.entity.User;
import com.example.blogandtodo.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @ApiOperation(value = "登录", notes = "将得到token与用户信息 通过user.avatar获取头像")
    @PostMapping("/login")
    public Result login(String code) {
        Map<String, Object> map = userService.login(code);
        return Result.success().data(map);
    }

    @ApiOperation(value = "解析token", notes = "将得到用户信息 通过user.avatar获取头像")
    @PostMapping("/parse_token")
    public Result parseToken() {
        User user = userService.parseToken();
        return Result.success().data("user", user);
    }

    @ApiOperation(value = "上传头像", notes = "请以 ip:port/avatar/头像名 方式 以get请求获取头像")
    @PostMapping("/avatar")
    public Result editAvatar(@RequestPart MultipartFile avatar) {
        String url = userService.editAvatar(avatar);
        return url != null ? Result.success().data("avatar", url) : Result.error();
    }

    @ApiOperation(value = "修改用户名", notes = "直接覆盖老用户名")
    @PostMapping("/nickname")
    public Result editNickName(@RequestParam String nickName) {
        boolean res = userService.editNickname(nickName);
        return res ? Result.success().message("用户名修改成功").data("nickName", nickName) : Result.error();
    }
}
