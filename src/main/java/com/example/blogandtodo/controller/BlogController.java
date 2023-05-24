package com.example.blogandtodo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.blogandtodo.common.Result;
import com.example.blogandtodo.dto.BlogDto;
import com.example.blogandtodo.entity.Blog;
import com.example.blogandtodo.service.BlogService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/blog")
public class BlogController {
    @Autowired
    private BlogService blogService;

    @ApiOperation(value = "分页条件查询[自己][含TODO]", notes = "BLOG(自己的)分页条件查询, 有todo事项, 允许带header description, tag, status条件")
    @GetMapping("/my/page")
    public Result getMyBlog(@RequestParam(defaultValue = "1") int currentPage, @RequestParam(defaultValue = "10") int pageSize, Blog condition) {
        IPage<BlogDto> blogPage = blogService.pageMyBlog(currentPage, pageSize, condition);
        return Result.success().data("blog_page", blogPage);
    }

    @ApiOperation(value = "分页条件查询[公开][含公开的TODO]", notes = "BLOG(任何公开的)分页条件查询, 有todo事项, 允许带header description, tag, status条件")
    @GetMapping("/public/page")
    public Result getPublicBlog(@RequestParam(defaultValue = "1") int currentPage, @RequestParam(defaultValue = "10") int pageSize, Blog condition) {
        IPage<BlogDto> blogPage = blogService.pagePublicBlog(currentPage, pageSize, condition);
        return Result.success().data("blog_page", blogPage);
    }

    @ApiOperation(value = "按id查询[自己][含TODO]", notes = "BLOG(自己的)按id查询, 有todo事项")
    @GetMapping("/my/{blog_id}")
    public Result getMyBlogById(@PathVariable("blog_id") int blogId) {
        BlogDto blogDto = blogService.getMyBlogById(blogId);
        return Result.success().data("blog", blogDto);
    }

    @ApiOperation(value = "按id查询[公开][含公开的TODO]", notes = "BLOG(任何公开的)按id查询, 有todo事项")
    @GetMapping("/public/{blog_id}")
    public Result getPublicBlogById(@PathVariable("blog_id") int blogId) {
        BlogDto blogDto = blogService.getPublicBlogById(blogId);
        return Result.success().data("blog", blogDto);
    }

    @ApiOperation(value = "BLOG新增", notes = "限自己的BLOG")
    @PostMapping
    public Result addBlog(@RequestBody Blog blog) {
        boolean res = blogService.saveBlog(blog);
        return res ? Result.success().message("新增成功") : Result.error();
    }

    @ApiOperation(value = "BLOG删除", notes = "限自己的BLOG 需指定id")
    @DeleteMapping("/{blog_id}")
    public Result deleteBlog(@PathVariable("blog_id") int blogId) {
        boolean res = blogService.removeBlog(blogId);
        return res ? Result.success().message("删除成功") : Result.error();
    }

    @ApiOperation(value = "BLOG修改", notes = "限自己的BLOG userId和status字段将被忽略 不会修改BLOG中的事项")
    @PutMapping
    public Result editBlog(@RequestBody Blog blog) {
        boolean res = blogService.editBlog(blog);
        return res ? Result.success().message("修改成功") : Result.error();
    }
}
