package com.example.blogandtodo.controller;

import com.example.blogandtodo.common.Result;
import com.example.blogandtodo.dto.BlogDto;
import com.example.blogandtodo.dto.PersonalItemDto;
import com.example.blogandtodo.service.BlogService;
import com.example.blogandtodo.service.GroupItemService;
import com.example.blogandtodo.service.PersonalItemService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/today")
public class TodayController {
    @Autowired
    private PersonalItemService personalItemService;

    @Autowired
    private GroupItemService groupItemService;
    @Autowired
    private BlogService blogService;

    @ApiOperation(value = "获取全部自己的今日事项", notes = "查询scheduled_time为今日 或 is_today的事项")
    @GetMapping
    public Result getTodayItem() {
        List<PersonalItemDto> todayItems = personalItemService.getTodayItem();
        return Result.success().data("personal_item_today", todayItems);
    }

    @ApiOperation(value = "反转个人事项的is_today", notes = "只反转is_today属性 不修改scheduled_time")
    @PutMapping("/{item_id}")
    public Result reverseIsToday(@PathVariable("item_id") int itemId) {
        boolean res = personalItemService.reverseIsToday(itemId);
        return res ? Result.success().message("更改状态成功") : Result.error();
    }

    @ApiOperation(value = "反转群组事项的is_today", notes = "只反转is_today属性 不修改scheduled_time")
    @PutMapping("/group/{item_id}")
    public Result reverseGroupIsToday(@PathVariable("item_id") int itemId) {
        boolean res = groupItemService.reverseIsToday(itemId);
        return res ? Result.success().message("更改状态成功") : Result.error();
    }


    @ApiOperation(value = "获取自己的今日博客", notes = "获取博客 不存在时也不会创建")
    @GetMapping("/blog")
    public Result getTodayBlog() {
        BlogDto todayBlog = blogService.getTodayBlog();
        return Result.success().data("blog_today", todayBlog);
    }

    @ApiOperation(value = "生成自己的今日博客", notes = "生成今日博客 一天仅生成一个 添加事项请使用PersonalItemController中的修改所属blog方法")
    @PostMapping("/blog")
    public Result generateTodayBlog() {
        boolean res = blogService.addTodayBlog();
        return res ? Result.success().message("创建成功") : Result.error();
    }


}
