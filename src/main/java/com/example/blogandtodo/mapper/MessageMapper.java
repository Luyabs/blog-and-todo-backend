package com.example.blogandtodo.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.blogandtodo.entity.Item;
import com.example.blogandtodo.entity.Message;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MessageMapper extends BaseMapper<Message> {

}
