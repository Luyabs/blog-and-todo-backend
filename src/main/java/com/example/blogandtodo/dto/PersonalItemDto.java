package com.example.blogandtodo.dto;

import com.example.blogandtodo.entity.Item;
import com.example.blogandtodo.entity.ItemNote;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ApiModel(value = "个人事项Dto", description = "继承item的个人事项")
public class PersonalItemDto extends Item {
    @ApiModelProperty("所属的博客id")
    private Integer blogId;

    @ApiModelProperty("可见性 0: 公开 1: 私有")
    private Integer visibility;

    @ApiModelProperty("笔记")
    private ItemNote itemNote;
}
