package com.example.blogandtodo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.blogandtodo.dto.GroupItemDto;
import com.example.blogandtodo.dto.PersonalItemDto;
import com.example.blogandtodo.entity.GroupItem;
import com.example.blogandtodo.entity.Item;

import java.util.List;


public interface GroupItemService extends IService<GroupItem> {
    List<GroupItemDto> allGroupItem(GroupItemDto condition);

    List<GroupItemDto> oneGroupItem(int groupId, GroupItemDto condition);

    boolean saveGroupItem(GroupItemDto groupItemDto);

    boolean editGroupItem(GroupItemDto groupItemDto);

    boolean reverseIsDone(int itemId);

    boolean reverseIsToday(int itemId);

    boolean removeItem(int itemId);
}
