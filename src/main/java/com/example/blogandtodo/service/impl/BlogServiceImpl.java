package com.example.blogandtodo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.blogandtodo.common.UserInfo;
import com.example.blogandtodo.common.exception.ServiceException;
import com.example.blogandtodo.dto.BlogDto;
import com.example.blogandtodo.entity.Blog;
import com.example.blogandtodo.entity.BlogRecord;
import com.example.blogandtodo.mapper.BlogMapper;
import com.example.blogandtodo.mapper.BlogRecordMapper;
import com.example.blogandtodo.mapper.ItemMapper;
import com.example.blogandtodo.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {
    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private BlogRecordMapper blogRecordMapper;

    @Autowired
    private ItemMapper itemMapper;

    @Override
    public IPage<BlogDto> pageMyBlog(int currentPage, int pageSize, Blog condition) {
        QueryWrapper<BlogDto> queryWrapper = new QueryWrapper<BlogDto>()
                .eq("user_id", UserInfo.get())  // 本人
                .like(condition.getHeader() != null, "header", condition.getHeader())
                .like(condition.getDescription() != null, "description", condition.getDescription())
                .like(condition.getTag() != null, "tag", condition.getTag())
                .like(condition.getStatus() != null, "status", condition.getStatus())
                .orderByDesc("update_time");
        IPage<BlogDto> blogDtoPage = blogMapper.selectPageDTO(new Page<>(currentPage, pageSize), queryWrapper);
        return blogDtoPage;
    }

    @Override
    public IPage<BlogDto> pagePublicBlog(int currentPage, int pageSize, Blog condition) {
        QueryWrapper<BlogDto> queryWrapper = new QueryWrapper<BlogDto>()
                .like(condition.getHeader() != null, "header", condition.getHeader())
                .like(condition.getDescription() != null, "description", condition.getDescription())
                .like(condition.getTag() != null, "tag", condition.getTag())
                .like(condition.getStatus() != null, "status", condition.getStatus())
                .orderByDesc("update_time");
        IPage<BlogDto> blogDtoPage = blogMapper.selectPublicPageDTO(new Page<>(currentPage, pageSize), queryWrapper);
        return blogDtoPage;
    }

    @Override
    public BlogDto getMyBlogById(int blogId) {
        BlogDto blogDto = blogMapper.selectPrivateDtoById(UserInfo.get(), blogId);
        if (blogDto == null)
            throw new ServiceException("id=" + blogId + "的BLOG不存在 / 不属于自己");
        return blogDto;
    }

    @Override
    public BlogDto getPublicBlogById(int blogId) {
        BlogDto blogDto = blogMapper.selectPublicDtoById(blogId);
        if (blogDto == null)
            throw new ServiceException("id=" + blogId + "的BLOG不存在");
        return blogDto;
    }

    @Override
    public boolean saveBlog(Blog blog) {
        blog.setId(null).setUserId(UserInfo.get()).setStatus(1).setCreateTime(null);
        return blogMapper.insert(blog) > 0;
    }

    @Override
    @Transactional
    public boolean removeBlog(int id) {
        // 同时删除包含该BLOG的浏览记录 与 解除该BLOG中所有的TODO与该BLOG的关联
        boolean exists = blogMapper.exists(new QueryWrapper<Blog>().eq("id", id));
        if (!exists)    // 该id不存在
            throw new ServiceException("id=" + id + "的BLOG不存在");

        // 级联删除包含该BLOG的浏览记录
        blogRecordMapper.delete(new QueryWrapper<BlogRecord>().eq("blog_id", id));

        // 级联解除该BLOG中所有的TODO与该BLOG的关联
        itemMapper.updateBlogIdToNull(id);

        return blogMapper.deleteById(id) > 0;
    }

    @Override
    public boolean editBlog(Blog blog) {
        blog.setUserId(null).setStatus(null).setCreateTime(null);

        // 验证blog是否属于当前用户
        Blog targetValid = blogMapper.selectById(blog.getId());
        if (targetValid == null)
            throw new ServiceException("blog.id 不存在");
        if (!targetValid.getUserId().equals(UserInfo.get()))
            throw new ServiceException("你只能修改自己的Blog");

        return blogMapper.updateById(blog) > 0;
    }

    @Override
    public BlogDto getTodayBlog() {
        BlogDto blogDto = blogMapper.selectTodayBlogDto(UserInfo.get());
        if (blogDto == null)
            throw new ServiceException("不存在今日Blog, 请通过addTodayBlog来创建一个");
        return blogDto;
    }

    @Override
    public boolean addTodayBlog() {
        if (blogMapper.selectTodayBlogDto(UserInfo.get()) != null)
            throw new ServiceException("你已经创建过今日Blog了");
        Blog blog = new Blog().setUserId(UserInfo.get())
                .setHeader("我的今日博客").setDescription("请添加todo事项").setStatus(1);
        return blogMapper.insert(blog) > 0;
    }
}
