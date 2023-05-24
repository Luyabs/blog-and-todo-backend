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
@ApiModel(value = "个人Todo事项", description = "是Item的逻辑子类")
public class PersonalItem {
    @ApiModelProperty("个人事项id")
    @TableId(type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("事项id")
    private Integer itemId;

    @ApiModelProperty("博客id")
    private Integer blogId;

    @ApiModelProperty("用户id")
    private Integer userId;

    @ApiModelProperty("可见性 0: 公开 1: 私有")
    private Integer visibility;

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
