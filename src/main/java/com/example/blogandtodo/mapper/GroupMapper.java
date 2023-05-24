package com.example.blogandtodo.mapper;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.blogandtodo.dto.BlogDto;
import com.example.blogandtodo.dto.GroupDto;
import com.example.blogandtodo.entity.BlogRecord;
import com.example.blogandtodo.entity.Group;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface GroupMapper extends BaseMapper<Group> {

    /**
     * ResultMap: groupWithUsers
     * ResultMap: 按用户id映射到group
     * 分页获取个任何BlogDto
     */
    @Results(id = "groupWithUsers", value = {
            @Result(
                    column = "id_mapping", property = "groupMembers", javaType = List.class,
                    many = @Many(select = "com.example.blogandtodo.mapper.UserMapper.selectByGroupId")
            )
    })
    @Select(
            """
            select id, id id_mapping, group_name, password, description, member_num, create_time, update_time
            from t_group
            ${ew.customSqlSegment}
            """)
    IPage<GroupDto> selectAllPageDTO(Page<Object> objectPage, @Param(Constants.WRAPPER) QueryWrapper<GroupDto> queryWrapper);


    @ResultMap("groupWithUsers")
    @Select(
            """
            <script>
            select g.id, g.id id_mapping, group_name, password, description, member_num, g.create_time, g.update_time
            from t_group g
            join relation_user_group on group_id = g.id
            where user_id = #{user_id}
            <if test="group_name != null and group_name != ''">
                and group_name like ('%' || #{group_name} || '%')
            </if>
            <if test="description != null and description != ''">
                and description like ('%' ||  #{description} || '%')
            </if>
            order by g.update_time
            </script>
            """)
    IPage<GroupDto> selectMyPageDTO(Page<Object> objectPage, @Param("user_id") int userId, @Param("group_name") String groupName, @Param("description") String description);

    @ResultMap("groupWithUsers")
    @Select(
            """
            select id, id id_mapping, group_name, password, description, member_num, create_time, update_time
            from t_group g
            where g.id = #{group_id}
            order by g.update_time
            """)
    GroupDto selectByIdDto(@Param("group_id") int groupId);
}
