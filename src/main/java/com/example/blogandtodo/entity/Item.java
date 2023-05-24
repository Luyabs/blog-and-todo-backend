package com.example.blogandtodo.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@ApiModel(value = "Todo事项", description = "是个人事项和群组事项的父类")
public class Item {
    @ApiModelProperty("事项id")
    @TableId(type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("事项内容")
    private String content;

    @ApiModelProperty("标签")
    private String tag;

    @ApiModelProperty("类别 0: 个人事项 1: 群组事项")
    private Integer type;

    @ApiModelProperty("是否完成")
    private Boolean isDone;

    @ApiModelProperty("是否需要今日完成")
    private Boolean isToday;

    @ApiModelProperty("计划中完成时间")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime scheduledTime;

    @ApiModelProperty("实际完成时间")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime finishTime;

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
