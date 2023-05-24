package com.example.blogandtodo.dto;

import com.example.blogandtodo.entity.Group;
import com.example.blogandtodo.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@ApiModel(value = "群组dto", description = "继承自群组 且含具体群组成员")
public class GroupDto extends Group {
    @ApiModelProperty("群组中的全部成员")
    List<User> groupMembers;
}
