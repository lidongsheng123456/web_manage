/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80036 (8.0.36)
 Source Host           : localhost:3306
 Source Schema         : web_manage

 Target Server Type    : MySQL
 Target Server Version : 80036 (8.0.36)
 File Encoding         : 65001

 Date: 28/05/2025 14:18:29
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for front_user
-- ----------------------------
DROP TABLE IF EXISTS `front_user`;
CREATE TABLE `front_user`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '姓名',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '手机号',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱',
  `img_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '图片路径',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of front_user
-- ----------------------------
INSERT INTO `front_user` VALUES (1, 'admin', 'e10adc3949ba59abbe56e057f20f883e', 'admin', '19976898057', '208550738@qq.com', 'http://localhost:8088/common/files/1748412551117-logo.jpg', '2025-04-24 10:33:25', '2025-05-28 14:09:37');

-- ----------------------------
-- Table structure for sys_com_query
-- ----------------------------
DROP TABLE IF EXISTS `sys_com_query`;
CREATE TABLE `sys_com_query`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '名称',
  `code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '代码',
  `custom_sql` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'sql语句',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '描述',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_com_query
-- ----------------------------
INSERT INTO `sys_com_query` VALUES (1, '后台用户查询', 'user_query', 'SELECT username as dictLabel,id as dictValue FROM sys_user', NULL, '2025-05-01 13:16:39', '2025-05-01 15:48:26');
INSERT INTO `sys_com_query` VALUES (4, '前台用户查询', 'front_user_query', 'SELECT username as dictLabel,id as dictValue FROM front_user', NULL, '2025-05-13 21:50:29', '2025-05-20 22:01:20');

-- ----------------------------
-- Table structure for sys_dict_data
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_data`;
CREATE TABLE `sys_dict_data`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '编号',
  `dict_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '字典类型',
  `dict_label` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '字典标签',
  `dict_value` int NOT NULL COMMENT '字典键值',
  `tag_type` enum('primary','success','info','warning','danger') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '标签类型',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '描述',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dict_data
-- ----------------------------
INSERT INTO `sys_dict_data` VALUES (1, 'shop_status', '打烊', 0, 'warning', '打烊', '2025-05-01 13:14:40', '2025-05-01 22:50:24');
INSERT INTO `sys_dict_data` VALUES (2, 'shop_status', '营业', 1, 'success', '营业', '2025-05-01 13:14:56', '2025-05-04 13:29:30');

-- ----------------------------
-- Table structure for sys_notice
-- ----------------------------
DROP TABLE IF EXISTS `sys_notice`;
CREATE TABLE `sys_notice`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `notice_title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '通知标题',
  `notice_content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '通知内容',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `create_user_id` bigint NOT NULL COMMENT '创建用户id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `notice_ibfk_1`(`create_user_id` ASC) USING BTREE,
  CONSTRAINT `sys_notice_ibfk_1` FOREIGN KEY (`create_user_id`) REFERENCES `sys_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 46 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_notice
-- ----------------------------
INSERT INTO `sys_notice` VALUES (1, '使用说明', '一个为毕业设计快速开发打造的脚手架系统，支持前后端二次开发。', '2025-01-19 13:59:59', '2025-05-21 20:48:57', 1);
INSERT INTO `sys_notice` VALUES (44, '提示', '当前页面为前台页面需要自定义，但后台模板是完整的。', '2025-05-27 13:29:10', '2025-05-27 13:30:10', 1);

-- ----------------------------
-- Table structure for sys_oper_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_oper_log`;
CREATE TABLE `sys_oper_log`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '日志主键',
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '模块标题',
  `business_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '业务类型（0其它 1新增 2修改 3删除）',
  `method` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '方法名称',
  `request_method` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '请求方式',
  `oper_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '操作人员',
  `oper_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '请求URL',
  `oper_param` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '请求参数',
  `json_result` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '返回参数',
  `status` int NULL DEFAULT 0 COMMENT '操作状态（0正常 1异常）',
  `error_msg` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '错误消息',
  `oper_time` datetime NULL DEFAULT NULL COMMENT '操作时间',
  `cost_time` bigint NULL DEFAULT 0 COMMENT '消耗时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_sys_oper_log_bt`(`business_type` ASC) USING BTREE,
  INDEX `idx_sys_oper_log_s`(`status` ASC) USING BTREE,
  INDEX `idx_sys_oper_log_ot`(`oper_time` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1315 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '操作日志记录' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_oper_log
-- ----------------------------
INSERT INTO `sys_oper_log` VALUES (956, '分配角色', 'insert', 'com.example.controller.admin.AdminRoleController.assignRole()', 'POST', 'lidongsheng', 'http://localhost:8088/admin/role/assign', '[AssignRoleDTO(roleId=3, userId=[1])]', '{\"code\":200,\"msg\":\"成功\"}', 0, '', '2025-02-01 21:22:57', 9);
INSERT INTO `sys_oper_log` VALUES (957, '退出后台', 'force', 'com.example.controller.admin.AdminWebController.logout()', 'GET', 'scx', 'http://localhost:8088/admin/logout/18', '[18]', '{\"code\":200,\"msg\":\"成功\"}', 0, '', '2025-02-01 21:26:34', 3);
INSERT INTO `sys_oper_log` VALUES (958, '修改用户', 'update', 'com.example.controller.admin.AdminUserController.updateUser()', 'PUT', 'lidongsheng', 'http://localhost:8088/admin/user', '[User(id=null, username=null, password=null, name=null, phone=null, email=null, imgUrl=null, createTime=null, updateTime=null)]', '', 1, 'Cannot invoke \"java.lang.Long.longValue()\" because the return value of \"com.example.system.domain.User.getId()\" is null', '2025-02-02 15:17:02', 4);
INSERT INTO `sys_oper_log` VALUES (959, '修改用户', 'update', 'com.example.controller.admin.AdminUserController.updateUser()', 'PUT', 'lidongsheng', 'http://localhost:8088/admin/user', '[User(id=null, username=null, password=null, name=null, phone=null, email=null, imgUrl=null, createTime=null, updateTime=null)]', '', 1, 'Cannot invoke \"java.lang.Long.longValue()\" because the return value of \"com.example.system.domain.User.getId()\" is null', '2025-02-02 15:17:09', 1);
INSERT INTO `sys_oper_log` VALUES (960, '修改用户', 'update', 'com.example.controller.admin.AdminUserController.updateUser()', 'PUT', 'lidongsheng', 'http://localhost:8088/admin/user', '[User(id=1, username=null, password=null, name=null, phone=null, email=null, imgUrl=http://139.196.196.178:8088/common/files/1737722850101-cat.jpg, createTime=null, updateTime=null)]', '', 1, '禁止操作超级管理员', '2025-02-02 15:18:45', 1);
INSERT INTO `sys_oper_log` VALUES (961, '退出后台', 'force', 'com.example.controller.admin.AdminWebController.logout()', 'GET', 'lidongsheng', 'http://localhost:8088/admin/logout/1', '[1]', '{\"code\":200,\"msg\":\"成功\"}', 0, '', '2025-02-02 15:19:01', 52);
INSERT INTO `sys_oper_log` VALUES (962, '登录后台', 'other', 'com.example.controller.admin.AdminWebController.login()', 'POST', '', 'http://localhost:8088/admin/login', '[UserDto(id=null, username=scx, password=20040905, name=null, phone=null, email=null, imgUrl=null, createTime=null, updateTime=null, code=podzi), org.apache.catalina.session.StandardSessionFacade@32eec554]', '', 1, '验证码错误', '2025-02-02 15:19:14', 0);
INSERT INTO `sys_oper_log` VALUES (963, '登录后台', 'other', 'com.example.controller.admin.AdminWebController.login()', 'POST', '', 'http://localhost:8088/admin/login', '[UserDto(id=null, username=scx, password=20040905, name=null, phone=null, email=null, imgUrl=null, createTime=null, updateTime=null, code=x8mll), org.apache.catalina.session.StandardSessionFacade@32eec554]', '{\"code\":200,\"msg\":\"成功\",\"data\":{\"id\":18,\"username\":\"scx\",\"name\":\"史晨翔\",\"phone\":\"19976898057\",\"email\":\"208550738@qq.com\",\"imgUrl\":\"http://localhost:8088/common/files/1737964108818-微信图片_20250105205358.jpg\",\"createTime\":1737964050000,\"updateTime\":1737964127000,\"permissions\":[{\"permission_code\":\"admin:notice:query\"},{\"permission_code\":\"admin:person:query\"}],\"roles\":[{\"role_code\":\"user\"}]}}', 0, '', '2025-02-02 15:19:20', 21);
INSERT INTO `sys_oper_log` VALUES (964, '退出后台', 'force', 'com.example.controller.admin.AdminWebController.logout()', 'GET', 'scx', 'http://localhost:8088/admin/logout/18', '[18]', '{\"code\":200,\"msg\":\"成功\"}', 0, '', '2025-02-02 15:20:21', 4);
INSERT INTO `sys_oper_log` VALUES (965, '登录后台', 'other', 'com.example.controller.admin.AdminWebController.login()', 'POST', '', 'http://localhost:8088/admin/login', '[UserDto(id=null, username=lidongsheng, password=123456, name=null, phone=null, email=null, imgUrl=null, createTime=null, updateTime=null, code=xzqrr), org.apache.catalina.session.StandardSessionFacade@32eec554]', '', 1, '验证码错误', '2025-02-02 15:20:26', 0);
INSERT INTO `sys_oper_log` VALUES (1286, '退出前台', 'force', 'com.example.controller.user.UserWebController.logout()', 'GET', 'admin', 'http://localhost:8088/user/logout', '[]', '{\"code\":200,\"msg\":\"成功\"}', 0, '', '2025-05-27 13:45:58', 7);
INSERT INTO `sys_oper_log` VALUES (1287, '登录前台', 'other', 'com.example.controller.user.UserWebController.login()', 'POST', 'admin', 'http://localhost:8088/user/login', '[UserDto(id=null, username=admin, password=123456, name=null, phone=null, email=null, imgUrl=null, createTime=null, updateTime=null, code=e60uj), org.apache.catalina.session.StandardSessionFacade@10691072]', '{\"code\":200,\"msg\":\"成功\",\"data\":{\"id\":1,\"username\":\"admin\",\"name\":\"admin\",\"phone\":\"19976898057\",\"email\":\"208550738@qq.com\",\"imgUrl\":\"http://localhost:8088/common/files/1748322742795-cat.png\",\"createTime\":1745462005000,\"updateTime\":1748322744000,\"permissions\":[],\"roles\":[]}}', 0, '', '2025-05-27 13:50:46', 8);
INSERT INTO `sys_oper_log` VALUES (1288, '修改个人信息', 'update', 'com.example.controller.user.UserWebController.updatePerson()', 'POST', 'admin', 'http://localhost:8088/user/person', '[User(id=1, username=admin, password=null, name=admin, phone=19976898057, email=208550738@qq.com, imgUrl=http://localhost:8088/common/files/1748322742795-cat.png, createTime=2025-04-24T10:33:25, updateTime=2025-05-27T13:12:24)]', '{\"code\":200,\"msg\":\"成功\"}', 0, '', '2025-05-27 13:50:54', 10);
INSERT INTO `sys_oper_log` VALUES (1289, '退出前台', 'force', 'com.example.controller.user.UserWebController.logout()', 'GET', 'admin', 'http://localhost:8088/user/logout', '[]', '{\"code\":200,\"msg\":\"成功\"}', 0, '', '2025-05-27 13:51:03', 3);
INSERT INTO `sys_oper_log` VALUES (1290, '登录前台', 'other', 'com.example.controller.user.UserWebController.login()', 'POST', 'admin', 'http://localhost:8088/user/login', '[UserDto(id=null, username=admin, password=123456, name=null, phone=null, email=null, imgUrl=null, createTime=null, updateTime=null, code=dpd9h), org.apache.catalina.session.StandardSessionFacade@10691072]', '{\"code\":200,\"msg\":\"成功\",\"data\":{\"id\":1,\"username\":\"admin\",\"name\":\"admin\",\"phone\":\"19976898057\",\"email\":\"208550738@qq.com\",\"imgUrl\":\"http://localhost:8088/common/files/1748322742795-cat.png\",\"createTime\":1745462005000,\"updateTime\":1748325054000,\"permissions\":[],\"roles\":[]}}', 0, '', '2025-05-27 13:52:18', 3);
INSERT INTO `sys_oper_log` VALUES (1291, '登录后台', 'other', 'com.example.controller.admin.AdminWebController.login()', 'POST', '', 'http://localhost:8088/admin/login', '[UserDto(id=null, username=admin, password=123456, name=null, phone=null, email=null, imgUrl=null, createTime=null, updateTime=null, code=bmlns), org.apache.catalina.session.StandardSessionFacade@6ec460e1]', '', 1, '验证码错误', '2025-05-27 22:25:44', 2);
INSERT INTO `sys_oper_log` VALUES (1292, '登录后台', 'other', 'com.example.controller.admin.AdminWebController.login()', 'POST', '', 'http://localhost:8088/admin/login', '[UserDto(id=null, username=admin, password=123456, name=null, phone=null, email=null, imgUrl=null, createTime=null, updateTime=null, code=dgyrm), org.apache.catalina.session.StandardSessionFacade@6ec460e1]', '{\"code\":200,\"msg\":\"成功\",\"data\":{\"id\":1,\"username\":\"admin\",\"name\":\"李东升\",\"phone\":\"19976898057\",\"email\":\"208550738@qq.com\",\"imgUrl\":\"http://localhost:8088/common/files/1746076011429-cat.png\",\"createTime\":1737266057000,\"updateTime\":1748265179000,\"permissions\":[{\"permission_code\":\"admin:notice:add\"},{\"permission_code\":\"admin:notice:delete\"},{\"permission_code\":\"admin:notice:update\"},{\"permission_code\":\"admin:notice:query\"},{\"permission_code\":\"admin:user:add\"},{\"permission_code\":\"admin:user:delete\"},{\"permission_code\":\"admin:user:update\"},{\"permission_code\":\"admin:operLog:delete\"},{\"permission_code\":\"admin:operLog:update\"},{\"permission_code\":\"admin:operLog:query\"},{\"permission_code\":\"admin:notice:export\"},{\"permission_code\":\"admin:user:export\"},{\"permission_code\":\"admin:operLog:export\"},{\"permission_code\":\"admin:person:query\"},{\"permission_code\":\"admin:permission:query\"},{\"permission_code\":\"admin:permission:remove\"},{\"permission_code\":\"admin:permission:assign\"},{\"permission_code\":\"admin:permission:export\"},{\"permission_code\":\"admin:permission:update\"},{\"permission_code\":\"admin:permission:delete\"},{\"permission_code\":\"admin:permission:add\"},{\"permission_code\":\"admin:role:remove\"},{\"permission_code\":\"admin:role:assign\"},{\"permission_code\":\"admin:role:export\"},{\"permission_code\":\"admin:role:query\"},{\"permission_code\":\"admin:role:update\"},{\"permission_code\":\"admin:role:delete\"},{\"permission_code\":\"admin:role:add\"},{\"permission_code\":\"admin:docs:query\"},{\"permission_code\":\"admin:dict:query\"},{\"permission_code\":\"admin:com-query:query\"},{\"permission_code\":\"admin:com-query:export\"},{\"permission_code\":\"admin:com-query:update\"},{\"permission_code\":\"admin:com-query:delete\"},{\"permission_code\":\"admin:com-query:add\"},{\"permission_code\":\"admin:dict:export\"},{\"permission_code\":\"admin:dict:update\"},{\"permission_code\":\"admin:dict:delete\"},{\"permission_code\":\"admin:dict:add\"},{\"permission_code\":\"admin:user:query\"}],\"roles\":[{\"role_code\":\"super_admin\"},{\"role_code\":\"admin\"},{\"role_code\":\"user\"}]}}', 0, '', '2025-05-27 22:25:49', 34);
INSERT INTO `sys_oper_log` VALUES (1293, '新增通知', 'insert', 'com.example.controller.admin.AdminNoticeController.addNotice()', 'POST', 'admin', 'http://localhost:8088/admin/notice', '[Notice(id=null, noticeTitle=测试, noticeContent=sb, createTime=2025-05-27T22:25:56.326865600, updateTime=2025-05-27T22:25:56.326865600, createUserId=null)]', '{\"code\":200,\"msg\":\"成功\"}', 0, '', '2025-05-27 22:25:56', 89);
INSERT INTO `sys_oper_log` VALUES (1294, '修改通知', 'update', 'com.example.controller.admin.AdminNoticeController.updateNotice()', 'PUT', 'admin', 'http://localhost:8088/admin/notice', '[Notice(id=45, noticeTitle=测试, noticeContent=sb, createTime=2025-05-27T22:25:56, updateTime=2025-05-27T22:27:07.767529700, createUserId=1)]', '{\"code\":200,\"msg\":\"成功\"}', 0, '', '2025-05-27 22:27:08', 82);
INSERT INTO `sys_oper_log` VALUES (1295, '修改通知', 'update', 'com.example.controller.admin.AdminNoticeController.updateNotice()', 'PUT', 'admin', 'http://localhost:8088/admin/notice', '[Notice(id=45, noticeTitle=测试, noticeContent=傻逼, createTime=2025-05-27T22:25:56, updateTime=2025-05-27T22:27:14.064472400, createUserId=1)]', '{\"code\":200,\"msg\":\"成功\"}', 0, '', '2025-05-27 22:27:14', 40);
INSERT INTO `sys_oper_log` VALUES (1296, '修改通知', 'update', 'com.example.controller.admin.AdminNoticeController.updateNotice()', 'PUT', 'admin', 'http://localhost:8088/admin/notice', '[Notice(id=45, noticeTitle=测试, noticeContent=艹, createTime=2025-05-27T22:25:56, updateTime=2025-05-27T22:27:28.829347400, createUserId=1)]', '{\"code\":200,\"msg\":\"成功\"}', 0, '', '2025-05-27 22:27:29', 47);
INSERT INTO `sys_oper_log` VALUES (1297, '修改通知', 'update', 'com.example.controller.admin.AdminNoticeController.updateNotice()', 'PUT', 'admin', 'http://localhost:8088/admin/notice', '[Notice(id=45, noticeTitle=测试, noticeContent=曹尼玛, createTime=2025-05-27T22:25:56, updateTime=2025-05-27T22:27:34.069422100, createUserId=1)]', '{\"code\":200,\"msg\":\"成功\"}', 0, '', '2025-05-27 22:27:34', 43);
INSERT INTO `sys_oper_log` VALUES (1298, '修改通知', 'update', 'com.example.controller.admin.AdminNoticeController.updateNotice()', 'PUT', 'admin', 'http://localhost:8088/admin/notice', '[Notice(id=45, noticeTitle=测试, noticeContent=cnm, createTime=2025-05-27T22:25:56, updateTime=2025-05-27T22:27:38.953362900, createUserId=1)]', '{\"code\":200,\"msg\":\"成功\"}', 0, '', '2025-05-27 22:27:39', 47);
INSERT INTO `sys_oper_log` VALUES (1299, '修改通知', 'update', 'com.example.controller.admin.AdminNoticeController.updateNotice()', 'PUT', 'admin', 'http://localhost:8088/admin/notice', '[Notice(id=45, noticeTitle=测试, noticeContent=智障, createTime=2025-05-27T22:25:56, updateTime=2025-05-27T22:27:43.919463600, createUserId=1)]', '{\"code\":200,\"msg\":\"成功\"}', 0, '', '2025-05-27 22:27:44', 60);
INSERT INTO `sys_oper_log` VALUES (1300, '批量删除通知', 'delete', 'com.example.controller.admin.AdminNoticeController.batchDeleteNotice()', 'DELETE', 'admin', 'http://localhost:8088/admin/notice/batchDelete', '[[45]]', '{\"code\":200,\"msg\":\"成功\"}', 0, '', '2025-05-27 22:27:49', 25);
INSERT INTO `sys_oper_log` VALUES (1301, '登录前台', 'other', 'com.example.controller.user.UserWebController.login()', 'POST', '', 'http://localhost:8088/user/login', '[UserDto(id=null, username=admin, password=123456, name=null, phone=null, email=null, imgUrl=null, createTime=null, updateTime=null, code=rmgih), org.apache.catalina.session.StandardSessionFacade@304c1018]', '{\"code\":200,\"msg\":\"成功\",\"data\":{\"id\":1,\"username\":\"admin\",\"name\":\"admin\",\"phone\":\"19976898057\",\"email\":\"208550738@qq.com\",\"imgUrl\":\"http://localhost:8088/common/files/1748322742795-cat.png\",\"createTime\":1745462005000,\"updateTime\":1748325054000,\"permissions\":[],\"roles\":[]}}', 0, '', '2025-05-28 13:27:58', 54);
INSERT INTO `sys_oper_log` VALUES (1302, '登录后台', 'other', 'com.example.controller.admin.AdminWebController.login()', 'POST', '', 'http://localhost:8088/admin/login', '[UserDto(id=null, username=admin, password=123456, name=null, phone=null, email=null, imgUrl=null, createTime=null, updateTime=null, code=qwppR), org.apache.catalina.session.StandardSessionFacade@304c1018]', '{\"code\":200,\"msg\":\"成功\",\"data\":{\"id\":1,\"username\":\"admin\",\"name\":\"李东升\",\"phone\":\"19976898057\",\"email\":\"208550738@qq.com\",\"imgUrl\":\"http://localhost:8088/common/files/1746076011429-cat.png\",\"createTime\":1737266057000,\"updateTime\":1748265179000,\"permissions\":[{\"permission_code\":\"admin:notice:add\"},{\"permission_code\":\"admin:notice:delete\"},{\"permission_code\":\"admin:notice:update\"},{\"permission_code\":\"admin:notice:query\"},{\"permission_code\":\"admin:user:add\"},{\"permission_code\":\"admin:user:delete\"},{\"permission_code\":\"admin:user:update\"},{\"permission_code\":\"admin:operLog:delete\"},{\"permission_code\":\"admin:operLog:update\"},{\"permission_code\":\"admin:operLog:query\"},{\"permission_code\":\"admin:notice:export\"},{\"permission_code\":\"admin:user:export\"},{\"permission_code\":\"admin:operLog:export\"},{\"permission_code\":\"admin:person:query\"},{\"permission_code\":\"admin:permission:query\"},{\"permission_code\":\"admin:permission:remove\"},{\"permission_code\":\"admin:permission:assign\"},{\"permission_code\":\"admin:permission:export\"},{\"permission_code\":\"admin:permission:update\"},{\"permission_code\":\"admin:permission:delete\"},{\"permission_code\":\"admin:permission:add\"},{\"permission_code\":\"admin:role:remove\"},{\"permission_code\":\"admin:role:assign\"},{\"permission_code\":\"admin:role:export\"},{\"permission_code\":\"admin:role:query\"},{\"permission_code\":\"admin:role:update\"},{\"permission_code\":\"admin:role:delete\"},{\"permission_code\":\"admin:role:add\"},{\"permission_code\":\"admin:docs:query\"},{\"permission_code\":\"admin:dict:query\"},{\"permission_code\":\"admin:com-query:query\"},{\"permission_code\":\"admin:com-query:export\"},{\"permission_code\":\"admin:com-query:update\"},{\"permission_code\":\"admin:com-query:delete\"},{\"permission_code\":\"admin:com-query:add\"},{\"permission_code\":\"admin:dict:export\"},{\"permission_code\":\"admin:dict:update\"},{\"permission_code\":\"admin:dict:delete\"},{\"permission_code\":\"admin:dict:add\"},{\"permission_code\":\"admin:user:query\"}],\"roles\":[{\"role_code\":\"super_admin\"},{\"role_code\":\"admin\"},{\"role_code\":\"user\"}]}}', 0, '', '2025-05-28 13:28:11', 15);
INSERT INTO `sys_oper_log` VALUES (1303, '新增权限', 'insert', 'com.example.controller.admin.AdminPermissionController.addPermission()', 'POST', 'admin', 'http://localhost:8088/admin/permission', '[Permission(id=null, permissionCode=admin:front-user:add, permissionName=新增前台用户, description=add, createTime=null, updateTime=null)]', '{\"code\":200,\"msg\":\"成功\"}', 0, '', '2025-05-28 13:30:58', 17);
INSERT INTO `sys_oper_log` VALUES (1304, '新增权限', 'insert', 'com.example.controller.admin.AdminPermissionController.addPermission()', 'POST', 'admin', 'http://localhost:8088/admin/permission', '[Permission(id=null, permissionCode=admin:front-user:delete, permissionName=删除前台用户, description=delete, createTime=null, updateTime=null)]', '{\"code\":200,\"msg\":\"成功\"}', 0, '', '2025-05-28 13:31:13', 7);
INSERT INTO `sys_oper_log` VALUES (1305, '新增权限', 'insert', 'com.example.controller.admin.AdminPermissionController.addPermission()', 'POST', 'admin', 'http://localhost:8088/admin/permission', '[Permission(id=null, permissionCode=admin:front-user:update, permissionName=修改前台用户, description=update, createTime=null, updateTime=null)]', '{\"code\":200,\"msg\":\"成功\"}', 0, '', '2025-05-28 13:31:25', 7);
INSERT INTO `sys_oper_log` VALUES (1306, '新增权限', 'insert', 'com.example.controller.admin.AdminPermissionController.addPermission()', 'POST', 'admin', 'http://localhost:8088/admin/permission', '[Permission(id=null, permissionCode=admin:front-user:query, permissionName=查询前台用户, description=query, createTime=null, updateTime=null)]', '{\"code\":200,\"msg\":\"成功\"}', 0, '', '2025-05-28 13:31:36', 8);
INSERT INTO `sys_oper_log` VALUES (1307, '新增权限', 'insert', 'com.example.controller.admin.AdminPermissionController.addPermission()', 'POST', 'admin', 'http://localhost:8088/admin/permission', '[Permission(id=null, permissionCode=admin:front-user:export, permissionName=导出前台用户, description=export, createTime=null, updateTime=null)]', '{\"code\":200,\"msg\":\"成功\"}', 0, '', '2025-05-28 13:31:49', 7);
INSERT INTO `sys_oper_log` VALUES (1308, '分配权限', 'insert', 'com.example.controller.admin.AdminPermissionController.assignPermission()', 'POST', 'admin', 'http://localhost:8088/admin/permission/assign', '[AssignPermissionDTO(permissionId=49, roleId=[1])]', '{\"code\":200,\"msg\":\"成功\"}', 0, '', '2025-05-28 13:32:00', 10);
INSERT INTO `sys_oper_log` VALUES (1309, '分配权限', 'insert', 'com.example.controller.admin.AdminPermissionController.assignPermission()', 'POST', 'admin', 'http://localhost:8088/admin/permission/assign', '[AssignPermissionDTO(permissionId=50, roleId=[1])]', '{\"code\":200,\"msg\":\"成功\"}', 0, '', '2025-05-28 13:32:03', 7);
INSERT INTO `sys_oper_log` VALUES (1310, '分配权限', 'insert', 'com.example.controller.admin.AdminPermissionController.assignPermission()', 'POST', 'admin', 'http://localhost:8088/admin/permission/assign', '[AssignPermissionDTO(permissionId=51, roleId=[1])]', '{\"code\":200,\"msg\":\"成功\"}', 0, '', '2025-05-28 13:32:05', 7);
INSERT INTO `sys_oper_log` VALUES (1311, '分配权限', 'insert', 'com.example.controller.admin.AdminPermissionController.assignPermission()', 'POST', 'admin', 'http://localhost:8088/admin/permission/assign', '[AssignPermissionDTO(permissionId=52, roleId=[1])]', '{\"code\":200,\"msg\":\"成功\"}', 0, '', '2025-05-28 13:32:08', 9);
INSERT INTO `sys_oper_log` VALUES (1312, '分配权限', 'insert', 'com.example.controller.admin.AdminPermissionController.assignPermission()', 'POST', 'admin', 'http://localhost:8088/admin/permission/assign', '[AssignPermissionDTO(permissionId=53, roleId=[1])]', '{\"code\":200,\"msg\":\"成功\"}', 0, '', '2025-05-28 13:32:11', 18);
INSERT INTO `sys_oper_log` VALUES (1313, '修改前台用户', 'update', 'com.example.controller.admin.AdminFrontUserController.updateFrontUser()', 'PUT', 'admin', 'http://localhost:8088/admin/front-user', '[FrontUser(id=1, username=admin1, password=null, name=admin1, phone=19976898051, email=108550738@qq.com, imgUrl=http://localhost:8088/common/files/1748412551117-logo.jpg, createTime=2025-04-24T10:33:25, updateTime=2025-05-28T14:09:21.497120300)]', '{\"code\":200,\"msg\":\"成功\"}', 0, '', '2025-05-28 14:09:22', 20);
INSERT INTO `sys_oper_log` VALUES (1314, '修改前台用户', 'update', 'com.example.controller.admin.AdminFrontUserController.updateFrontUser()', 'PUT', 'admin', 'http://localhost:8088/admin/front-user', '[FrontUser(id=1, username=admin, password=null, name=admin, phone=19976898057, email=208550738@qq.com, imgUrl=http://localhost:8088/common/files/1748412551117-logo.jpg, createTime=2025-04-24T10:33:25, updateTime=2025-05-28T14:09:37.059061700)]', '{\"code\":200,\"msg\":\"成功\"}', 0, '', '2025-05-28 14:09:37', 18);

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `permission_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `permission_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `permission_code`(`permission_code` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 54 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES (1, 'admin:notice:add', '新增通知', 'c', '2025-01-20 17:59:26', '2025-02-01 16:01:32');
INSERT INTO `sys_permission` VALUES (2, 'admin:notice:delete', '删除通知', 'r', '2025-01-20 18:00:08', '2025-02-01 16:01:39');
INSERT INTO `sys_permission` VALUES (3, 'admin:notice:update', '修改通知', 'u', '2025-01-20 18:00:24', '2025-02-01 16:01:45');
INSERT INTO `sys_permission` VALUES (4, 'admin:notice:query', '查询通知', 'd', '2025-01-20 18:00:45', '2025-02-01 16:01:50');
INSERT INTO `sys_permission` VALUES (5, 'admin:user:add', '新增用户', 'c', '2025-01-21 11:14:59', '2025-02-01 16:01:57');
INSERT INTO `sys_permission` VALUES (6, 'admin:user:delete', '删除用户', 'r', '2025-01-21 11:15:19', '2025-02-01 16:02:02');
INSERT INTO `sys_permission` VALUES (7, 'admin:user:update', '修改用户', 'u', '2025-01-21 11:15:31', '2025-02-01 16:02:10');
INSERT INTO `sys_permission` VALUES (8, 'admin:user:query', '查询用户', 'd', '2025-01-21 11:15:42', '2025-02-01 16:02:16');
INSERT INTO `sys_permission` VALUES (9, 'admin:operLog:delete', '删除日志', 'r', '2025-01-21 22:00:07', '2025-02-01 16:02:22');
INSERT INTO `sys_permission` VALUES (10, 'admin:operLog:update', '修改日志', 'u', '2025-01-21 22:00:33', '2025-02-01 16:02:27');
INSERT INTO `sys_permission` VALUES (11, 'admin:operLog:query', '查询日志', 'd', '2025-01-21 22:00:49', '2025-02-01 16:02:37');
INSERT INTO `sys_permission` VALUES (12, 'admin:notice:export', '导出通知', 'e', '2025-01-22 16:41:10', '2025-02-01 16:02:44');
INSERT INTO `sys_permission` VALUES (13, 'admin:user:export', '导出用户', 'e', '2025-01-22 16:41:48', '2025-02-01 16:02:50');
INSERT INTO `sys_permission` VALUES (14, 'admin:operLog:export', '导出日志', 'e', '2025-01-22 16:42:50', '2025-02-01 16:02:54');
INSERT INTO `sys_permission` VALUES (15, 'admin:permission:query', '查询访问权限', 'd', '2025-01-25 15:37:12', '2025-02-01 16:39:14');
INSERT INTO `sys_permission` VALUES (16, 'admin:person:query', '查询个人', 'd', '2025-01-25 15:53:17', '2025-02-01 16:03:08');
INSERT INTO `sys_permission` VALUES (24, 'admin:role:add', '新增角色', 'c', '2025-02-01 16:35:16', '2025-02-01 16:35:16');
INSERT INTO `sys_permission` VALUES (25, 'admin:role:delete', '删除角色', 'r', '2025-02-01 16:35:30', '2025-02-01 16:35:30');
INSERT INTO `sys_permission` VALUES (26, 'admin:role:update', '修改角色', 'u', '2025-02-01 16:35:50', '2025-02-01 16:35:50');
INSERT INTO `sys_permission` VALUES (27, 'admin:role:query', '查询角色', 'd', '2025-02-01 16:36:03', '2025-02-01 16:36:03');
INSERT INTO `sys_permission` VALUES (28, 'admin:role:export', '导出角色', 'e', '2025-02-01 16:36:30', '2025-02-01 16:36:30');
INSERT INTO `sys_permission` VALUES (29, 'admin:role:assign', '分配角色', 'a', '2025-02-01 16:36:45', '2025-02-01 16:36:45');
INSERT INTO `sys_permission` VALUES (30, 'admin:role:remove', '移除角色', 'r', '2025-02-01 16:36:59', '2025-02-01 16:36:59');
INSERT INTO `sys_permission` VALUES (31, 'admin:permission:add', '新增访问权限', 'c', '2025-02-01 16:37:56', '2025-02-01 16:37:56');
INSERT INTO `sys_permission` VALUES (32, 'admin:permission:delete', '删除访问权限', 'r', '2025-02-01 16:38:11', '2025-02-01 16:38:11');
INSERT INTO `sys_permission` VALUES (33, 'admin:permission:update', '修改访问权限', 'u', '2025-02-01 16:38:28', '2025-02-01 16:38:28');
INSERT INTO `sys_permission` VALUES (35, 'admin:permission:export', '导出访问权限', 'e', '2025-02-01 16:39:33', '2025-02-01 16:39:33');
INSERT INTO `sys_permission` VALUES (36, 'admin:permission:assign', '分配访问权限', 'a', '2025-02-01 16:39:49', '2025-02-01 16:39:49');
INSERT INTO `sys_permission` VALUES (37, 'admin:permission:remove', '移除访问权限', 'r', '2025-02-01 16:40:03', '2025-02-01 16:40:03');
INSERT INTO `sys_permission` VALUES (38, 'admin:docs:query', '接口文档查询', 'c', '2025-04-29 11:54:24', '2025-04-29 11:54:24');
INSERT INTO `sys_permission` VALUES (39, 'admin:dict:query', '查询字典', 'd', '2025-05-01 13:19:43', '2025-05-01 13:19:43');
INSERT INTO `sys_permission` VALUES (40, 'admin:com-query:query', '通用查询', 'd', '2025-05-01 13:20:22', '2025-05-01 13:20:22');
INSERT INTO `sys_permission` VALUES (41, 'admin:dict:add', '新增字典', 'c', '2025-05-01 13:31:28', '2025-05-01 13:31:28');
INSERT INTO `sys_permission` VALUES (42, 'admin:dict:delete', '删除字典', 'r', '2025-05-01 13:31:42', '2025-05-01 13:31:42');
INSERT INTO `sys_permission` VALUES (43, 'admin:dict:update', '修改字典', 'u', '2025-05-01 13:31:58', '2025-05-01 13:31:58');
INSERT INTO `sys_permission` VALUES (44, 'admin:dict:export', '导出字典', 'e', '2025-05-01 13:32:28', '2025-05-01 13:32:28');
INSERT INTO `sys_permission` VALUES (45, 'admin:com-query:add', '新增通用查询', 'c', '2025-05-01 13:32:51', '2025-05-01 13:32:51');
INSERT INTO `sys_permission` VALUES (46, 'admin:com-query:delete', '删除通用查询', 'r', '2025-05-01 13:33:04', '2025-05-01 13:33:04');
INSERT INTO `sys_permission` VALUES (47, 'admin:com-query:update', '修改通用查询', 'u', '2025-05-01 13:33:18', '2025-05-01 13:33:18');
INSERT INTO `sys_permission` VALUES (48, 'admin:com-query:export', '导出通用查询', 'e', '2025-05-01 13:33:41', '2025-05-01 13:33:41');
INSERT INTO `sys_permission` VALUES (49, 'admin:front-user:add', '新增前台用户', 'add', '2025-05-28 13:30:58', '2025-05-28 13:30:58');
INSERT INTO `sys_permission` VALUES (50, 'admin:front-user:delete', '删除前台用户', 'delete', '2025-05-28 13:31:13', '2025-05-28 13:31:13');
INSERT INTO `sys_permission` VALUES (51, 'admin:front-user:update', '修改前台用户', 'update', '2025-05-28 13:31:25', '2025-05-28 13:31:25');
INSERT INTO `sys_permission` VALUES (52, 'admin:front-user:query', '查询前台用户', 'query', '2025-05-28 13:31:36', '2025-05-28 13:31:36');
INSERT INTO `sys_permission` VALUES (53, 'admin:front-user:export', '导出前台用户', 'export', '2025-05-28 13:31:49', '2025-05-28 13:31:49');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `role_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `role_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `role_code`(`role_code` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, 'super_admin', '超级管理员', '上帝', '2025-01-20 17:49:56', '2025-01-20 17:54:06');
INSERT INTO `sys_role` VALUES (2, 'admin', '管理员', '小卡拉米', '2025-01-20 17:51:15', '2025-01-20 17:54:09');
INSERT INTO `sys_role` VALUES (3, 'user', '用户', '虾兵蟹将', '2025-01-20 17:54:00', '2025-01-20 17:54:11');

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `role_id` bigint NOT NULL,
  `permission_id` bigint NOT NULL,
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `role_id`(`role_id` ASC) USING BTREE,
  INDEX `permission_id`(`permission_id` ASC) USING BTREE,
  CONSTRAINT `sys_role_permission_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `sys_role_permission_ibfk_2` FOREIGN KEY (`permission_id`) REFERENCES `sys_permission` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 77 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
INSERT INTO `sys_role_permission` VALUES (1, 1, 1, '2025-01-20 18:01:46');
INSERT INTO `sys_role_permission` VALUES (2, 1, 2, '2025-01-20 18:01:54');
INSERT INTO `sys_role_permission` VALUES (3, 1, 3, '2025-01-20 18:02:02');
INSERT INTO `sys_role_permission` VALUES (4, 1, 4, '2025-01-20 18:02:08');
INSERT INTO `sys_role_permission` VALUES (5, 1, 5, '2025-01-20 18:04:00');
INSERT INTO `sys_role_permission` VALUES (6, 1, 6, '2025-01-20 18:04:27');
INSERT INTO `sys_role_permission` VALUES (7, 1, 7, '2025-01-20 18:04:36');
INSERT INTO `sys_role_permission` VALUES (9, 1, 9, '2025-01-21 11:16:50');
INSERT INTO `sys_role_permission` VALUES (10, 1, 10, '2025-01-21 11:16:57');
INSERT INTO `sys_role_permission` VALUES (11, 1, 11, '2025-01-21 11:17:10');
INSERT INTO `sys_role_permission` VALUES (12, 2, 3, '2025-01-21 11:18:40');
INSERT INTO `sys_role_permission` VALUES (13, 2, 4, '2025-01-21 11:19:29');
INSERT INTO `sys_role_permission` VALUES (14, 2, 8, '2025-01-21 22:01:53');
INSERT INTO `sys_role_permission` VALUES (15, 3, 4, '2025-01-21 22:02:03');
INSERT INTO `sys_role_permission` VALUES (17, 1, 12, '2025-01-22 16:43:15');
INSERT INTO `sys_role_permission` VALUES (18, 1, 13, '2025-01-22 16:43:21');
INSERT INTO `sys_role_permission` VALUES (19, 1, 14, '2025-01-22 16:43:28');
INSERT INTO `sys_role_permission` VALUES (20, 2, 12, '2025-01-22 16:44:19');
INSERT INTO `sys_role_permission` VALUES (21, 2, 13, '2025-01-22 16:44:28');
INSERT INTO `sys_role_permission` VALUES (22, 2, 14, '2025-01-22 16:44:39');
INSERT INTO `sys_role_permission` VALUES (23, 2, 11, '2025-01-22 16:46:08');
INSERT INTO `sys_role_permission` VALUES (26, 1, 16, '2025-01-25 15:53:31');
INSERT INTO `sys_role_permission` VALUES (27, 2, 16, '2025-01-25 15:53:39');
INSERT INTO `sys_role_permission` VALUES (28, 3, 16, '2025-01-25 15:53:46');
INSERT INTO `sys_role_permission` VALUES (34, 1, 15, '2025-01-30 20:25:15');
INSERT INTO `sys_role_permission` VALUES (38, 1, 37, '2025-02-01 16:43:23');
INSERT INTO `sys_role_permission` VALUES (39, 1, 36, '2025-02-01 16:43:30');
INSERT INTO `sys_role_permission` VALUES (40, 1, 35, '2025-02-01 16:43:33');
INSERT INTO `sys_role_permission` VALUES (41, 1, 33, '2025-02-01 16:43:36');
INSERT INTO `sys_role_permission` VALUES (42, 1, 32, '2025-02-01 16:43:45');
INSERT INTO `sys_role_permission` VALUES (43, 1, 31, '2025-02-01 16:43:49');
INSERT INTO `sys_role_permission` VALUES (44, 1, 30, '2025-02-01 16:43:52');
INSERT INTO `sys_role_permission` VALUES (45, 1, 29, '2025-02-01 16:43:55');
INSERT INTO `sys_role_permission` VALUES (46, 1, 28, '2025-02-01 16:43:58');
INSERT INTO `sys_role_permission` VALUES (47, 1, 27, '2025-02-01 16:44:01');
INSERT INTO `sys_role_permission` VALUES (48, 1, 26, '2025-02-01 16:44:05');
INSERT INTO `sys_role_permission` VALUES (49, 1, 25, '2025-02-01 16:44:08');
INSERT INTO `sys_role_permission` VALUES (50, 1, 24, '2025-02-01 16:44:11');
INSERT INTO `sys_role_permission` VALUES (56, 1, 38, '2025-04-29 11:54:27');
INSERT INTO `sys_role_permission` VALUES (57, 1, 39, '2025-05-01 13:20:43');
INSERT INTO `sys_role_permission` VALUES (60, 1, 40, '2025-05-01 13:20:48');
INSERT INTO `sys_role_permission` VALUES (61, 1, 48, '2025-05-01 13:35:23');
INSERT INTO `sys_role_permission` VALUES (62, 1, 47, '2025-05-01 13:35:27');
INSERT INTO `sys_role_permission` VALUES (63, 1, 46, '2025-05-01 13:35:29');
INSERT INTO `sys_role_permission` VALUES (64, 1, 45, '2025-05-01 13:35:32');
INSERT INTO `sys_role_permission` VALUES (65, 1, 44, '2025-05-01 13:35:35');
INSERT INTO `sys_role_permission` VALUES (66, 1, 43, '2025-05-01 13:35:37');
INSERT INTO `sys_role_permission` VALUES (67, 1, 42, '2025-05-01 13:35:39');
INSERT INTO `sys_role_permission` VALUES (68, 1, 41, '2025-05-01 13:35:42');
INSERT INTO `sys_role_permission` VALUES (72, 1, 49, '2025-05-28 13:32:00');
INSERT INTO `sys_role_permission` VALUES (73, 1, 50, '2025-05-28 13:32:02');
INSERT INTO `sys_role_permission` VALUES (74, 1, 51, '2025-05-28 13:32:05');
INSERT INTO `sys_role_permission` VALUES (75, 1, 52, '2025-05-28 13:32:07');
INSERT INTO `sys_role_permission` VALUES (76, 1, 53, '2025-05-28 13:32:10');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '姓名',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '手机号',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱',
  `img_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '图片路径',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'admin', 'e10adc3949ba59abbe56e057f20f883e', '李东升', '19976898057', '208550738@qq.com', 'http://localhost:8088/common/files/1746076011429-cat.png', '2025-01-19 13:54:17', '2025-05-26 21:12:59');
INSERT INTO `sys_user` VALUES (5, 'clh', 'e10adc3949ba59abbe56e057f20f883e', '龙欢', '19976898051', '208550738@qq.com', 'http://localhost:8088/common/files/1747746357669-微信图片f.jpg', '2025-01-19 18:20:45', '2025-05-20 21:05:58');
INSERT INTO `sys_user` VALUES (18, 'scx', 'e10adc3949ba59abbe56e057f20f883e', '史晨翔', '19976898057', '208550738@qq.com', 'http://localhost:8088/common/files/1747746344077-微信图片_20250105205358.jpg', '2025-01-27 15:47:30', '2025-05-20 21:42:36');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `role_id` bigint NOT NULL,
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_id`(`user_id` ASC) USING BTREE,
  INDEX `role_id`(`role_id` ASC) USING BTREE,
  CONSTRAINT `sys_user_role_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `sys_user_role_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 49 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (4, 5, 2, '2025-01-20 17:55:55');
INSERT INTO `sys_user_role` VALUES (28, 18, 3, '2025-01-30 20:14:43');
INSERT INTO `sys_user_role` VALUES (34, 1, 1, '2025-02-01 20:11:34');
INSERT INTO `sys_user_role` VALUES (36, 1, 2, '2025-02-01 21:22:53');
INSERT INTO `sys_user_role` VALUES (37, 1, 3, '2025-02-01 21:22:57');

SET FOREIGN_KEY_CHECKS = 1;
