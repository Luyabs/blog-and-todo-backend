package com.example.blogandtodo.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.blogandtodo.dto.GroupItemDto;
import com.example.blogandtodo.entity.Group;
import com.example.blogandtodo.entity.GroupItem;
import com.example.blogandtodo.entity.ItemNote;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface GroupItemMapper extends BaseMapper<GroupItem> {

    /**
     * 全部条件获取自己已加入的所有组的任意事项DTO page
     * 可携带条件content tag isDone
     */
    @Results(id = "withNote", value = {
            @Result(
                    column = "id_mapping", property = "itemNote", javaType = ItemNote.class,
                    many = @Many(select = "com.example.blogandtodo.mapper.ItemNoteMapper.selectByItemId")
            )}
    )
    @Select("""
            <script>
            select
                i.id, i.id id_mapping, content, tag, type, is_done, is_today, scheduled_time, finish_time,
                i.create_time, i.update_time, g.group_id, edit_time
            from item i
            join group_item g on i.id = g.item_id
            where
                g.group_id in (
                    select r.group_id
                    from t_user u
                    join relation_user_group r on u.id = r.user_id
                    where u.id = #{user_id}
                )
                <if test="content != null and content != ''">
                    and content like ('%' || #{content} || '%')
                </if>
                 and type = 1
            </script>
            """)
    List<GroupItemDto> selectAllDtos(@Param("user_id") int userId, @Param("content") String content, @Param("tag") String tag);

    /**
     * 全部条件获取自己已加入的指定id组的任意事项DTO page
     * 可携带条件content tag isDone
     */
    @ResultMap("withNote")
    @Select("""
            <script>
            select
                i.id, i.id id_mapping, content, tag, type, is_done, is_today, scheduled_time, finish_time,
                i.create_time, i.update_time, g.group_id, edit_time
            from item i
            join group_item g on i.id = g.item_id
            where
                g.group_id = #{group_id}
            and
                exists (
                    select 1
                    from relation_user_group
                    where user_id = #{user_id}
                    and group_id = #{group_id}
                )
                <if test="content != null and content != ''">
                    and content like ('%' || #{content} || '%')
                </if>
                <if test="tag != null and tag != ''">
                    and tag like ('%' || #{tag} || '%')
                </if>
                 and type = 1
            </script>
            """)
    List<GroupItemDto> selectOneDtos(@Param("user_id") int userId, @Param("group_id") int groupId, @Param("content") String content, @Param("tag") String tag);
}
