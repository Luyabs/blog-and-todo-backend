package com.example.blogandtodo.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.blogandtodo.entity.Item;
import com.example.blogandtodo.entity.ItemNote;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ItemNoteMapper extends BaseMapper<ItemNote> {
    @Select("""
            select item_note.*
            from item_note
            where item_id = #{item_id}
            """)
    ItemNote selectByItemId(@Param("item_id") int itemId);
}
