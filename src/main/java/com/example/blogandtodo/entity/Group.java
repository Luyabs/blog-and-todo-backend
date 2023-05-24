package com.example.blogandtodo.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@ApiModel(value = "群组", description = "群组")
@TableName("t_group")
public class Group {
    @ApiModelProperty("群id")
    @TableId(type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("群组名")
    private String groupName;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("群组简介")
    private String description;

    @ApiModelProperty("群组人数")
    private Integer memberNum;

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
