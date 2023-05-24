package com.example.blogandtodo.dto;

import com.example.blogandtodo.entity.Item;
import com.example.blogandtodo.entity.ItemNote;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ApiModel(value = "群组事项Dto", description = "继承item的群组事项")
public class GroupItemDto extends Item {
    @ApiModelProperty("所属的群组id")
    private Integer groupId;

    @ApiModelProperty("编辑次数")
    private Integer editTime;

    @ApiModelProperty("笔记")
    private ItemNote itemNote;
}
