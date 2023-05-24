package com.example.blogandtodo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.blogandtodo.entity.ItemNote;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ItemNoteService extends IService<ItemNote> {

    boolean editNote(ItemNote itemNote);

    boolean editGroupNote(int groupId, ItemNote itemNote);

    List<String> saveFile(int itemNoteId, MultipartFile[] files);
}
