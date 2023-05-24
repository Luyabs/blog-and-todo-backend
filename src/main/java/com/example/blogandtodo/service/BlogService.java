package com.example.blogandtodo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.blogandtodo.dto.BlogDto;
import com.example.blogandtodo.entity.Blog;

public interface BlogService extends IService<Blog> {
    IPage<BlogDto> pageMyBlog(int currentPage, int pageSize, Blog condition);

    IPage<BlogDto> pagePublicBlog(int currentPage, int pageSize, Blog condition);

    BlogDto getMyBlogById(int blogId);

    BlogDto getPublicBlogById(int blogId);

    boolean saveBlog(Blog blog);

    boolean removeBlog(int id);

    boolean editBlog(Blog blog);

    BlogDto getTodayBlog();

    boolean addTodayBlog();
}
