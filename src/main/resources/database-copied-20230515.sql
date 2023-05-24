/*
 Navicat Premium Data Transfer

 Source Server         : SHU-DB-Course
 Source Server Type    : PostgreSQL
 Source Server Version : 90204
 Source Host           : 123.249.9.1:26000
 Source Catalog        : blog_and_todo
 Source Schema         : public

 Target Server Type    : PostgreSQL
 Target Server Version : 90204
 File Encoding         : 65001

 Date: 15/05/2023 11:17:37
*/


-- ----------------------------
-- Sequence structure for blog_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."blog_id_seq";
CREATE SEQUENCE "public"."blog_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for blog_record_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."blog_record_id_seq";
CREATE SEQUENCE "public"."blog_record_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for group_item_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."group_item_id_seq";
CREATE SEQUENCE "public"."group_item_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for image_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."image_id_seq";
CREATE SEQUENCE "public"."image_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for item_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."item_id_seq";
CREATE SEQUENCE "public"."item_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for item_note_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."item_note_id_seq";
CREATE SEQUENCE "public"."item_note_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for message_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."message_id_seq";
CREATE SEQUENCE "public"."message_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for personal_item_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."personal_item_id_seq";
CREATE SEQUENCE "public"."personal_item_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for relation_user_group_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."relation_user_group_id_seq";
CREATE SEQUENCE "public"."relation_user_group_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for t_group_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."t_group_id_seq";
CREATE SEQUENCE "public"."t_group_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for t_user_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."t_user_id_seq";
CREATE SEQUENCE "public"."t_user_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Table structure for blog
-- ----------------------------
DROP TABLE IF EXISTS "public"."blog";
CREATE TABLE "public"."blog" (
  "id" int4 NOT NULL DEFAULT nextval('blog_id_seq'::regclass),
  "user_id" int4 NOT NULL,
  "header" varchar(80) COLLATE "pg_catalog"."default",
  "description" varchar(160) COLLATE "pg_catalog"."default",
  "tag" varchar(160) COLLATE "pg_catalog"."default",
  "status" int4 DEFAULT 1,
  "create_time" timestamp(6),
  "update_time" timestamp(6)
)
;

-- ----------------------------
-- Records of blog
-- ----------------------------
INSERT INTO "public"."blog" VALUES (18, 1, '5.13blog build12', 'blogtest', NULL, 1, '2023-05-13 15:21:17.596063', '2023-05-14 17:42:44.109022');
INSERT INTO "public"."blog" VALUES (14, 2, '博客7', '这是描述', '1234577777', 1, '2023-05-06 15:46:02.059879', '2023-05-06 15:46:02.059879');
INSERT INTO "public"."blog" VALUES (9, 1, '博客6', '这是描述', NULL, 1, '2023-04-27 11:47:20.523817', '2023-05-03 14:21:28.024471');
INSERT INTO "public"."blog" VALUES (8, 1, '博客4', '这是描述', '1234577777', 1, '2023-04-27 00:46:45.024568', '2023-04-27 13:59:18.173651');
INSERT INTO "public"."blog" VALUES (7, 1, '博客2', '这是描述', '123', 1, '2023-04-26 21:45:56.605003', '2023-04-26 21:45:56.605003');
INSERT INTO "public"."blog" VALUES (13, 2, '博客5', '这是描述', '1234577777', 1, '2023-05-10 14:21:24.754', '2023-05-10 14:21:24.754');
INSERT INTO "public"."blog" VALUES (15, 1, '我的今日博客', '请添加todo事项', NULL, 1, '2023-05-10 15:53:40.22552', '2023-05-10 15:53:40.22552');
INSERT INTO "public"."blog" VALUES (16, 1, '我的今日博客', '请添加todo事项', NULL, 1, '2023-05-11 15:27:27.671783', '2023-05-11 15:27:27.67181');
INSERT INTO "public"."blog" VALUES (17, 1, '5.12blog页面创建', '5.12blog测试', NULL, 1, '2023-05-12 21:44:26.761525', '2023-05-12 23:19:21.915456');

-- ----------------------------
-- Table structure for blog_record
-- ----------------------------
DROP TABLE IF EXISTS "public"."blog_record";
CREATE TABLE "public"."blog_record" (
  "id" int4 NOT NULL DEFAULT nextval('blog_record_id_seq'::regclass),
  "user_id" int4,
  "blog_id" int4,
  "action" varchar(80) COLLATE "pg_catalog"."default",
  "is_deleted" bool DEFAULT false,
  "create_time" timestamp(6),
  "update_time" timestamp(6)
)
;

-- ----------------------------
-- Records of blog_record
-- ----------------------------

-- ----------------------------
-- Table structure for group_item
-- ----------------------------
DROP TABLE IF EXISTS "public"."group_item";
CREATE TABLE "public"."group_item" (
  "id" int4 NOT NULL DEFAULT nextval('group_item_id_seq'::regclass),
  "item_id" int4,
  "group_id" int4,
  "edit_time" int4 DEFAULT 0,
  "create_time" timestamp(6),
  "update_time" timestamp(6)
)
;

-- ----------------------------
-- Records of group_item
-- ----------------------------
INSERT INTO "public"."group_item" VALUES (1, 10, 2, 0, '2023-04-26 16:04:13.898169', '2023-04-26 16:04:13.898169');
INSERT INTO "public"."group_item" VALUES (3, 54, 2, 0, '2023-05-14 23:35:21.337233', '2023-05-14 23:35:21.337233');
INSERT INTO "public"."group_item" VALUES (2, 53, 2, 11, '2023-05-14 23:35:19.493224', '2023-05-14 23:40:04.734889');

-- ----------------------------
-- Table structure for image
-- ----------------------------
DROP TABLE IF EXISTS "public"."image";
CREATE TABLE "public"."image" (
  "id" int4 NOT NULL DEFAULT nextval('image_id_seq'::regclass),
  "item_note_id" int4,
  "url" varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
  "location" int4 NOT NULL,
  "create_time" timestamp(6),
  "update_time" timestamp(6)
)
;

-- ----------------------------
-- Records of image
-- ----------------------------

-- ----------------------------
-- Table structure for item
-- ----------------------------
DROP TABLE IF EXISTS "public"."item";
CREATE TABLE "public"."item" (
  "id" int4 NOT NULL DEFAULT nextval('item_id_seq'::regclass),
  "content" varchar(300) COLLATE "pg_catalog"."default",
  "tag" varchar(80) COLLATE "pg_catalog"."default",
  "type" int4 NOT NULL,
  "is_done" bool DEFAULT false,
  "scheduled_time" timestamp(6),
  "finish_time" timestamp(6),
  "create_time" timestamp(6),
  "update_time" timestamp(6),
  "is_today" bool DEFAULT false
)
;

-- ----------------------------
-- Records of item
-- ----------------------------
INSERT INTO "public"."item" VALUES (45, '完成blog部分', NULL, 0, 'f', '2023-05-13 23:59:59', NULL, '2023-05-12 21:42:59.511802', '2023-05-12 22:19:07.58058', 'f');
INSERT INTO "public"."item" VALUES (46, 'blog测试', NULL, 0, 't', NULL, NULL, '2023-05-12 21:44:23.42876', '2023-05-12 21:49:50.809085', 'f');
INSERT INTO "public"."item" VALUES (31, '3333', NULL, 1, 't', NULL, NULL, '2023-05-06 19:27:34.818054', '2023-05-06 20:03:50.659163', 'f');
INSERT INTO "public"."item" VALUES (29, '1111', NULL, 1, 't', NULL, NULL, '2023-05-06 19:27:19.038498', '2023-05-06 20:03:54.898491', 'f');
INSERT INTO "public"."item" VALUES (30, '2222', NULL, 1, 'f', NULL, NULL, '2023-05-06 19:27:25.775812', '2023-05-06 20:27:54.289703', 'f');
INSERT INTO "public"."item" VALUES (17, 'TODO事项11', NULL, 1, 't', NULL, '2023-05-10 22:43:25.033473', '2023-05-02 21:42:19.802312', '2023-05-06 15:48:17.533017', 'f');
INSERT INTO "public"."item" VALUES (28, 'TODO事项15', 'sdasdasdjahsdjkasdsadsdaadsadadasd', 1, 'f', '2023-05-07 22:50:19', NULL, '2023-05-06 17:08:32.253468', '2023-05-07 23:07:42.084085', 'f');
INSERT INTO "public"."item" VALUES (41, 'test1', NULL, 0, 'f', '2023-05-11 23:59:59', NULL, '2023-05-10 17:03:36.299393', '2023-05-11 15:38:47.980535', 'f');
INSERT INTO "public"."item" VALUES (18, 'TODO事项10', NULL, 1, 't', NULL, NULL, '2023-05-02 21:59:29.815676', '2023-05-06 15:48:17.042695', 'f');
INSERT INTO "public"."item" VALUES (19, 'TODO事项3', NULL, 1, 'f', NULL, NULL, '2023-05-02 22:03:31.208951', '2023-05-10 16:47:32.63877', 'f');
INSERT INTO "public"."item" VALUES (9, 'TODO事项7', '123123123123123', 1, 't', NULL, NULL, NULL, '2023-05-04 21:06:38.518736', 'f');
INSERT INTO "public"."item" VALUES (35, 'ssss', NULL, 1, 'f', NULL, NULL, '2023-05-10 15:31:43.375097', '2023-05-10 16:47:34.122994', 'f');
INSERT INTO "public"."item" VALUES (26, 'TODO事项14', 'sdasdasdjahsdjkasdsadsadadasd', 1, 'f', '2023-05-10 16:45:20', NULL, '2023-05-06 15:44:16.332586', '2023-05-06 16:50:12.935694', 'f');
INSERT INTO "public"."item" VALUES (54, '测试群组事项1', '测试群组事项1', 1, 'f', NULL, NULL, '2023-05-14 23:35:21.304907', '2023-05-14 23:35:21.304907', 'f');
INSERT INTO "public"."item" VALUES (53, '测试群组事项2', '测试群组事项2', 1, 'f', NULL, NULL, '2023-05-14 23:35:19.460538', '2023-05-14 23:40:04.644132', 'f');
INSERT INTO "public"."item" VALUES (27, 'TODO事项13', 'sdasdasdjahsdjkasdsadsdaadsadadasd', 1, 'f', '2023-05-10 16:15:23', NULL, '2023-05-06 16:34:30.819935', '2023-05-06 16:34:30.819935', 'f');
INSERT INTO "public"."item" VALUES (24, 'TODO事项8', 'sdasdasdjahsdjkasdsadsadadasd', 1, 'f', '2023-05-10 16:47:31', NULL, '2023-05-06 15:00:39.957052', '2023-05-06 15:02:56.19632', 'f');
INSERT INTO "public"."item" VALUES (32, 'TODO事项6', NULL, 1, 'f', NULL, NULL, '2023-05-10 15:23:52.054053', '2023-05-10 16:48:51.990512', 'f');
INSERT INTO "public"."item" VALUES (40, 'test1', NULL, 0, 'f', '2023-05-11 23:59:59', NULL, '2023-05-10 16:51:15.15735', '2023-05-10 17:02:49.640595', 'f');
INSERT INTO "public"."item" VALUES (43, 'test3', NULL, 0, 'f', '2023-05-11 23:59:59', NULL, '2023-05-10 17:03:56.474437', '2023-05-10 17:04:40.879712', 'f');
INSERT INTO "public"."item" VALUES (42, 'test2', NULL, 0, 't', '2023-05-11 23:59:59', NULL, '2023-05-10 17:03:45.380866', '2023-05-11 16:02:36.874764', 'f');
INSERT INTO "public"."item" VALUES (10, 'TODO事项1', NULL, 1, 't', '2023-05-12 23:59:59', NULL, '2023-04-26 16:04:13.861829', '2023-05-11 14:52:41.684738', 'f');
INSERT INTO "public"."item" VALUES (21, 'TODO事项5', NULL, 1, 'f', '2023-05-03 23:59:59', NULL, '2023-05-02 22:22:38.291658', '2023-05-11 14:52:55.354195', 'f');
INSERT INTO "public"."item" VALUES (37, '1111', NULL, 1, 'f', '2023-05-10 23:59:59', NULL, '2023-05-10 16:08:45.771044', '2023-05-11 14:53:11.183885', 'f');
INSERT INTO "public"."item" VALUES (49, 'test1', NULL, 0, 't', NULL, NULL, '2023-05-13 16:14:38.586231', '2023-05-13 18:30:09.809832', 'f');
INSERT INTO "public"."item" VALUES (48, 'test2', NULL, 0, 't', NULL, NULL, '2023-05-13 16:06:23.124799', '2023-05-13 16:11:09.964258', 'f');
INSERT INTO "public"."item" VALUES (47, 'blog', NULL, 0, 't', NULL, NULL, '2023-05-13 15:21:15.420607', '2023-05-13 16:11:34.829025', 'f');
INSERT INTO "public"."item" VALUES (52, 'test4', NULL, 0, 't', NULL, NULL, '2023-05-13 16:15:01.194256', '2023-05-13 16:15:33.647785', 'f');
INSERT INTO "public"."item" VALUES (51, 'test3', NULL, 0, 't', NULL, NULL, '2023-05-13 16:14:51.687002', '2023-05-13 16:15:40.481139', 'f');
INSERT INTO "public"."item" VALUES (50, 'test2', NULL, 0, 't', NULL, NULL, '2023-05-13 16:14:44.690825', '2023-05-13 16:15:46.076729', 'f');

-- ----------------------------
-- Table structure for item_note
-- ----------------------------
DROP TABLE IF EXISTS "public"."item_note";
CREATE TABLE "public"."item_note" (
  "id" int4 NOT NULL DEFAULT nextval('item_note_id_seq'::regclass),
  "item_id" int4,
  "content" varchar(1500) COLLATE "pg_catalog"."default",
  "image_count" int4 DEFAULT 0,
  "create_time" timestamp(6),
  "update_time" timestamp(6)
)
;

-- ----------------------------
-- Records of item_note
-- ----------------------------
INSERT INTO "public"."item_note" VALUES (2, 26, '我是笔记', 0, '2023-05-06 15:44:16.522713', '2023-05-06 15:45:24.188068');
INSERT INTO "public"."item_note" VALUES (37, 48, '<p>ssssssssss</p>', 0, '2023-05-13 16:06:23.18207', '2023-05-13 16:11:09.966037');
INSERT INTO "public"."item_note" VALUES (6, 10, '<p>笔记6</p>', 0, '2023-05-06 16:52:55', '2023-05-11 14:52:41.655369');
INSERT INTO "public"."item_note" VALUES (29, 40, NULL, 0, '2023-05-10 16:51:15.216616', '2023-05-10 17:02:49.639299');
INSERT INTO "public"."item_note" VALUES (36, 47, '<p>完成blog页面设计</p>', 0, '2023-05-13 15:21:15.473951', '2023-05-13 16:11:34.827354');
INSERT INTO "public"."item_note" VALUES (12, 21, '<p>笔记3</p>', 0, '2023-05-06 16:52:55', '2023-05-11 14:52:55.357578');
INSERT INTO "public"."item_note" VALUES (32, 43, NULL, 0, '2023-05-10 17:03:56.536067', '2023-05-10 17:04:40.880468');
INSERT INTO "public"."item_note" VALUES (26, 37, '<p>asdsdsaddadad</p>', 0, '2023-05-10 16:08:45.830185', '2023-05-11 14:53:11.197374');
INSERT INTO "public"."item_note" VALUES (10, 18, '笔记4', 0, '2023-05-06 16:52:55', '2023-05-06 16:52:55');
INSERT INTO "public"."item_note" VALUES (9, 17, '笔记7', 0, '2023-05-06 16:52:55', '2023-05-06 16:52:55');
INSERT INTO "public"."item_note" VALUES (14, 9, '笔记2', 0, '2023-05-06 16:52:55', '2023-05-06 16:52:55');
INSERT INTO "public"."item_note" VALUES (3, 27, '笔记1', 0, '2023-05-06 16:34:30.950849', '2023-05-06 16:34:30.950849');
INSERT INTO "public"."item_note" VALUES (41, 52, '<p>来看看究竟如何</p>', 0, '2023-05-13 16:15:01.250797', '2023-05-13 16:15:33.661148');
INSERT INTO "public"."item_note" VALUES (17, 28, NULL, 0, '2023-05-06 17:08:32.407901', '2023-05-06 17:08:32.407901');
INSERT INTO "public"."item_note" VALUES (18, 29, NULL, 0, '2023-05-06 19:27:19.101144', '2023-05-06 19:27:19.101152');
INSERT INTO "public"."item_note" VALUES (19, 30, NULL, 0, '2023-05-06 19:27:25.833124', '2023-05-06 19:27:25.833133');
INSERT INTO "public"."item_note" VALUES (20, 31, NULL, 0, '2023-05-06 19:27:34.877835', '2023-05-06 19:27:34.877842');
INSERT INTO "public"."item_note" VALUES (40, 51, '<p>来看看吧</p>', 0, '2023-05-13 16:14:51.748178', '2023-05-13 16:15:40.46986');
INSERT INTO "public"."item_note" VALUES (39, 50, '<p>来看看吧</p>', 0, '2023-05-13 16:14:44.747123', '2023-05-13 16:15:46.076273');
INSERT INTO "public"."item_note" VALUES (38, 49, '<p>来看看吧</p>', 0, '2023-05-13 16:14:38.643544', '2023-05-13 18:30:09.897333');
INSERT INTO "public"."item_note" VALUES (43, 54, NULL, 0, '2023-05-14 23:35:21.368698', '2023-05-14 23:35:21.368698');
INSERT INTO "public"."item_note" VALUES (42, 53, '这是群组笔记', 0, '2023-05-14 23:35:19.527435', '2023-05-14 23:53:25.333505');
INSERT INTO "public"."item_note" VALUES (31, 42, '<p><br></p>', 0, '2023-05-10 17:03:45.441917', '2023-05-11 16:02:36.900767');
INSERT INTO "public"."item_note" VALUES (11, 19, '<p>笔记12</p>', 0, '2023-05-06 16:52:55', '2023-05-10 16:47:32.613402');
INSERT INTO "public"."item_note" VALUES (24, 35, '<p><br></p>', 0, '2023-05-10 15:31:43.434978', '2023-05-10 16:47:34.085542');
INSERT INTO "public"."item_note" VALUES (21, 32, '<p><br></p>', 0, '2023-05-10 15:23:52.121944', '2023-05-10 16:48:51.981077');
INSERT INTO "public"."item_note" VALUES (30, 41, '这是群组笔记', 0, '2023-05-10 17:03:36.363932', '2023-05-14 23:57:13.590109');
INSERT INTO "public"."item_note" VALUES (35, 46, '<p>blog测试sdsssdas</p>', 0, '2023-05-12 21:44:23.483307', '2023-05-12 21:49:50.807722');
INSERT INTO "public"."item_note" VALUES (34, 45, '<p>试图完成blog页面测试一下hhhh</p>', 0, '2023-05-12 21:42:59.619819', '2023-05-12 22:19:07.62143');

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS "public"."message";
CREATE TABLE "public"."message" (
  "id" int4 NOT NULL DEFAULT nextval('message_id_seq'::regclass),
  "from_user" int4,
  "to_user" int4,
  "content" varchar(300) COLLATE "pg_catalog"."default",
  "type" int4 NOT NULL,
  "is_deleted" bool DEFAULT false,
  "create_time" timestamp(6),
  "update_time" timestamp(6)
)
;

-- ----------------------------
-- Records of message
-- ----------------------------

-- ----------------------------
-- Table structure for personal_item
-- ----------------------------
DROP TABLE IF EXISTS "public"."personal_item";
CREATE TABLE "public"."personal_item" (
  "id" int4 NOT NULL DEFAULT nextval('personal_item_id_seq'::regclass),
  "item_id" int4,
  "blog_id" int4,
  "visibility" int4 DEFAULT 1,
  "create_time" timestamp(6),
  "update_time" timestamp(6),
  "user_id" int4
)
;

-- ----------------------------
-- Records of personal_item
-- ----------------------------
INSERT INTO "public"."personal_item" VALUES (44, 49, 18, 1, '2023-05-13 16:14:38.614893', '2023-05-13 18:30:09.956276', 1);
INSERT INTO "public"."personal_item" VALUES (24, 29, NULL, 1, '2023-05-06 19:27:19.071459', '2023-05-06 19:27:19.071468', 3);
INSERT INTO "public"."personal_item" VALUES (25, 30, NULL, 1, '2023-05-06 19:27:25.804614', '2023-05-06 19:27:25.804621', 3);
INSERT INTO "public"."personal_item" VALUES (26, 31, NULL, 1, '2023-05-06 19:27:34.849327', '2023-05-06 19:27:34.849338', 3);
INSERT INTO "public"."personal_item" VALUES (23, 28, NULL, 0, '2023-05-06 17:08:32.345834', '2023-05-07 23:08:05.604095', 2);
INSERT INTO "public"."personal_item" VALUES (14, 19, NULL, 0, '2023-05-02 22:03:31.238153', '2023-05-10 16:47:32.591588', 1);
INSERT INTO "public"."personal_item" VALUES (30, 35, NULL, 0, '2023-05-10 15:31:43.404969', '2023-05-10 16:47:34.069265', 1);
INSERT INTO "public"."personal_item" VALUES (27, 32, NULL, 1, '2023-05-10 15:23:52.092069', '2023-05-10 16:48:51.954347', 1);
INSERT INTO "public"."personal_item" VALUES (37, 42, 16, 1, '2023-05-10 17:03:45.411491', '2023-05-11 16:02:37.07225', 1);
INSERT INTO "public"."personal_item" VALUES (12, 17, NULL, 1, '2023-05-02 21:42:19.868745', '2023-05-02 21:42:19.869169', 1);
INSERT INTO "public"."personal_item" VALUES (13, 18, NULL, 1, '2023-05-02 21:59:29.844432', '2023-05-02 21:59:29.844439', 1);
INSERT INTO "public"."personal_item" VALUES (41, 46, 17, 1, '2023-05-12 21:44:23.456508', '2023-05-12 21:49:50.777454', 1);
INSERT INTO "public"."personal_item" VALUES (36, 41, NULL, 1, '2023-05-10 17:03:36.332948', '2023-05-11 14:29:01.891928', 1);
INSERT INTO "public"."personal_item" VALUES (2, 9, 8, 0, NULL, '2023-05-03 14:46:41.769706', 1);
INSERT INTO "public"."personal_item" VALUES (21, 26, 14, 1, '2023-05-06 15:44:16.453708', '2023-05-06 15:46:33.201861', 2);
INSERT INTO "public"."personal_item" VALUES (38, 43, NULL, 1, '2023-05-10 17:03:56.505551', '2023-05-10 17:04:40.839674', 1);
INSERT INTO "public"."personal_item" VALUES (16, 21, NULL, 1, '2023-05-02 22:22:38.322075', '2023-05-11 14:52:55.327787', 1);
INSERT INTO "public"."personal_item" VALUES (40, 45, NULL, 1, '2023-05-12 21:42:59.584614', '2023-05-12 22:19:07.591053', 1);
INSERT INTO "public"."personal_item" VALUES (32, 37, NULL, 1, '2023-05-10 16:08:45.800781', '2023-05-11 14:53:11.156037', 1);
INSERT INTO "public"."personal_item" VALUES (22, 27, 14, 1, '2023-05-06 16:34:30.887828', '2023-05-06 16:59:31.196771', 2);
INSERT INTO "public"."personal_item" VALUES (35, 40, NULL, 1, '2023-05-10 16:51:15.18606', '2023-05-11 15:46:44.444971', 1);
INSERT INTO "public"."personal_item" VALUES (43, 48, 18, 1, '2023-05-13 16:06:23.153287', '2023-05-13 16:11:09.937509', 1);
INSERT INTO "public"."personal_item" VALUES (42, 47, 18, 1, '2023-05-13 15:21:15.447272', '2023-05-13 16:11:34.80144', 1);
INSERT INTO "public"."personal_item" VALUES (47, 52, 18, 1, '2023-05-13 16:15:01.222487', '2023-05-13 16:15:33.630821', 1);
INSERT INTO "public"."personal_item" VALUES (46, 51, 18, 1, '2023-05-13 16:14:51.715176', '2023-05-13 16:15:40.433959', 1);
INSERT INTO "public"."personal_item" VALUES (45, 50, 18, 1, '2023-05-13 16:14:44.719005', '2023-05-13 16:15:46.049981', 1);

-- ----------------------------
-- Table structure for relation_user_group
-- ----------------------------
DROP TABLE IF EXISTS "public"."relation_user_group";
CREATE TABLE "public"."relation_user_group" (
  "id" int4 NOT NULL DEFAULT nextval('relation_user_group_id_seq'::regclass),
  "user_id" int4 NOT NULL,
  "group_id" int4 NOT NULL,
  "authority" int4 DEFAULT 0,
  "status" int4 DEFAULT 0,
  "create_time" timestamp(6),
  "update_time" timestamp(6)
)
;

-- ----------------------------
-- Records of relation_user_group
-- ----------------------------
INSERT INTO "public"."relation_user_group" VALUES (2, 1, 2, 0, 0, '2023-05-04 10:55:39.854816', '2023-05-04 10:55:39.854816');
INSERT INTO "public"."relation_user_group" VALUES (3, 2, 2, 0, 0, '2023-05-07 23:15:59.191023', '2023-05-07 23:15:59.191023');
INSERT INTO "public"."relation_user_group" VALUES (4, 1, 4, 0, 0, '2023-05-13 19:33:45.226968', '2023-05-13 19:33:45.226976');
INSERT INTO "public"."relation_user_group" VALUES (5, 1, 7, 0, 0, '2023-05-13 20:00:11.976766', '2023-05-13 20:00:11.976772');
INSERT INTO "public"."relation_user_group" VALUES (6, 1, 8, 0, 0, '2023-05-14 17:11:53.689577', '2023-05-14 17:11:53.68959');
INSERT INTO "public"."relation_user_group" VALUES (7, 1, 9, 0, 0, '2023-05-14 19:03:34.964805', '2023-05-14 19:03:34.964814');
INSERT INTO "public"."relation_user_group" VALUES (8, 1, 10, 0, 0, '2023-05-14 19:03:50.573202', '2023-05-14 19:03:50.573212');
INSERT INTO "public"."relation_user_group" VALUES (9, 1, 11, 0, 0, '2023-05-14 19:05:03.212179', '2023-05-14 19:05:03.212187');
INSERT INTO "public"."relation_user_group" VALUES (10, 1, 12, 0, 0, '2023-05-14 19:05:10.590468', '2023-05-14 19:05:10.590477');
INSERT INTO "public"."relation_user_group" VALUES (11, 1, 13, 0, 0, '2023-05-14 19:05:19.521607', '2023-05-14 19:05:19.521619');
INSERT INTO "public"."relation_user_group" VALUES (12, 1, 14, 0, 0, '2023-05-14 19:05:33.695812', '2023-05-14 19:05:33.695833');

-- ----------------------------
-- Table structure for t_group
-- ----------------------------
DROP TABLE IF EXISTS "public"."t_group";
CREATE TABLE "public"."t_group" (
  "id" int4 NOT NULL DEFAULT nextval('t_group_id_seq'::regclass),
  "group_name" varchar(80) COLLATE "pg_catalog"."default" NOT NULL,
  "password" varchar(120) COLLATE "pg_catalog"."default",
  "description" varchar(160) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "update_time" timestamp(6),
  "member_num" int4 DEFAULT 0
)
;

-- ----------------------------
-- Records of t_group
-- ----------------------------
INSERT INTO "public"."t_group" VALUES (7, 'test1', '123qwe', NULL, '2023-05-13 20:00:11.948987', '2023-05-13 20:00:11.948993', 1);
INSERT INTO "public"."t_group" VALUES (4, 'sbcmj', 'sbcmj', '2', '2023-05-13 19:33:45.198345', '2023-05-13 19:33:45.19835', 1);
INSERT INTO "public"."t_group" VALUES (8, '数据库', '123', NULL, '2023-05-14 17:11:53.657201', '2023-05-14 17:11:53.657224', 1);
INSERT INTO "public"."t_group" VALUES (2, '幸福一家人', '123456', '123好@', '2023-05-04 10:55:39.714306', '2023-05-14 18:49:07.309017', 2);
INSERT INTO "public"."t_group" VALUES (9, 'test2', '123', NULL, '2023-05-14 19:03:34.932731', '2023-05-14 19:03:34.932739', 1);
INSERT INTO "public"."t_group" VALUES (10, 'test2', '123', NULL, '2023-05-14 19:03:50.544881', '2023-05-14 19:03:50.544889', 1);
INSERT INTO "public"."t_group" VALUES (11, 'test5', NULL, NULL, '2023-05-14 19:05:03.183581', '2023-05-14 19:05:03.183589', 1);
INSERT INTO "public"."t_group" VALUES (12, 'test6', NULL, NULL, '2023-05-14 19:05:10.562414', '2023-05-14 19:05:10.562436', 1);
INSERT INTO "public"."t_group" VALUES (13, 'test', NULL, NULL, '2023-05-14 19:05:19.4949', '2023-05-14 19:05:19.494907', 1);
INSERT INTO "public"."t_group" VALUES (14, 'test7', NULL, NULL, '2023-05-14 19:05:33.667807', '2023-05-14 19:05:33.667816', 1);

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS "public"."t_user";
CREATE TABLE "public"."t_user" (
  "id" int4 NOT NULL DEFAULT nextval('t_user_id_seq'::regclass),
  "wx_id" varchar(80) COLLATE "pg_catalog"."default",
  "wx_name" varchar(80) COLLATE "pg_catalog"."default",
  "authority" int4 DEFAULT 0,
  "is_banned" bool DEFAULT false,
  "description" varchar(160) COLLATE "pg_catalog"."default",
  "email" varchar(80) COLLATE "pg_catalog"."default",
  "gender" varchar(10) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "update_time" timestamp(6),
  "avatar" varchar(150) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO "public"."t_user" VALUES (2, 'oWRce5W9RRUoBpBJ0IOz6A2Hbeug', NULL, 0, 'f', NULL, NULL, NULL, '2023-05-06 14:56:43.352806', '2023-05-06 14:56:43.35282', NULL);
INSERT INTO "public"."t_user" VALUES (3, 'oWRce5YP0pzC6FUt3F2QYyGRBiKA', NULL, 0, 'f', NULL, NULL, NULL, '2023-05-06 19:22:15.336064', '2023-05-06 19:22:15.336073', NULL);
INSERT INTO "public"."t_user" VALUES (1, 'oWRce5YQvIHjd23jng4yuftVNKTo', NULL, 0, 'f', NULL, NULL, NULL, '2023-04-26 15:06:12.544589', '2023-05-15 00:54:25.610873', '/avatar/oWRce5YQvIHjd23jng4yuftVNKTo.jpg');

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "public"."blog_id_seq"
OWNED BY "public"."blog"."id";
SELECT setval('"public"."blog_id_seq"', 19, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "public"."blog_record_id_seq"
OWNED BY "public"."blog_record"."id";
SELECT setval('"public"."blog_record_id_seq"', 2, false);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "public"."group_item_id_seq"
OWNED BY "public"."group_item"."id";
SELECT setval('"public"."group_item_id_seq"', 4, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "public"."image_id_seq"
OWNED BY "public"."image"."id";
SELECT setval('"public"."image_id_seq"', 2, false);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "public"."item_id_seq"
OWNED BY "public"."item"."id";
SELECT setval('"public"."item_id_seq"', 55, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "public"."item_note_id_seq"
OWNED BY "public"."item_note"."id";
SELECT setval('"public"."item_note_id_seq"', 44, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "public"."message_id_seq"
OWNED BY "public"."message"."id";
SELECT setval('"public"."message_id_seq"', 2, false);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "public"."personal_item_id_seq"
OWNED BY "public"."personal_item"."id";
SELECT setval('"public"."personal_item_id_seq"', 48, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "public"."relation_user_group_id_seq"
OWNED BY "public"."relation_user_group"."id";
SELECT setval('"public"."relation_user_group_id_seq"', 13, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "public"."t_group_id_seq"
OWNED BY "public"."t_group"."id";
SELECT setval('"public"."t_group_id_seq"', 15, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "public"."t_user_id_seq"
OWNED BY "public"."t_user"."id";
SELECT setval('"public"."t_user_id_seq"', 4, true);

-- ----------------------------
-- Primary Key structure for table blog
-- ----------------------------
ALTER TABLE "public"."blog" ADD CONSTRAINT "blog_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table blog_record
-- ----------------------------
ALTER TABLE "public"."blog_record" ADD CONSTRAINT "blog_record_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table group_item
-- ----------------------------
ALTER TABLE "public"."group_item" ADD CONSTRAINT "group_item_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table image
-- ----------------------------
ALTER TABLE "public"."image" ADD CONSTRAINT "image_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table item
-- ----------------------------
ALTER TABLE "public"."item" ADD CONSTRAINT "item_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table item_note
-- ----------------------------
ALTER TABLE "public"."item_note" ADD CONSTRAINT "item_note_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table message
-- ----------------------------
ALTER TABLE "public"."message" ADD CONSTRAINT "message_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table personal_item
-- ----------------------------
ALTER TABLE "public"."personal_item" ADD CONSTRAINT "personal_item_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table relation_user_group
-- ----------------------------
ALTER TABLE "public"."relation_user_group" ADD CONSTRAINT "relation_user_group_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table t_group
-- ----------------------------
ALTER TABLE "public"."t_group" ADD CONSTRAINT "group_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Uniques structure for table t_user
-- ----------------------------
ALTER TABLE "public"."t_user" ADD CONSTRAINT "user_wx_id_key" UNIQUE ("wx_id");

-- ----------------------------
-- Primary Key structure for table t_user
-- ----------------------------
ALTER TABLE "public"."t_user" ADD CONSTRAINT "user_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Foreign Keys structure for table blog
-- ----------------------------
ALTER TABLE "public"."blog" ADD CONSTRAINT "blog_user_id_fkey" FOREIGN KEY ("user_id") REFERENCES "public"."t_user" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table blog_record
-- ----------------------------
ALTER TABLE "public"."blog_record" ADD CONSTRAINT "blog_record_blog_id_fkey" FOREIGN KEY ("blog_id") REFERENCES "public"."blog" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."blog_record" ADD CONSTRAINT "blog_record_user_id_fkey" FOREIGN KEY ("user_id") REFERENCES "public"."t_user" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table group_item
-- ----------------------------
ALTER TABLE "public"."group_item" ADD CONSTRAINT "group_item_group_id_fkey" FOREIGN KEY ("group_id") REFERENCES "public"."t_group" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."group_item" ADD CONSTRAINT "group_item_item_id_fkey" FOREIGN KEY ("item_id") REFERENCES "public"."item" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table image
-- ----------------------------
ALTER TABLE "public"."image" ADD CONSTRAINT "image_item_note_id_fkey" FOREIGN KEY ("item_note_id") REFERENCES "public"."item_note" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table item_note
-- ----------------------------
ALTER TABLE "public"."item_note" ADD CONSTRAINT "item_note_item_id_fkey" FOREIGN KEY ("item_id") REFERENCES "public"."item" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table message
-- ----------------------------
ALTER TABLE "public"."message" ADD CONSTRAINT "message_from_user_fkey" FOREIGN KEY ("from_user") REFERENCES "public"."t_user" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."message" ADD CONSTRAINT "message_to_user_fkey" FOREIGN KEY ("to_user") REFERENCES "public"."t_user" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table personal_item
-- ----------------------------
ALTER TABLE "public"."personal_item" ADD CONSTRAINT "personal_item_blog_id_fkey" FOREIGN KEY ("blog_id") REFERENCES "public"."blog" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."personal_item" ADD CONSTRAINT "personal_item_item_id_fkey" FOREIGN KEY ("item_id") REFERENCES "public"."item" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."personal_item" ADD CONSTRAINT "personal_item_t_user_id_fk" FOREIGN KEY ("user_id") REFERENCES "public"."t_user" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table relation_user_group
-- ----------------------------
ALTER TABLE "public"."relation_user_group" ADD CONSTRAINT "relation_user_group_group_id_fkey" FOREIGN KEY ("group_id") REFERENCES "public"."t_group" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."relation_user_group" ADD CONSTRAINT "relation_user_group_user_id_fkey" FOREIGN KEY ("user_id") REFERENCES "public"."t_user" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
