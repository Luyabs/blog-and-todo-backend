package com.example.blogandtodo.common;

import com.example.blogandtodo.mapper.ItemMapper;
import com.example.blogandtodo.service.ItemNoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTask {
    @Autowired
    private ItemMapper itemMapper;

    /**
     * 每日0点更新
     */
    @Scheduled(cron = "0 0 0 1/1 * ?")
    public boolean resetIsToday() {
        return itemMapper.updateIsTodayToFalse() && itemMapper.updateIsTodayToTrue();
    }
}
