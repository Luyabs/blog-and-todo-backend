package com.example.blogandtodo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.blogandtodo.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface UserService extends IService<User> {
    Map<String, Object> login(String code);  // 登录

    String editAvatar(MultipartFile avatar);    // 修改头像

    User parseToken();

    boolean editNickname(String nickName);
}
