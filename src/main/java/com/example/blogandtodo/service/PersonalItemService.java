package com.example.blogandtodo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.blogandtodo.dto.BlogDto;
import com.example.blogandtodo.dto.PersonalItemDto;
import com.example.blogandtodo.entity.Item;
import com.example.blogandtodo.entity.PersonalItem;

import java.util.List;


public interface PersonalItemService extends IService<PersonalItem> {
    IPage<PersonalItemDto> pageMyItem(int currentPage, int pageSize, PersonalItemDto condition);

    IPage<PersonalItemDto> pagePublicItem(int currentPage, int pageSize, PersonalItemDto condition);

    PersonalItemDto getMyItemById(int itemId);

    PersonalItemDto getPublicItemById(int itemId);

    boolean saveItem(Item item);

    boolean removeItem(int id);

    boolean editItem(Item item);

    boolean editItemVisibility(int itemId, int visibility);

    boolean editItemBelongTo(int itemId, int blogId);

    List<PersonalItemDto> getTodayItem();

    boolean reverseIsToday(int itemId);

    boolean reverseIsDone(int itemId);
}
