package com.example.blogandtodo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.blogandtodo.common.UserInfo;
import com.example.blogandtodo.common.exception.ServiceException;
import com.example.blogandtodo.dto.PersonalItemDto;
import com.example.blogandtodo.entity.Blog;
import com.example.blogandtodo.entity.Item;
import com.example.blogandtodo.entity.ItemNote;
import com.example.blogandtodo.entity.PersonalItem;
import com.example.blogandtodo.mapper.*;
import com.example.blogandtodo.service.PersonalItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class PersonalItemServiceImpl extends ServiceImpl<PersonalItemMapper, PersonalItem> implements PersonalItemService {
    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private PersonalItemMapper personalItemMapper;

    @Autowired
    private ItemNoteMapper itemNoteMapper;

    @Autowired
    private GroupItemMapper groupItemMapper;

    @Override
    public IPage<PersonalItemDto> pageMyItem(int currentPage, int pageSize, PersonalItemDto condition) {
        QueryWrapper<PersonalItemDto> queryWrapper = new QueryWrapper<PersonalItemDto>()
                .eq("user_id", UserInfo.get())
                .eq("type", 0)
                .like(condition.getContent() != null, "content", condition.getContent())
                .like(condition.getTag() != null, "tag", condition.getTag())
                .eq(condition.getIsDone() != null, "is_done", condition.getIsDone())
                .eq(condition.getVisibility() != null, "visibility", condition.getVisibility())
                .orderByDesc("update_time");
        IPage<PersonalItemDto> personalItemDtoPage = personalItemMapper.selectPageDTO(new Page<>(currentPage, pageSize), queryWrapper);
        return personalItemDtoPage;
    }

    @Override
    public IPage<PersonalItemDto> pagePublicItem(int currentPage, int pageSize, PersonalItemDto condition) {
        QueryWrapper<PersonalItemDto> queryWrapper = new QueryWrapper<PersonalItemDto>()
                .eq("type", 0)
                .like(condition.getContent() != null, "content", condition.getContent())
                .like(condition.getTag() != null, "tag", condition.getTag())
                .eq(condition.getIsDone() != null, "is_done", condition.getIsDone())
                .eq("visibility", 0)        // 公开
                .orderByDesc("update_time");
        IPage<PersonalItemDto> personalItemDtoPage = personalItemMapper.selectPublicPageDTO(new Page<>(currentPage, pageSize), queryWrapper);
        return personalItemDtoPage;
    }

    @Override
    public PersonalItemDto getMyItemById(int itemId) {
        PersonalItemDto personalItemDto = personalItemMapper.selectMyPersonalItemDtoByItemId(UserInfo.get(), itemId);
        if (personalItemDto == null)
            throw new ServiceException("id=" + itemId + "的个人事项不存在 / 不属于自己");
        return personalItemDto;
    }

    @Override
    public PersonalItemDto getPublicItemById(int itemId) {
        PersonalItemDto personalItemDto = personalItemMapper.selectPublicPersonalItemDtoByItemId(itemId);
        if (personalItemDto == null)
            throw new ServiceException("id=" + itemId + "的个人事项不存在 / 未公开");
        return personalItemDto;
    }

    @Override
    @Transactional
    public boolean saveItem(Item item) {
        item.setId(null).setType(0).setIsDone(false).setCreateTime(null);
        boolean res = itemMapper.insert(item) > 0; // 插入item表
        if (!res)
            throw new ServiceException("向事项父表中插入数据失败");

        PersonalItem personalItem = new PersonalItem();
        personalItem.setItemId(item.getId()).setVisibility(1).setUserId(UserInfo.get());

        res = personalItemMapper.insert(personalItem) > 0;
        if (!res)
            throw new ServiceException("向个人事项表中插入数据失败");

        res = itemNoteMapper.insert(new ItemNote().setItemId(item.getId())) > 0;
        if (!res)
            throw new ServiceException("向事项笔记表中插入数据失败");

        return true;
    }

    @Override
    @Transactional
    public boolean removeItem(int itemId) {
        getPersonalItemWithCheckingPresentAndOwner(itemId);

        boolean res = itemNoteMapper.delete(new QueryWrapper<ItemNote>().eq("item_id", itemId)) > 0;
        if (!res)
            throw new ServiceException("从事项笔记表中删除失败");

        res = personalItemMapper.delete(new QueryWrapper<PersonalItem>().eq("item_id", itemId)) > 0;
        if (!res)
            throw new ServiceException("从个人事项表中删除失败");

        res = itemMapper.deleteById(itemId) > 0;
        if (!res)
            throw new ServiceException("从事项父表中删除失败");

        return true;
    }

    @Override
    public boolean editItem(Item item) {
        getPersonalItemWithCheckingPresentAndOwner(item.getId());   // 仅做检查 不返回
        if (LocalDateTime.of(1, 1, 1, 0, 0, 0).equals(item.getScheduledTime())) {
            itemMapper.updateScheduledTimeToNull(item.getId());
            item.setScheduledTime(null);
        }
        item.setType(null).setCreateTime(null);
        return itemMapper.updateById(item) > 0;
    }

    @Override
    public boolean editItemVisibility(int itemId, int visibility) {
        if (visibility < 0 || visibility > 1)
            throw new ServiceException("可见度必须为0或1  0: 公开 1: 私有");

        PersonalItem personalItem = getPersonalItemWithCheckingPresentAndOwner(itemId);
        personalItem.setVisibility(visibility);

        return personalItemMapper.updateById(personalItem) > 0;
    }

    @Override
    public boolean editItemBelongTo(int itemId, int blogId) {
        PersonalItem personalItem = getPersonalItemWithCheckingPresentAndOwner(itemId);

        if (blogId == -1) {
            return personalItemMapper.updateBlogIdToNull(itemId);
        }
        Blog blog = blogMapper.selectById(blogId);
        if (blog == null)
            throw new ServiceException("blog_id=" + blogId + "博客不存在");
        if (!blog.getUserId().equals(UserInfo.get()))
            throw new ServiceException("blog_id=" + blogId + "的博客不属于你");

        personalItem.setBlogId(blogId != -1 ? blogId : null);
        return personalItemMapper.updateById(personalItem) > 0;
    }

    @Override
    public List<PersonalItemDto> getTodayItem() {
        QueryWrapper<PersonalItemDto> queryWrapper = new QueryWrapper<PersonalItemDto>()
                .eq("user_id", UserInfo.get())        // 自己的
                .eq("type", 0)
                .and(wrapper ->
                        wrapper.likeRight("scheduled_time", LocalDate.now())
                                .or().eq("is_today", true))
                .orderByDesc("update_time");
        List<PersonalItemDto> personalItemDtos = personalItemMapper.selectTodayDTO(queryWrapper);
        return personalItemDtos;
    }

    @Override
    public boolean reverseIsToday(int itemId) {
        Item item = getItemWithCheckingPresentAndOwner(itemId);
        item.setIsToday(!item.getIsToday());
        return itemMapper.updateById(item) > 0;
    }

    @Transactional
    @Override
    public boolean reverseIsDone(int itemId) {
        Item item = getItemWithCheckingPresentAndOwner(itemId);
        if (!item.getIsDone())  // 未完成 -> 已完成
            return itemMapper.updateIsDoneAndFinishTime(itemId);
        else
            return itemMapper.updateNotDoneAndFinishTime(itemId);
    }

    /**
     * 获取item 同时检查是否存在与用户权限
     */
    private Item getItemWithCheckingPresentAndOwner(int itemId) {
        Item item = itemMapper.selectById(itemId);
        if (item == null)
            throw new ServiceException("不存在item_id=" + itemId + "的事项");

        PersonalItem personalItem = personalItemMapper.selectOne(new QueryWrapper<PersonalItem>().eq("item_id", itemId));
        if (!personalItem.getUserId().equals(UserInfo.get()))
            throw new ServiceException("item_id=" + itemId + "的事项不属于你");
        return item;
    }

    /**
     * 获取personalItem 同时检查是否存在与用户权限
     */
    private PersonalItem getPersonalItemWithCheckingPresentAndOwner(int itemId) {
        PersonalItem personalItem = personalItemMapper.selectOne(new QueryWrapper<PersonalItem>().eq("item_id", itemId));
        if (personalItem == null)
            throw new ServiceException("item_id=" + itemId + "的个人事项不存在");
        if (!personalItem.getUserId().equals(UserInfo.get()))
            throw new ServiceException("item_id=" + itemId + "的事项不属于你");
        return personalItem;
    }
}
