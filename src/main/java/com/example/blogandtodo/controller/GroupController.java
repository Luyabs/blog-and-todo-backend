package com.example.blogandtodo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.blogandtodo.common.Result;
import com.example.blogandtodo.dto.GroupDto;
import com.example.blogandtodo.entity.Group;
import com.example.blogandtodo.service.GroupService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/group")
public class GroupController {
    @Autowired
    private GroupService groupService;

    @ApiOperation(value = "分页条件查询[全部]", notes = "群组分页条件查询 允许带groupName, description, memberNum条件")
    @GetMapping("/all/page")
    public Result getAllPageGroup(@RequestParam(defaultValue = "1") int currentPage, @RequestParam(defaultValue = "10") int pageSize, String groupName, String description, @RequestParam(defaultValue = "0") int minMemberNum, @RequestParam(defaultValue = "100") int maxMemberNum) {
        IPage<GroupDto> groupPage = groupService.pageDto(currentPage, pageSize, groupName, description, minMemberNum, maxMemberNum);
        return Result.success().data("group_page", groupPage);
    }

    @ApiOperation(value = "分页条件查询[自己]", notes = "群组分页条件查询 允许带groupName, description条件")
    @GetMapping("/my/page")
    public Result getMyPageGroup(@RequestParam(defaultValue = "1") int currentPage, @RequestParam(defaultValue = "10") int pageSize, String groupName, String description) {
        IPage<GroupDto> groupPage = groupService.myPageDto(currentPage, pageSize, groupName, description);
        return Result.success().data("group_page", groupPage);
    }

    @ApiOperation(value = "按id查询[全部]", notes = "按id查询")
    @GetMapping("/public/{group_id}")
    public Result getAllGroupById(@PathVariable("group_id") int groupId) {
        GroupDto groupDto = groupService.getAllGroupById(groupId);
        return Result.success().data("group", groupDto);
    }

    @ApiOperation(value = "按id查询[自己]", notes = "按id查询, 仅自己加入的群组")
    @GetMapping("/my/{group_id}")
    public Result getMyGroupById(@PathVariable("group_id") int groupId) {
        GroupDto groupDto = groupService.getMyGroupById(groupId);
        return Result.success().data("group", groupDto);
    }

    @ApiOperation(value = "加入群", notes = "加入群")
    @PostMapping("/join/{group_id}")
    public Result joinGroup(@PathVariable("group_id") int groupId, String password) {
        boolean res = groupService.saveGroup(groupId, password);
        return res ? Result.success().message("入群成功") : Result.error();
    }

    @ApiOperation(value = "退出群", notes = "退出群, 最后一人退出后群将消失")
    @PostMapping("/withdraw/{group_id}")
    public Result withdrawGroup(@PathVariable("group_id") int groupId, String password) {
        boolean res = groupService.withdrawGroup(groupId, password);
        return res ? Result.success().message("退群成功") : Result.error();
    }

    @ApiOperation(value = "新建群", notes = "新建群")
    @PostMapping
    public Result createGroup(@RequestBody Group group) {
        boolean res = groupService.createGroup(group);
        return res ? Result.success().message("创建成功") : Result.error();
    }

    @ApiOperation(value = "修改群信息", notes = "修改群信息, 仅支持修改groupName, description")
    @PutMapping
    public Result editGroup(@RequestBody Group group) {
        boolean res = groupService.editGroup(group);
        return res ? Result.success().message("修改群信息成功") : Result.error();
    }
}
