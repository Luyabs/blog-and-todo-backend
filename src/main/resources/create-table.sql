-- create database blog_and_todo;

create table t_user (				-- 用户
    id int primary key,     -- 主键
    wx_id varchar(80) unique,   -- 微信id
    wx_name varchar(80) ,    -- 微信名
    authority int default 0, -- 身份 0: 管理员 1: 普通用户
    is_banned bool default false, 	-- 是否被封禁

    description varchar(160),   -- 个人简介
    email varchar(80),			-- 邮箱
    gender varchar(10),			-- 性别

    create_time timestamp,  -- 创建时间
    update_time timestamp,   -- 更新时间

    check ( authority in (0, 1) )
);

create table t_group (			-- 群组
    id int primary key,     -- 主键
    group_name varchar(80) not null,    -- 群组名
    password varchar(120),
    description varchar(160),   -- 群组简介

    create_time timestamp,  -- 创建时间
    update_time timestamp   -- 更新时间
);

create table relation_user_group (		-- 用户群组关系
    id int primary key,     -- 主键
    user_id int not null,    -- 用户id
    group_id int not null,   -- 群组id
    authority int default 0,  -- 用户在群组的身份 默认0: 无特殊权限
    status int default 0,	-- 用户在群的状态 0: 在群 1: 退群 2: 拉黑

    create_time timestamp,  -- 创建时间
    update_time timestamp,   -- 更新时间

    check ( status in (0, 1, 2) ),
    foreign key(user_id) references t_user(id),
    foreign key(group_id) references t_group(id)
);

create table blog (					-- 博客
    id int primary key,     -- 主键
    user_id int not null,    -- 用户id
    header varchar(80),   -- 博客标题
    description varchar(160),   -- 博客简介
    tag varchar(160),   -- 博客标签
    status int default 1,			-- 状态 0: 需要审核 1: 正常 2: 正在审核

    create_time timestamp,  -- 创建时间
    update_time timestamp,   -- 更新时间

    check ( status in (0, 1, 2) ),
    foreign key(user_id) references t_user(id)
);

create table item (					-- 事项
    id int primary key,     -- 主键
    content varchar(300),   -- 事项内容
    tag varchar(80),   -- 标签`
    type int not null,   -- 0: 群组事项 1: 个人事项
    is_done bool default false,   -- 是否完成
    scheduled_time timestamp,		-- 计划中完成时间
    finish_time timestamp,		-- 实际完成时间

    create_time timestamp,  -- 创建时间
    update_time timestamp,   -- 更新时间

    check ( type in (0, 1) )
);

create table personal_item (	-- 个人事项
    id int primary key,     -- 主键
    item_id int,	-- 事项id
    blog_id int,	-- 博客id
    visibility int default 1,   -- 0: 公开 1: 私有

    create_time timestamp,  -- 创建时间
    update_time timestamp,   -- 更新时间

    check ( visibility in (0, 1) ),
    foreign key(item_id) references "item"(id),
    foreign key(blog_id) references "blog"(id)
);

create table group_item (		-- 群组事项
    id int primary key,     -- 主键
    item_id int,	-- 事项id
    group_id int,	-- 组id
    edit_time int default 0,   -- 编辑次数

    create_time timestamp,  -- 创建时间
    update_time timestamp,   -- 更新时间

    foreign key(item_id) references "item"(id),
    foreign key(group_id) references t_group(id)
);

create table item_note (		-- 关于事项的笔记
    id int primary key,     -- 主键
    item_id int,	-- 事项id
    content varchar(800),   -- 笔记内容(允许笔记内容中出现图片地址)
    image_count int default 0, -- 笔记中图片出现数量

    create_time timestamp,  -- 创建时间
    update_time timestamp,   -- 更新时间

    foreign key(item_id) references "item"(id)
);

create table image (				-- 图片存放记录
    id int primary key,     -- 主键
    item_note_id int,	-- 笔记id
    url varchar(200) not null,   -- 图片在服务器上的地址
    location int not null,		-- 图片出现在笔记中的位置

    create_time timestamp,  -- 创建时间
    update_time timestamp,   -- 更新时间

    foreign key(item_note_id) references "item_note"(id)
);

create table blog_record (	-- 博客浏览记录
    id int primary key,     -- 主键
    user_id int,	-- 用户id
    blog_id int,	-- 博客id
    action varchar(80),		-- 交互行为
    is_deleted bool default false,		-- 是否被用户逻辑删除

    create_time timestamp,  -- 创建时间
    update_time timestamp,   -- 更新时间

    foreign key(user_id) references t_user(id),
    foreign key(blog_id) references "blog"(id)
);

create table message (			-- 信息(私信或通知)
    id int primary key,     -- 主键
    from_user int,	-- from用户id
    to_user int,	-- to用户id
    content varchar(300),		-- 信息内容
    type int not null,		-- 信息类型 0: 私信 1: 群组 2: 管理员消息
    is_deleted bool default false,		-- 是否被用户逻辑删除

    create_time timestamp,  -- 创建时间
    update_time timestamp,   -- 更新时间

    check ( type in (0, 1, 2) ),
    foreign key(from_user) references t_user(id),
    foreign key(to_user) references t_user(id)
);

