package com.example.blogandtodo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.blogandtodo.common.JwtUtils;
import com.example.blogandtodo.common.Result;
import com.example.blogandtodo.common.UserInfo;
import com.example.blogandtodo.common.exception.ServiceException;
import com.example.blogandtodo.entity.User;
import com.example.blogandtodo.mapper.UserMapper;
import com.example.blogandtodo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Value("${wechat.appid}")
    private String appid;  // 小程序id

    @Value("${wechat.secret}")
    private String secret;  // 小程序密钥

    @Value("${base-url.avatar}")
    private String avatarBaseUrl;  // 头像所在位置

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserMapper userMapper;

    @Override
    public Map<String, Object> login(String code) {
        // 请求微信用户open_id
        String url = "https://api.weixin.qq.com/sns/jscode2session" +
                "?appid=" + appid + "&secret=" + secret + "&js_code=" + code + "&grant_type=authorization_code";
        Map map = restTemplate.getForObject(url, Map.class);
        String wxId = (String) map.get("openid");

        Map<String, Object> res = new HashMap<>();
        if (map.containsKey("openid")) {
            User user = userMapper.selectOne(new QueryWrapper<User>().eq("wx_id", wxId));
            if (user == null) {     // 未登录过则记录新用户
                user = new User();
                user.setWxId(wxId);
                userMapper.insert(user);
            }
            res.put("user", user);  // 返回用户数据
            res.put("token", JwtUtils.generateToken(user.getId().toString()));  //
            return res; // 用表中主键id生成token
        }
        throw new ServiceException(map.toString());
    }

    @Override
    public User parseToken() {
        return userMapper.selectById(UserInfo.get());
    }

    @Transactional
    @Override
    public String editAvatar(MultipartFile avatar) {
        new File(avatarBaseUrl).mkdirs();  // 没有文件夹就创一个

        String originFileName = avatar.getOriginalFilename();   // 原文件名
        String newFilePath;     // 新文件名(磁盘中)
        String newFileUrl;     // 新文件名(给外部访问用的路径)

        try {
            if (originFileName == null)
                throw new ServiceException("文件名错误");
            int index = originFileName.lastIndexOf('.');
            String suffix = originFileName.substring(index + 1);
            if (index <= 0 || (!suffix.equals("jpg") && !suffix.equals("png")))
                throw new ServiceException("头像格式错误(只能为jpg或png)");
            newFilePath = avatarBaseUrl + userMapper.selectById(UserInfo.get()).getWxId() + "." + suffix;
            File avatarFile = new File(newFilePath);
            avatar.transferTo(avatarFile);

            newFileUrl = "/avatar/" + userMapper.selectById(UserInfo.get()).getWxId() + "." + suffix;
            boolean res = userMapper.updateById(new User().setId(UserInfo.get()).setAvatar(newFileUrl)) > 0;
            if (!res)
                throw new ServiceException("设置头像失败(插入表时异常)");
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new ServiceException(ex.getMessage());
        }
        return newFileUrl;
    }

    @Override
    public boolean editNickname(String nickName) {
        User user = userMapper.selectById(UserInfo.get());
        return userMapper.updateById(user.setWxName(nickName)) > 0;
    }
}
