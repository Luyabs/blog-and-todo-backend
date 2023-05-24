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
@ApiModel(value = "事项笔记", description = "关于事项的笔记")
public class ItemNote {
    @ApiModelProperty("笔记id")
    @TableId(type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("事项id")
    private Integer itemId;

    @ApiModelProperty("笔记内容(允许笔记内容中出现图片地址)")
    private String content;

    @ApiModelProperty("笔记中图片出现数量")
    private Integer imageCount;

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
