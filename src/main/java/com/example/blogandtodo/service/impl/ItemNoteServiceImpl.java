package com.example.blogandtodo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.blogandtodo.common.UserInfo;
import com.example.blogandtodo.common.exception.ServiceException;
import com.example.blogandtodo.dto.BlogDto;
import com.example.blogandtodo.entity.*;
import com.example.blogandtodo.mapper.*;
import com.example.blogandtodo.service.BlogService;
import com.example.blogandtodo.service.GroupItemService;
import com.example.blogandtodo.service.ItemNoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class ItemNoteServiceImpl extends ServiceImpl<ItemNoteMapper, ItemNote> implements ItemNoteService {
    @Value("${base-url.note-file}")
    private String noteFileBaseUrl;  // 附件所在位置

    @Autowired
    private ItemNoteMapper itemNoteMapper;

    @Autowired
    private PersonalItemMapper personalItemMapper;

    @Autowired
    private RelationUserGroupMapper relationUserGroupMapper;

    @Autowired
    private ItemNoteFileMapper itemNoteFileMapper;

    @Override
    public boolean editNote(ItemNote itemNote) {
        ItemNote target = itemNoteMapper.selectById(itemNote.getId());
        if (target == null)
            throw new ServiceException("不存在id=" + itemNote.getId() + "的笔记");

        PersonalItem safetyCheck = personalItemMapper.selectOne(new QueryWrapper<PersonalItem>().eq("user_id", UserInfo.get()).eq("item_id", target.getItemId()));
        if (safetyCheck == null)
            throw new ServiceException("你不是id=" + target.getItemId() + "事项拥有者");

        target.setContent(itemNote.getContent());


        if (itemNoteMapper.updateById(target) <= 0)
            throw new ServiceException("未知异常, 更新失败, 联系后端");

        cleanTrashFile(target);           // 笔记更新后会对图片文件做检查 如果有不存在的文件则删除
        return true;
    }

    @Override
    @Transactional
    public boolean editGroupNote(int groupId, ItemNote itemNote) {
        ItemNote target = itemNoteMapper.selectById(itemNote.getId());
        if (target == null)
            throw new ServiceException("不存在id=" + itemNote.getId() + "的笔记");

        PersonalItem wrongUrlCheck = personalItemMapper.selectOne(new QueryWrapper<PersonalItem>().eq("item_id", target.getItemId()));
        if (wrongUrlCheck != null)
            throw new ServiceException("此URL不能用来修改个人事项");

        RelationUserGroup one = relationUserGroupMapper.selectOne(
                new QueryWrapper<RelationUserGroup>()
                        .eq("group_Id", groupId)
                        .eq("user_id", UserInfo.get())
        );
        if (one == null)
            throw new ServiceException("您未加入group_id=" + groupId + "的群组, 无权修改此笔记");

        // TODO: 使用触发器更新item_note的图片数量: 当update item_note的content时 自动查到content里图片数量并更新
        target.setContent(itemNote.getContent());

        if (itemNoteMapper.updateById(target) <= 0)
            throw new ServiceException("未知异常, 更新失败, 联系后端");

        cleanTrashFile(target);           // 笔记更新后会对图片文件做检查 如果有不存在的文件则删除
        return true;
    }

    @Override
    @Transactional
    public List<String> saveFile(int itemNoteId, MultipartFile[] files) {
        //TODO: 可能需要限制文件上传大小 + 验证成员身份 现在暂时不做
        if (itemNoteMapper.selectById(itemNoteId) == null)
            throw new ServiceException("不存在iiem_note_id=" + itemNoteId + "的笔记");

        new File(noteFileBaseUrl).mkdirs();  // 没有文件夹就创一个

        List<String> newFileUrlList = new ArrayList<>();
        for (MultipartFile file : files) {
            String originFileName = file.getOriginalFilename();   // 原文件名
            String newFilePath;     // 新文件名(磁盘中)
            String newFileUrl;     // 新文件名(给外部访问用的路径)
            try {
                if (originFileName == null)
                    throw new ServiceException("文件名错误");
                int index = originFileName.lastIndexOf('.');
                String suffix = originFileName.substring(index + 1);

                // 填入记录表
                ItemNoteFile newFile = new ItemNoteFile().setItemNoteId(itemNoteId);
                boolean res = itemNoteFileMapper.insert(newFile) > 0;
                if (!res)
                    throw new ServiceException("向图片记录表插入数据失败");

                // 更新URL记录
                newFileUrl = "/note-file/" + newFile.getId() + "." + suffix;
                res = itemNoteFileMapper.updateById(newFile.setUrl(newFileUrl)) > 0;
                if (!res)
                    throw new ServiceException("更新文件url时发生异常");

                // 存到磁盘
                newFilePath = noteFileBaseUrl + newFile.getId() + "." + suffix;
                File diskFile = new File(newFilePath);
                file.transferTo(diskFile);

                newFileUrlList.add(newFileUrl);
            } catch (Exception ex) {
                log.error(ex.getMessage());
                throw new ServiceException(ex.getMessage());
            }
        }
        return newFileUrlList;
    }

    /**
     * 清除磁盘上存在 但笔记中未使用的垃圾文件
     */
    private void cleanTrashFile(ItemNote itemNote) {
        // 笔记更新后会对图片文件做检查 如果有不存在的文件则删除
        String content = itemNote.getContent();
        // 把有关这个事项的文件查出来 逐一比对笔记中是否涉及了该文件
        List<ItemNoteFile> itemNoteFiles = itemNoteFileMapper.selectList(new QueryWrapper<ItemNoteFile>().eq("item_note_id", itemNote.getId()));
        if (content != null) {
            for (ItemNoteFile itemNoteFile : itemNoteFiles) {
                if (!content.contains(itemNoteFile.getUrl().substring(1))) { // 如果笔记内没有这个文件 则删除
                    itemNoteFileMapper.deleteByIdTrigger(itemNoteFile.getId());
                    log.error(noteFileBaseUrl + itemNoteFile.getUrl().split("/note-file/")[1]);
                    new File(noteFileBaseUrl + itemNoteFile.getUrl().split("/note-file/")[1]).delete();
                }
            }
        }
    }

    /**
     * 每天0点扫垃圾
     */
    @Scheduled(cron = "0 0 0 1/1 * ?")
    public void dailyTrashClean() {
        List<ItemNote> noteList = itemNoteMapper.selectList(null);
        for (ItemNote note : noteList) {
            cleanTrashFile(note);
        }
    }
}
