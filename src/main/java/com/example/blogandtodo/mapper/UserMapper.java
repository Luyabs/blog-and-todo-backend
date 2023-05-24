package com.example.blogandtodo.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.blogandtodo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    @Select("""
            select u.*
            from t_user u
            join relation_user_group on user_id = u.id
            where group_id = #{group_id}
            """)
    List<User> selectByGroupId(@Param("group_id") int groupId);
}
