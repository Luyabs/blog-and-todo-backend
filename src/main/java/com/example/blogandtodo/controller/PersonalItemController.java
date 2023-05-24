package com.example.blogandtodo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.blogandtodo.common.Result;
import com.example.blogandtodo.dto.PersonalItemDto;
import com.example.blogandtodo.entity.Item;
import com.example.blogandtodo.service.PersonalItemService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/item/personal")
public class PersonalItemController {
    @Autowired
    private PersonalItemService personalItemService;

    @ApiOperation(value = "分页条件查询[自己]", notes = "个人事项(自己的)分页条件查询, 有note笔记, 允许带content, tag, is_done, visibility条件")
    @GetMapping("/my/page")
    public Result getMyItem(@RequestParam(defaultValue = "1") int currentPage, @RequestParam(defaultValue = "10") int pageSize, PersonalItemDto condition) {
        IPage<PersonalItemDto> personalItemPage = personalItemService.pageMyItem(currentPage, pageSize, condition);
        return Result.success().data("personal_item_page", personalItemPage);
    }

    @ApiOperation(value = "分页条件查询[公开]", notes = "个人事项(任何公开的)分页条件查询, 有note笔记, 允许带content, tag, is_done条件")
    @GetMapping("/public/page")
    public Result getPublicItem(@RequestParam(defaultValue = "1") int currentPage, @RequestParam(defaultValue = "10") int pageSize, PersonalItemDto condition) {
        IPage<PersonalItemDto> personalItemPage = personalItemService.pagePublicItem(currentPage, pageSize, condition);
        return Result.success().data("personal_item_page", personalItemPage);
    }

    @ApiOperation(value = "按id查询[自己]", notes = "个人事项(自己的)按id查询")
    @GetMapping("/my/{item_id}")
    public Result getMyItemById(@PathVariable("item_id") int itemId) {
        PersonalItemDto personalItemDto = personalItemService.getMyItemById(itemId);
        return Result.success().data("personal_item", personalItemDto);
    }

    @ApiOperation(value = "按id查询[公开]", notes = "个人事项(任何公开的)按id查询")
    @GetMapping("/public/{item_id}")
    public Result getPublicItemById(@PathVariable("item_id") int itemId) {
        PersonalItemDto personalItemDto = personalItemService.getPublicItemById(itemId);
        return Result.success().data("personal_item", personalItemDto);
    }

    @ApiOperation(value = "Item新增", notes = "限自己的Item, 会自动生成对应的笔记(内容为null)")
    @PostMapping
    public Result addItem(@RequestBody Item item) {
        boolean res = personalItemService.saveItem(item);
        return res ? Result.success().message("新增成功") : Result.error();
    }

    @ApiOperation(value = "Item删除", notes = "限自己的Item 需指定id")
    @DeleteMapping("/{item_id}")
    public Result deleteItem(@PathVariable("item_id") int itemId) {
        boolean res = personalItemService.removeItem(itemId);
        return res ? Result.success().message("删除成功") : Result.error();
    }

    @ApiOperation(value = "Item修改基本内容", notes = "限自己的Item 不能修改visibility或所属blog 请用/note下的接口修改笔记")
    @PutMapping
    public Result editItem(@RequestBody Item item) {
        boolean res = personalItemService.editItem(item);
        return res ? Result.success().message("修改成功") : Result.error();
    }

    @ApiOperation(value = "Item修改可见度", notes = "0: 公开 1: 私有 限自己的Item 仅visibility")
    @PostMapping("/visibility")
    public Result editItemVisibility(@RequestParam int itemId, @RequestParam int visibility) {
        boolean res = personalItemService.editItemVisibility(itemId, visibility);
        return res ? Result.success().message("可见度更新成功") : Result.error();
    }

    @ApiOperation(value = "Item更改所属blog", notes = "如果像设置不属于Blog则传入-1 限自己的Item 仅blogId")
    @PostMapping("/blog")
    public Result editItemBlog(@RequestParam int itemId, @RequestParam int blogId) {
        boolean res = personalItemService.editItemBelongTo(itemId, blogId);
        return res ? Result.success().message("调整所属blog成功") : Result.error();
    }

    @ApiOperation(value = "反转个人事项is_done状态", notes = "限自己的事项")
    @PostMapping("/is_done")
    public Result editItemBlog(@RequestParam int itemId) {
        boolean res = personalItemService.reverseIsDone(itemId);
        return res ? Result.success().message("反转完成状态成功") : Result.error();
    }
}
