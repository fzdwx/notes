/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80024
 Source Host           : localhost:3306
 Source Schema         : mall

 Target Server Type    : MySQL
 Target Server Version : 80024
 File Encoding         : 65001

 Date: 06/06/2021 10:12:41
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `icon` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像',
  `email` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `nick_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `note` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注信息',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `login_time` datetime(0) NULL DEFAULT NULL COMMENT '最后登录时间',
  `status` int(0) NULL DEFAULT 1 COMMENT '帐号启用状态：0->禁用；1->启用',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '后台用户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES (1, 'test', '$2a$10$NZ5o7r2E.ayT2ZoxgjlI.eJ6OEYqjH7INR/F.mXDbjZJi9HF0YCVG', 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180607/timg.jpg', 'test@qq.com', '测试账号', NULL, '2018-09-29 13:55:30', '2018-09-29 13:55:39', 1);
INSERT INTO `admin` VALUES (3, 'admin', '$2a$10$IsYKhgez9HmV2kXrnNt0OuYWwKFAkSHTBUoWQ5Zq4NJS.LnEO7v3G', 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180607/timg.jpg', 'admin@163.com', '系统管理员', '系统管理员', '2018-10-08 13:32:47', '2019-04-20 12:45:16', 1);
INSERT INTO `admin` VALUES (4, 'like', '$2a$10$k.60GYHp7nK7GT0GxG5MVOcrvZNdnvtBoHarXZPEbd.bVPRU2keKW', 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180607/timg.jpg', 'like@qq.com', 'like', 'like专用', '2019-10-06 15:53:51', '2020-02-03 14:55:55', 1);
INSERT INTO `admin` VALUES (6, 'productAdmin', '$2a$10$6/.J.p.6Bhn7ic4GfoB5D.pGd7xSiD1a9M6ht6yO0fxzlKJPjRAGm', NULL, 'product@qq.com', '商品管理员', '只有商品权限', '2020-02-07 16:15:08', NULL, 1);
INSERT INTO `admin` VALUES (7, 'orderAdmin', '$2a$10$UqEhA9UZXjHHA3B.L9wNG.6aerrBjC6WHTtbv1FdvYPUI.7lkL6E.', NULL, 'order@qq.com', '订单管理员', '只有订单管理权限', '2020-02-07 16:15:50', NULL, 1);

-- ----------------------------
-- Table structure for admin_authority
-- ----------------------------
DROP TABLE IF EXISTS `admin_authority`;
CREATE TABLE `admin_authority`  (
  `ID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '用户职权 ID',
  `NAME` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '职权名称',
  `DESCRIPTION` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '职权描述',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '用户职权. 如 ADMIN, USER etc. 职权代表了一簇可访问的资源集 (RESOURCE).' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin_authority
-- ----------------------------
INSERT INTO `admin_authority` VALUES ('4', 'ADMIN', '管理员');
INSERT INTO `admin_authority` VALUES ('e8ad77d15cc04c3097658d945714d63c', 'USER', '用户');

-- ----------------------------
-- Table structure for client
-- ----------------------------
DROP TABLE IF EXISTS `client`;
CREATE TABLE `client`  (
  `ID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '客户端 ID',
  `CLIENT_SECRET` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '客户端 Secret (加密后)',
  `SCOPE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '客户端 Scope (英文逗号分隔)',
  `AUTHORIZED_GRANT_TYPE` varchar(70) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '授权方式, 只可能是: authorization_code,implicit,refresh_token,password,client_credentials.\r\n如果是多个, 以英文逗号分隔.',
  `REDIRECT_URI` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '重定向地址, 当授权方式是 authorization_code 时有效. 如果有多个, 按英文逗号分隔.',
  `ACCESS_TOKEN_VALIDITY` int(0) NULL DEFAULT 120 COMMENT 'access-token 过期时间 (秒)',
  `REFRESH_TOKEN_VALIDITY` int(0) NULL DEFAULT 240 COMMENT 'refresh-token 过期时间 (秒)',
  `AUTO_APPROVE` tinyint(1) NULL DEFAULT 0 COMMENT '是否自动允许',
  `DESCRIPTION` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '客户端描述',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '客户端' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of client
-- ----------------------------
INSERT INTO `client` VALUES ('client', '$2a$10$W0af8zbYneYlIBlWo.pkXue6K9cQTeAfTfRvt7J3.xbjPsuDAx146', 'ACCESS_RESOURCE', 'authorization_code,password,implicit,client_credentials,refresh_token', 'http://127.0.0.1:9090/callback', 1288, 2408, 1, 'client_secret: client-a-p');

-- ----------------------------
-- Table structure for client_access_scope
-- ----------------------------
DROP TABLE IF EXISTS `client_access_scope`;
CREATE TABLE `client_access_scope`  (
  `ID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '客户端访问范围 ID',
  `SCOPE` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '客户端访问范围编码',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of client_access_scope
-- ----------------------------
INSERT INTO `client_access_scope` VALUES ('9922e6a2b8314043a7fd174e8869922b', 'ACCESS_RESOURCE');

-- ----------------------------
-- Table structure for client_authority
-- ----------------------------
DROP TABLE IF EXISTS `client_authority`;
CREATE TABLE `client_authority`  (
  `ID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '客户端职权 ID',
  `NAME` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '职权名称',
  `DESCRIPTION` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '职权描述',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '客户端职权. 职权代表了一簇可访问的资源集 (RESOURCE).' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of client_authority
-- ----------------------------
INSERT INTO `client_authority` VALUES ('11cffc50570a4866819cee58d695b703', 'SENIOR_CLIENT', '初级');
INSERT INTO `client_authority` VALUES ('11cffc50570a4866819cee58d695b723', 'THIRD_PARTY_CLIENT', '第三方客户端');
INSERT INTO `client_authority` VALUES ('4300f23105084396a86c3ff301e54e3e', 'FIRST_PARTY_FRONTEND_CLIENT', '第一方前端');
INSERT INTO `client_authority` VALUES ('a54ba268a6604b54b2ac26990310b3ee', 'INTERMEDIATE_CLIENT', '中级');
INSERT INTO `client_authority` VALUES ('bade7a63483640bab2d79cd2b78fde48', 'FIRST_PARTY_BACKEND_CLIENT', '第一方后端');
INSERT INTO `client_authority` VALUES ('bade7a63483640bab2d79cd2b78fdea8', 'JUNIOR_CLIENT', '高级');

-- ----------------------------
-- Table structure for mapping_admin_authority_to_resource
-- ----------------------------
DROP TABLE IF EXISTS `mapping_admin_authority_to_resource`;
CREATE TABLE `mapping_admin_authority_to_resource`  (
  `ADMIN_AUTHORITY_ID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '用户职权 ID',
  `RESOURCE_ID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '资源 ID',
  PRIMARY KEY (`ADMIN_AUTHORITY_ID`, `RESOURCE_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '用户职权和资源的映射表.' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of mapping_admin_authority_to_resource
-- ----------------------------
INSERT INTO `mapping_admin_authority_to_resource` VALUES ('4', 'c9f1dcc6effc432eb0c474b307b6cec4');

-- ----------------------------
-- Table structure for mapping_admin_to_admin_authority
-- ----------------------------
DROP TABLE IF EXISTS `mapping_admin_to_admin_authority`;
CREATE TABLE `mapping_admin_to_admin_authority`  (
  `ADMIN_ID` bigint(0) NOT NULL COMMENT '用户 ID',
  `ADMIN_AUTHORITY_ID` varbinary(32) NOT NULL COMMENT '用户职权 ID',
  PRIMARY KEY (`ADMIN_ID`, `ADMIN_AUTHORITY_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '用户和用户职权的映射表.' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of mapping_admin_to_admin_authority
-- ----------------------------
INSERT INTO `mapping_admin_to_admin_authority` VALUES (4, 0x340A);

-- ----------------------------
-- Table structure for mapping_client_access_scope_to_resource
-- ----------------------------
DROP TABLE IF EXISTS `mapping_client_access_scope_to_resource`;
CREATE TABLE `mapping_client_access_scope_to_resource`  (
  `CLIENT_ACCESS_SCOPE_ID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '客户端访问范围 ID',
  `RESOURCE_ID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '资源 ID',
  PRIMARY KEY (`CLIENT_ACCESS_SCOPE_ID`, `RESOURCE_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of mapping_client_access_scope_to_resource
-- ----------------------------
INSERT INTO `mapping_client_access_scope_to_resource` VALUES ('9922e6a2b8314043a7fd174e8869922b', 'c9f1dcc6effc432eb0c474b307b6cec3');

-- ----------------------------
-- Table structure for mapping_client_authority_to_resource
-- ----------------------------
DROP TABLE IF EXISTS `mapping_client_authority_to_resource`;
CREATE TABLE `mapping_client_authority_to_resource`  (
  `CLIENT_AUTHORITY_ID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '客户端职权 ID',
  `RESOURCE_ID` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '标识资源的 ID',
  PRIMARY KEY (`CLIENT_AUTHORITY_ID`, `RESOURCE_ID`) USING BTREE,
  INDEX `FK_MCATR_RESOURCE_ID_RESOURCE`(`RESOURCE_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of mapping_client_authority_to_resource
-- ----------------------------
INSERT INTO `mapping_client_authority_to_resource` VALUES ('11cffc50570a4866819cee58d695b703', 'c9f1dcc6effc432eb0c474b307b6cec3');

-- ----------------------------
-- Table structure for mapping_client_to_client_access_scope
-- ----------------------------
DROP TABLE IF EXISTS `mapping_client_to_client_access_scope`;
CREATE TABLE `mapping_client_to_client_access_scope`  (
  `CLIENT_ID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '客户端 ID',
  `CLIENT_ACCESS_SCOPE_ID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '客户端访问范围 ID',
  PRIMARY KEY (`CLIENT_ID`, `CLIENT_ACCESS_SCOPE_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of mapping_client_to_client_access_scope
-- ----------------------------
INSERT INTO `mapping_client_to_client_access_scope` VALUES ('client', '9922e6a2b8314043a7fd174e8869922b');
INSERT INTO `mapping_client_to_client_access_scope` VALUES ('client-a', '9922e6a2b8314043a7fd174e8869922b');

-- ----------------------------
-- Table structure for mapping_client_to_client_authority
-- ----------------------------
DROP TABLE IF EXISTS `mapping_client_to_client_authority`;
CREATE TABLE `mapping_client_to_client_authority`  (
  `CLIENT_ID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '客户端 ID',
  `CLIENT_AUTHORITY_ID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '客户端职权 ID',
  PRIMARY KEY (`CLIENT_ID`, `CLIENT_AUTHORITY_ID`) USING BTREE,
  INDEX `FK_MCTCA_CLIENT_AUTHORITY_ID_CLIENT_AUTHORITY`(`CLIENT_AUTHORITY_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '客户端到客户端职权的映射表.' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of mapping_client_to_client_authority
-- ----------------------------
INSERT INTO `mapping_client_to_client_authority` VALUES ('client', '11cffc50570a4866819cee58d695b703');

-- ----------------------------
-- Table structure for mapping_client_to_resource_server
-- ----------------------------
DROP TABLE IF EXISTS `mapping_client_to_resource_server`;
CREATE TABLE `mapping_client_to_resource_server`  (
  `CLIENT_ID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '客户端 ID',
  `RESOURCE_SERVER_ID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '资源服务器 ID',
  PRIMARY KEY (`CLIENT_ID`, `RESOURCE_SERVER_ID`) USING BTREE,
  INDEX `FK_MCTRS_RESOURCE_SERVER_ID_RESOURCE_SERVER`(`RESOURCE_SERVER_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '客户端到资源资源服务器的映射表. 标识了一个客户端可以访问的资源服务器.' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of mapping_client_to_resource_server
-- ----------------------------
INSERT INTO `mapping_client_to_resource_server` VALUES ('client', 'resource-server');

-- ----------------------------
-- Table structure for resource
-- ----------------------------
DROP TABLE IF EXISTS `resource`;
CREATE TABLE `resource`  (
  `ID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '标识资源的 ID',
  `ENDPOINT` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '资源端点, 支持通配符',
  `RESOURCE_SERVER_ID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '资源服务器 ID, 用以标识当前端点属于哪个资源服务.\r\n外键约束 RESOURCE_SERVER 的 ID.',
  PRIMARY KEY (`ID`) USING BTREE,
  INDEX `FK_R_RESOURCE_SERVER_ID_2_RS`(`RESOURCE_SERVER_ID`) USING BTREE,
  CONSTRAINT `FK_R_RESOURCE_SERVER_ID_2_RS` FOREIGN KEY (`RESOURCE_SERVER_ID`) REFERENCES `resource_server` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '资源. 代表着形如 /user/1 的具体的资源本身.' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of resource
-- ----------------------------
INSERT INTO `resource` VALUES ('c9f1dcc6effc432eb0c474b307b6cec3', '/resource/access', 'resource-server');
INSERT INTO `resource` VALUES ('c9f1dcc6effc432eb0c474b307b6cec4', '/hello', 'resource-server');

-- ----------------------------
-- Table structure for resource_server
-- ----------------------------
DROP TABLE IF EXISTS `resource_server`;
CREATE TABLE `resource_server`  (
  `ID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '资源服务器 ID',
  `RESOURCE_SECRET` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '资源密钥 (加密后)',
  `DESCRIPTION` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '资源服务器描述',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '资源服务器. 可提供客户端访问的资源服务器定义.' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of resource_server
-- ----------------------------
INSERT INTO `resource_server` VALUES ('resource-server', 'resource-server-p', '资源服务器');

SET FOREIGN_KEY_CHECKS = 1;
