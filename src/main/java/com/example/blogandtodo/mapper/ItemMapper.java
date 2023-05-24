package com.example.blogandtodo.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.blogandtodo.entity.Item;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ItemMapper extends BaseMapper<Item> {
    @Update("update personal_item set blog_id = null where blog_id = #{blog_id}")
    int updateBlogIdToNull(@Param("blog_id") int blogId);   // 找到所有包含blog.id的blog_record

    @Update("update item set scheduled_time = null where id = #{item_id}")
    void updateScheduledTimeToNull(@Param("item_id") int itemId);

    /**
     * 将所有事项的is_today变为false
     * 周期 0 点
     */
    @Update("update item set is_today = false")
    boolean updateIsTodayToFalse();

    /**
     * 把scheduled_time为今天的个人事项 is_today变为true
     * 周期 0 点
     */
    @Update("update item set is_today = true where is_done = false and substr(scheduled_time, 1, 10) = current_date")
    boolean updateIsTodayToTrue();

    /**
     * 按id将item设置为完成状态并记录finish_time为当前时间
     */
    @Update("""
            update item
            set is_done = true, finish_time = now()
            where id = #{item_id}
            """)
    boolean updateIsDoneAndFinishTime(@Param("item_id") int itemId);

    /**
     *  按id将item清除完成状态并置finish_time = null
     */
    @Update("""
            update item
            set is_done = false, finish_time = null
            where id = #{item_id}
            """)
    boolean updateNotDoneAndFinishTime(@Param("item_id") int itemId);
}
