package com.example.blogandtodo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.blogandtodo.common.UserInfo;
import com.example.blogandtodo.common.exception.ServiceException;
import com.example.blogandtodo.dto.GroupItemDto;
import com.example.blogandtodo.entity.*;
import com.example.blogandtodo.mapper.*;
import com.example.blogandtodo.service.GroupItemService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class GroupItemServiceImpl extends ServiceImpl<GroupItemMapper, GroupItem> implements GroupItemService {
    @Autowired
    private GroupMapper groupMapper;

    @Autowired
    private RelationUserGroupMapper relationUserGroupMapper;

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private GroupItemMapper groupItemMapper;

    @Autowired
    private ItemNoteMapper itemNoteMapper;

    @Override
    public List<GroupItemDto> allGroupItem(GroupItemDto condition) {
        return groupItemMapper.selectAllDtos(UserInfo.get(), condition.getContent(), condition.getTag());
    }

    @Override
    public List<GroupItemDto> oneGroupItem(int groupId, GroupItemDto condition) {
        checkInGroup(groupId);
        return groupItemMapper.selectOneDtos(UserInfo.get(), groupId, condition.getContent(), condition.getTag());
    }

    @Override
    @Transactional
    public boolean saveGroupItem(GroupItemDto groupItemDto) {
        checkInGroup(groupItemDto.getGroupId());    // 检查本人是否在群中 且群是否存在
        groupItemDto.setId(null).setType(1).setIsDone(false).setCreateTime(null);

        boolean res = itemMapper.insert(groupItemDto) > 0; // 插入item表
        if (!res)
            throw new ServiceException("向事项父表中插入数据失败");

        GroupItem groupItem = new GroupItem();
        groupItem.setItemId(groupItemDto.getId())
                .setGroupId(groupItemDto.getGroupId())
                .setEditTime(0);

        res = groupItemMapper.insert(groupItem) > 0;
        if (!res)
            throw new ServiceException("向群组事项表中插入数据失败");

        res = itemNoteMapper.insert(new ItemNote().setItemId(groupItem.getItemId())) > 0;
        if (!res)
            throw new ServiceException("向事项笔记表中插入数据失败");

        return true;
    }

    @Override
    @Transactional
    public boolean editGroupItem(GroupItemDto groupItemDto) {
        checkInGroup(groupItemDto.getGroupId());    // 检查本人是否在群中 且群是否存在
        Item item = checkItemExist(groupItemDto.getId());   // 检查群组事项是否存在

        item.setContent(groupItemDto.getContent())
                .setTag(groupItemDto.getTag())
                .setIsDone(groupItemDto.getIsDone())
                .setScheduledTime(groupItemDto.getScheduledTime())
                .setIsToday(groupItemDto.getIsToday());
        itemMapper.updateById(item);

        if (LocalDateTime.of(1, 1, 1, 0, 0, 0).equals(item.getScheduledTime())) {
            itemMapper.updateScheduledTimeToNull(item.getId());
            item.setScheduledTime(null);
        }

        GroupItem groupItem = groupItemMapper.selectOne(new QueryWrapper<GroupItem>().eq("item_id", item.getId()));
        return groupItemMapper.updateById(groupItem.setEditTime(groupItem.getEditTime() + 1)) > 0;
    }

    @Override
    @Transactional
    public boolean reverseIsDone(int itemId) {
        GroupItem groupItem = checkGroupItemExist(itemId);  // 检查群组事项是否存在
        checkInGroup(groupItem.getGroupId());   // 检查是否本人在群里
        Item item = checkItemExist(itemId);     // 仅获取item对象

        boolean res = groupItemMapper.updateById(groupItem.setEditTime(groupItem.getEditTime() + 1)) > 0;
        if (!item.getIsDone())  // 未完成 -> 已完成
            return itemMapper.updateIsDoneAndFinishTime(itemId) && res;
        else
            return itemMapper.updateNotDoneAndFinishTime(itemId) && res;
    }

    @Override
    @Transactional
    public boolean reverseIsToday(int itemId) {
        GroupItem groupItem = checkGroupItemExist(itemId);  // 检查群组事项是否存在
        checkInGroup(groupItem.getGroupId());   // 检查是否本人在群里
        Item item = checkItemExist(itemId);     // 仅获取item对象

        boolean res = groupItemMapper.updateById(groupItem.setEditTime(groupItem.getEditTime() + 1)) > 0;
        item.setIsToday(!item.getIsToday());
        return itemMapper.updateById(item) > 0 && res;
    }

    @Override
    @Transactional
    public boolean removeItem(int itemId) {
        GroupItem groupItem = checkGroupItemExist(itemId);  // 检查群组事项是否存在
        checkInGroup(groupItem.getGroupId());   // 检查是否本人在群里

        boolean res = itemNoteMapper.delete(new QueryWrapper<ItemNote>().eq("item_id", itemId)) > 0;
        if (!res)
            throw new ServiceException("从事项笔记表中删除失败");

        res = groupItemMapper.delete(new QueryWrapper<GroupItem>().eq("item_id", itemId)) > 0;
        if (!res)
            throw new ServiceException("从个人事项表中删除失败");

        res = itemMapper.deleteById(itemId) > 0;
        if (!res)
            throw new ServiceException("从事项父表中删除失败");

        return true;
    }

    /**
     * 检查群组是否存在且本人是否在群内
     */
    public void checkInGroup(Integer groupId) {
        if (groupId == null)
            throw new ServiceException("请传入groupId");
        int userId = UserInfo.get();
        Group group = groupMapper.selectById(groupId);
        if (group == null)
            throw new ServiceException("不存在group_id=" + groupId + "的群组");

        RelationUserGroup one = relationUserGroupMapper.selectOne(
                new QueryWrapper<RelationUserGroup>()
                        .eq("group_Id", groupId)
                        .eq("user_id", userId)
        );
        if (one == null)
            throw new ServiceException("您未加入group_id=" + groupId + "的群组");
    }

    /**
     * 检查事项是否存在
     */
    private Item checkItemExist(Integer itemId) {
        if (itemId == null)
            throw new ServiceException("请传入itemId");
        Item item = itemMapper.selectById(itemId);
        if (item == null)
            throw new ServiceException("不存在item_id=" + itemId + "的事项");
        return item;
    }


    /**
     * 检查群组事项是否存在
     */
    private GroupItem checkGroupItemExist(int itemId) {
        GroupItem groupItem = groupItemMapper.selectOne(new QueryWrapper<GroupItem>().eq("item_id", itemId));
        if (groupItem == null)
            throw new ServiceException("不存在item_id=" + itemId + "的群组事项");
        return groupItem;
    }
}
