package com.example.blogandtodo;

import com.example.blogandtodo.dto.BlogDto;
import com.example.blogandtodo.dto.PersonalItemDto;
import com.example.blogandtodo.mapper.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.util.List;

@Slf4j
@SpringBootTest
public class MapperTest {
    @Autowired
    private BlogMapper blogMapper;
    @Autowired
    private BlogRecordMapper blogRecordMapper;
    @Autowired
    private GroupItemMapper groupItemMapper;
    @Autowired
    private GroupMapper groupMapper;
    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private ItemNoteMapper itemNoteMapper;
    @Autowired
    private MessageMapper messageMapper;
    @Autowired
    private PersonalItemMapper personalItemMapper;
    @Autowired
    private RelationUserGroupMapper relationUserGroupMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ItemNoteFileMapper itemNoteFileMapper;

    @Test
    void mapperTest() {
//        groupItemMapper.selectOneDtos(1, 2, "TODO", null, true);
        itemNoteFileMapper.deleteByIdTrigger(21);
    }
}
