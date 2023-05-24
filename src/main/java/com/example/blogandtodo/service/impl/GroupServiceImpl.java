package com.example.blogandtodo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.blogandtodo.common.UserInfo;
import com.example.blogandtodo.common.exception.ServiceException;
import com.example.blogandtodo.dto.GroupDto;
import com.example.blogandtodo.entity.*;
import com.example.blogandtodo.mapper.*;
import com.example.blogandtodo.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GroupServiceImpl extends ServiceImpl<GroupMapper, Group> implements GroupService {
    @Autowired
    private GroupMapper groupMapper;

    @Autowired
    private RelationUserGroupMapper relationUserGroupMapper;

    @Override
    public IPage<GroupDto> pageDto(int currentPage, int pageSize, String groupName, String description, int minMemberNum, int maxMemberNum) {
        QueryWrapper<GroupDto> queryWrapper = new QueryWrapper<GroupDto>()
                .like(groupName != null, "groupName", groupName)
                .like(description != null, "description", description)
                .ge(minMemberNum <= maxMemberNum, "member_num", minMemberNum)
                .le(minMemberNum <= maxMemberNum, "member_num", maxMemberNum)
                .orderByDesc("update_time");
        IPage<GroupDto> groupDto = groupMapper.selectAllPageDTO(new Page<>(currentPage, pageSize), queryWrapper);
        return groupDto;
    }

    @Override
    public IPage<GroupDto> myPageDto(int currentPage, int pageSize, String groupName, String description) {
        IPage<GroupDto> groupDto = groupMapper.selectMyPageDTO(new Page<>(currentPage, pageSize), UserInfo.get(), groupName, description);
        return groupDto;
    }

    @Override
    public GroupDto getAllGroupById(int groupId) {
        GroupDto groupDto = groupMapper.selectByIdDto(groupId);
        if (groupDto == null)
            throw new ServiceException("id=" + groupId + "的群组不存在");
        return groupDto;
    }

    @Override
    public GroupDto getMyGroupById(int groupId) {
        GroupDto groupDto = groupMapper.selectByIdDto(groupId);
        if (groupDto == null)
            throw new ServiceException("id=" + groupId + "的群组不存在");
        for (User user : groupDto.getGroupMembers()) {
            if (UserInfo.get().equals(user.getId()))
                return groupDto;
        }
        throw new ServiceException("未加入id=" + groupId + "的群组");
    }

    @Override
    @Transactional
    public boolean saveGroup(int groupId, String password) {
        Group group = groupMapper.selectById(groupId);
        if (group == null)
            throw new ServiceException("id=" + groupId + "的群组不存在");

        RelationUserGroup relationUserGroup = relationUserGroupMapper.selectOne(new QueryWrapper<RelationUserGroup>().eq("group_id", groupId).eq("user_id", UserInfo.get()));
        if (relationUserGroup != null)
            throw new ServiceException("你已经在id=" + groupId + "的群中, 无法重复加入");

        if (group.getPassword() != null && !password.equals(group.getPassword()))
            throw new ServiceException("密码错误, 无法入群");

        relationUserGroupMapper.insert(new RelationUserGroup().setUserId(UserInfo.get()).setGroupId(groupId));
        return groupMapper.updateById(group.setMemberNum(group.getMemberNum() + 1)) > 0;
    }

    @Override
    @Transactional
    public boolean withdrawGroup(int groupId, String password) {
        Group group = groupMapper.selectById(groupId);
        if (group == null)
            throw new ServiceException("id=" + groupId + "的群组不存在");

        RelationUserGroup relationUserGroup = relationUserGroupMapper.selectOne(new QueryWrapper<RelationUserGroup>().eq("group_id", groupId).eq("user_id", UserInfo.get()));
        if (relationUserGroup == null)
            throw new ServiceException("未加入id=" + groupId + "的群组");

        if (group.getPassword() != null && !password.equals(group.getPassword()))
            throw new ServiceException("密码错误, 无法入群");

        relationUserGroupMapper.deleteById(relationUserGroup.getId());

        if (group.getMemberNum() == 1)
            return groupMapper.deleteById(group) > 0;  // 只剩最后一人则删除本群
        else
            return groupMapper.updateById(group.setMemberNum(group.getMemberNum() - 1)) > 0;
    }

    @Override
    @Transactional
    public boolean createGroup(Group group) {
        group.setId(null).setCreateTime(null).setMemberNum(1);
        groupMapper.insert(group);
        return relationUserGroupMapper.insert(new RelationUserGroup().setUserId(UserInfo.get()).setGroupId(group.getId())) > 0;
    }

    @Override
    public boolean editGroup(Group group) {
        Group targetGroup = groupMapper.selectById(group.getId());
        if (targetGroup == null)
            throw new ServiceException("id=" + group.getId() + "的群组不存在");

        RelationUserGroup relationUserGroup = relationUserGroupMapper.selectOne(new QueryWrapper<RelationUserGroup>().eq("group_id", group.getId()).eq("user_id", UserInfo.get()));
        if (relationUserGroup == null)
            throw new ServiceException("未加入id=" + group.getId() + "的群组");

        targetGroup.setGroupName(group.getGroupName());
        targetGroup.setDescription(group.getDescription());
        targetGroup.setPassword(group.getPassword());
        return groupMapper.updateById(targetGroup) > 0;
    }
}
