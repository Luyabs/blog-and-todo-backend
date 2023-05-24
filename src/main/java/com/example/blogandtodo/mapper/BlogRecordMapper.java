package com.example.blogandtodo.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.blogandtodo.entity.Blog;
import com.example.blogandtodo.entity.BlogRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BlogRecordMapper extends BaseMapper<BlogRecord> {
    @Select("select id from blog_record where blog_id = #{blog_id}")
    List<Integer> selectIdByBlogId(@Param("blog_id") int blogId);   // 找到所有包含blog.id的blog_record
}
