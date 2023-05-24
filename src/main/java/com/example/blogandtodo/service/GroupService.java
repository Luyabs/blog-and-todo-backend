package com.example.blogandtodo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.blogandtodo.dto.GroupDto;
import com.example.blogandtodo.entity.Group;

public interface GroupService extends IService<Group> {

    IPage<GroupDto> pageDto(int currentPage, int pageSize, String groupName, String description, int minMemberNum, int maxMemberNum);

    IPage<GroupDto> myPageDto(int currentPage, int pageSize, String groupName, String description);

    GroupDto getAllGroupById(int groupId);

    GroupDto getMyGroupById(int groupId);

    boolean saveGroup(int groupId, String password);

    boolean withdrawGroup(int groupId, String password);

    boolean createGroup(Group group);

    boolean editGroup(Group group);
}
