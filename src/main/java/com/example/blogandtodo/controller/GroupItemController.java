package com.example.blogandtodo.controller;

import com.example.blogandtodo.common.Result;
import com.example.blogandtodo.dto.GroupItemDto;
import com.example.blogandtodo.entity.GroupItem;
import com.example.blogandtodo.entity.Item;
import com.example.blogandtodo.entity.PersonalItem;
import com.example.blogandtodo.service.GroupItemService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/item/group")
public class GroupItemController {
    @Autowired
    private GroupItemService groupItemService;

    @ApiOperation(value = "全部条件查询[自己所在的所有组]", notes = "全部查询自己所在所有组的事项, 有note, 允许带content, tag, is_done[默认=false]条件")
    @GetMapping("/all")
    public Result getAllGroupItem(GroupItemDto condition) {
        List<GroupItemDto> groupItemDtos = groupItemService.allGroupItem(condition);
        return Result.success().data("group_item_all", groupItemDtos);
    }

    @ApiOperation(value = "全部条件查询[自己所在的某个组]", notes = "全部查询自己所在某个组(需指定组id)的事项, 有note, 允许带content, tag, is_done[默认=false]条件")
    @GetMapping("/{group_id}")
    public Result getOneGroupItem(@PathVariable("group_id") int groupId, GroupItemDto condition) {
        List<GroupItemDto> groupItemDtos = groupItemService.oneGroupItem(groupId, condition);
        return Result.success().data("group_item_all", groupItemDtos);
    }

    @ApiOperation(value = "新增群组事项", notes = "会自动生成对应的笔记(内容为null)")
    @PostMapping
    public Result addItem(@RequestBody GroupItemDto groupItemDto) {
        boolean res = groupItemService.saveGroupItem(groupItemDto);
        return res ? Result.success().message("新增成功") : Result.error();
    }

    @ApiOperation(value = "修改群组事项", notes = "只修改content tag is_done schedule_time is_today常规内容 不能修改is_done, edit_time, group_id等 请用/note下的接口修改笔记")
    @PutMapping
    public Result editItem(@RequestBody GroupItemDto groupItemDto) {
        boolean res = groupItemService.editGroupItem(groupItemDto);
        return res ? Result.success().message("修改成功") : Result.error();
    }

    @ApiOperation(value = "反转群组事项is_done状态", notes = "限自己的事项")
    @PostMapping("/is_done")
    public Result editItemIsDone(@RequestParam int itemId) {
        boolean res = groupItemService.reverseIsDone(itemId);
        return res ? Result.success().message("反转完成状态成功") : Result.error();
    }

    @ApiOperation(value = "Item删除", notes = "限自己加入群的Item 需指定id")
    @DeleteMapping("/{item_id}")
    public Result deleteItem(@PathVariable("item_id") int itemId) {
        boolean res = groupItemService.removeItem(itemId);
        return res ? Result.success().message("删除成功") : Result.error();
    }
}
