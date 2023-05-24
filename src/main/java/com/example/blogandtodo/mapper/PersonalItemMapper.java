package com.example.blogandtodo.mapper;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.blogandtodo.dto.BlogDto;
import com.example.blogandtodo.dto.PersonalItemDto;
import com.example.blogandtodo.entity.ItemNote;
import com.example.blogandtodo.entity.PersonalItem;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PersonalItemMapper extends BaseMapper<PersonalItem> {
    /**
     * 分页条件获取任意的任意事项DTO page
     */
    @Results(id = "withNote", value = {
            @Result(
                    column = "id_mapping", property = "itemNote", javaType = ItemNote.class,
                    many = @Many(select = "com.example.blogandtodo.mapper.ItemNoteMapper.selectByItemId")
            )}
    )
    @Select("""
            select
                item.id, item.id id_mapping, content, tag, type, is_done, is_today, scheduled_time, finish_time,
                item.create_time, item.update_time, blog_id, visibility
            from
                item join personal_item on item.id = personal_item.item_id
            ${ew.customSqlSegment}
            """)
    IPage<PersonalItemDto> selectPageDTO(Page<Object> objectPage, @Param(Constants.WRAPPER) QueryWrapper<PersonalItemDto> queryWrapper);

    /**
     * 分页条件获取任意的个人事项DTO page
     */
    @ResultMap("withNote")
    @Select("""
            select
                item.id, item.id id_mapping, content, tag, type, is_done, is_today, scheduled_time, finish_time,
                item.create_time, item.update_time, blog_id, visibility
            from
                item join personal_item on item.id = personal_item.item_id
            ${ew.customSqlSegment}
            """)
    IPage<PersonalItemDto> selectPublicPageDTO(Page<Object> objectPage, @Param(Constants.WRAPPER) QueryWrapper<PersonalItemDto> queryWrapper);

    /**
     * 按item_id获取公开的个人事项DTO列表
     */
    @ResultMap("withNote")
    @Select("""
            select
                item.id, item.id id_mapping, content, tag, type, is_done, is_today, scheduled_time, finish_time,
                item.create_time, item.update_time, blog_id, user_id, visibility
            from
                item join personal_item on item.id = personal_item.item_id
            where
                item.id = #{item_id}
            and
                type = 0
            and
                visibility = 0
            """)
    PersonalItemDto selectPublicPersonalItemDtoByItemId(@Param("item_id") int itemId);

    /**
     * 按blog_id获取任意的个人事项DTO列表
     */
    @ResultMap("withNote")
    @Select("""
            select
                item.id, item.id id_mapping, content, tag, type, is_done, is_today, scheduled_time, finish_time,
                item.create_time, item.update_time, blog_id, user_id, visibility
            from
                item join personal_item on item.id = personal_item.item_id
            where
                blog_id = #{blog_id}
            and
                type = 0
            """)
    List<PersonalItemDto> selectAnyPersonalItemDtoByBlogId(@Param("blog_id") int blogId);

    /**
     * 按item_id获取任意的个人事项DTO列表
     */
    @ResultMap("withNote")
    @Select("""
            select
                item.id, item.id id_mapping, content, tag, type, is_done, is_today, scheduled_time, finish_time,
                item.create_time, item.update_time, blog_id, user_id, visibility
            from
                item join personal_item on item.id = personal_item.item_id
            where
                item.id = #{item_id}
            and
                type = 0
            and
                user_id = #{user_id}
            """)
    PersonalItemDto selectMyPersonalItemDtoByItemId(@Param("user_id") int userId, @Param("item_id") int itemId);

    /**
     * 按item_id获取公开的个人事项DTO列表
     */
    @ResultMap("withNote")
    @Select("""
            select
                item.id, item.id id_mapping, content, tag, type, is_done, is_today, scheduled_time, finish_time,
                item.create_time, item.update_time, blog_id, user_id, visibility
            from
                item join personal_item on item.id = personal_item.item_id
            where
                item.id = #{item_id}
            and
                visibility = 0
            and
                type = 0
            """)
    List<PersonalItemDto> selectPublicPersonalItemDtoByBlogId(@Param("item_id") int itemId);


    /**
     * 按item_id获取公开的个人事项DTO列表
     */
    @ResultMap("withNote")
    @Select("""
            select
                item.id, item.id id_mapping, content, tag, type, is_done, is_today, scheduled_time, finish_time,
                item.create_time, item.update_time, blog_id, user_id, visibility
            from
                item join personal_item on item.id = personal_item.item_id
            ${ew.customSqlSegment}
            """)
    List<PersonalItemDto> selectTodayDTO(@Param(Constants.WRAPPER) QueryWrapper<PersonalItemDto> queryWrapper);

    @Update("""
            update personal_item
            set blog_id = null
            where item_id = #{item_id}
            """)
    boolean updateBlogIdToNull(@Param("item_id") int itemId);
}
