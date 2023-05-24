-- 笔记新增附件触发器函数
CREATE OR REPLACE FUNCTION update_image_count()
    RETURNS TRIGGER AS
$$
BEGIN
    -- 在item_note表中找到对应的记录，并将image_count字段加一
    UPDATE item_note
    SET image_count = item_note.image_count + 1
    WHERE id = NEW.item_note_id;

    RETURN NEW;
END;
$$
    LANGUAGE plpgsql;

-- 笔记新增附件触发器
CREATE TRIGGER update_item_note
    AFTER INSERT ON item_note_file
    FOR EACH ROW
EXECUTE PROCEDURE update_image_count();


-- 笔记删除附件触发器函数
CREATE OR REPLACE FUNCTION decrease_image_count()
    RETURNS TRIGGER AS
$$
BEGIN
    -- 在item_note表中找到对应的记录，并将image_count字段减一
    UPDATE item_note
    SET image_count = item_note.image_count - 1
    WHERE id = OLD.item_note_id;

    RETURN OLD;
END;
$$
    LANGUAGE plpgsql;

-- 笔记删除附件触发器
CREATE TRIGGER decrease_item_note
    AFTER DELETE ON item_note_file
    FOR EACH ROW
EXECUTE PROCEDURE decrease_image_count();
