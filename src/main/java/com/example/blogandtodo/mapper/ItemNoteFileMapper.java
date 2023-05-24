package com.example.blogandtodo.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.blogandtodo.entity.ItemNoteFile;
import com.example.blogandtodo.entity.RelationUserGroup;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ItemNoteFileMapper extends BaseMapper<ItemNoteFile> {
    @Delete("delete from item_note_file where id = #{id}")
    void deleteByIdTrigger(Integer id);
}
