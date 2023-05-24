package com.example.blogandtodo.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@ApiModel(value = "消息", description = "私信或通知")
public class Message {
    @ApiModelProperty("消息id")
    @TableId(type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("from用户id")
    private Integer fromUser;

    @ApiModelProperty("to用户id")
    private Integer toUser;

    @ApiModelProperty("信息内容")
    private String content;

    @ApiModelProperty("信息类型 0: 私信 1: 群组 2: 管理员消息")
    private Integer type;

    @ApiModelProperty("是否被用户逻辑删除")
    private Boolean isDeleted;

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
