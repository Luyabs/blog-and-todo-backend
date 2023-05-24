package com.example.blogandtodo.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@ApiModel(value = "用户", description = "用户/管理员")
@TableName("t_user")
public class User {
    @ApiModelProperty("用户id")
    @TableId(type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("微信id")
    private String wxId;

    @ApiModelProperty("微信名")
    private String wxName;

    @ApiModelProperty("头像所在url")
    private String avatar;

    @ApiModelProperty("身份 0: 管理员 1: 普通用户")
    private Integer authority;

    @ApiModelProperty("是否被封禁")
    private Boolean isBanned;

    @ApiModelProperty("个人简介")
    private String description;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("性别")
    private String gender;

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
