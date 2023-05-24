package com.example.blogandtodo.dto;

import com.example.blogandtodo.entity.Blog;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@ApiModel(value = "博客dto", description = "继承自博客 且含有个人事项")
public class BlogDto extends Blog {
    @ApiModelProperty("博客中含有的全部事项")
    List<PersonalItemDto> personalItemDtoList;
}
