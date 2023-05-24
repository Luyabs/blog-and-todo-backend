package com.example.blogandtodo.mapper;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.blogandtodo.dto.BlogDto;
import com.example.blogandtodo.entity.Blog;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BlogMapper extends BaseMapper<Blog> {
    /**
     * 获取属于用户的博客的id列表
     */
    @Select("select id from blog ${ew.customSqlSegment}")
    List<Integer> selectBlogIdList(@Param(Constants.WRAPPER) Wrapper<Blog> wrapper);


    /**
     * ResultMap: combineBlogWithAnyPersonalItems
     * ResultMap: 获取任何Blog_id = id的个人事项
     * 分页获取个任何BlogDto
     */
    @Results(id = "combineBlogWithAnyPersonalItems", value = {
            @Result(
                    column = "id_mapping", property = "personalItemDtoList", javaType = List.class,
                    many = @Many(select = "com.example.blogandtodo.mapper.PersonalItemMapper.selectAnyPersonalItemDtoByBlogId")
            )
    })
    @Select(
            """
            select id, id id_mapping, user_id, header, description, tag, status, create_time, update_time
            from blog
            ${ew.customSqlSegment}
            """)
    IPage<BlogDto> selectPageDTO(Page<Object> objectPage, @Param(Constants.WRAPPER) Wrapper<BlogDto> wrapper);


    /**
     * ResultMap: combineBlogWithPublicPersonalItems
     * ResultMap: 仅获取任何Blog_id = id且visibility = 0的个人事项
     * 分页获取公开的BlogDto
     */
    @Results(id = "combineBlogWithPublicPersonalItems", value = {
            @Result(
                    column = "id_mapping", property = "personalItemDtoList", javaType = List.class,
                    many = @Many(select = "com.example.blogandtodo.mapper.PersonalItemMapper.selectPublicPersonalItemDtoByBlogId")
            )
    })
    @Select(
            """
            select id, id id_mapping, user_id, header, description, tag, status, create_time, update_time
            from blog
            ${ew.customSqlSegment}
            """)
    IPage<BlogDto> selectPublicPageDTO(Page<Object> objectPage, @Param(Constants.WRAPPER) Wrapper<BlogDto> wrapper);

    /**
     * 按blog_id与user_id获取BlogDto
     */
    @ResultMap("combineBlogWithAnyPersonalItems")
    @Select(
            """
            select id, id id_mapping, user_id, header, description, tag, status, create_time, update_time
            from blog
            where user_id = #{user_id}
            and id = #{blog_id}
            """)
    BlogDto selectPrivateDtoById(@Param("user_id") int userId, @Param("blog_id") int blogId);

    /**
     * 按blog_id获取公开的BlogDto
     */
    @ResultMap("combineBlogWithPublicPersonalItems")
    @Select(
            """
            select id, id id_mapping, user_id, header, description, tag, status, create_time, update_time
            from blog
            where id = #{blog_id}
            """)
    BlogDto selectPublicDtoById(@Param("blog_id") int blogId);

    /**
     * 获取今日自己创建的BlogDto
     * @param userId 自己的用户id
     * @return BlogDto
     */
    @ResultMap("combineBlogWithAnyPersonalItems")
    @Select(
            """
            select id, id id_mapping, user_id, header, description, tag, status, create_time, update_time
            from blog
            where user_id = #{user_id}
            and substr(create_time, 1, 10) = current_date
            """)
    BlogDto selectTodayBlogDto(@Param("user_id") int userId);

}
