package com.example.blogandtodo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.blogandtodo.common.Result;
import com.example.blogandtodo.dto.BlogDto;
import com.example.blogandtodo.entity.Blog;
import com.example.blogandtodo.entity.ItemNote;
import com.example.blogandtodo.mapper.ItemNoteMapper;
import com.example.blogandtodo.service.BlogService;
import com.example.blogandtodo.service.ItemNoteService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/note")
public class ItemNoteController {
    @Autowired
    private ItemNoteService itemNoteService;

    @ApiOperation(value = "笔记修改", notes = "限自己的笔记 当前只允许修改文字 稍后会提供图片上传方式 [笔记会在任意方式获取blog或个人事项时自动获取， 会在创建/删除个人事项时自动创建/删除]")
    @PutMapping
    public Result editPersonalNote(@RequestBody ItemNote itemNote) {
        boolean res = itemNoteService.editNote(itemNote);
        return res ? Result.success().message("修改成功") : Result.error();
    }

    @ApiOperation(value = "群组笔记修改", notes = "限自己加入群组的笔记")
    @PutMapping("/{group_id}")
    public Result editGroupNote(@PathVariable("group_id") int groupId, @RequestBody ItemNote itemNote) {
        boolean res = itemNoteService.editGroupNote(groupId, itemNote);
        return res ? Result.success().message("修改成功") : Result.error();
    }

    @ApiOperation(value = "笔记附件上传[不区分群组个人]", notes = "如果笔记中包含文字外的附件, 则应当通过本接口上传文件并获取文件地址")
    @PostMapping("/file/{item_note_id}")
    public Result postFile(@PathVariable("item_note_id") int itemNoteId, @RequestPart MultipartFile[] files) {
        List<String> res = itemNoteService.saveFile(itemNoteId, files);
        return res != null ? Result.success().data("urls", res) : Result.error();
    }
}
