/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50720
 Source Host           : localhost:3306
 Source Schema         : mall

 Target Server Type    : MySQL
 Target Server Version : 50720
 File Encoding         : 65001

 Date: 03/06/2021 12:07:04
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `icon` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像',
  `email` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `nick_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `note` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注信息',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `login_time` datetime NULL DEFAULT NULL COMMENT '最后登录时间',
  `status` int(1) NULL DEFAULT 1 COMMENT '帐号启用状态：0->禁用；1->启用',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '后台用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES (1, 'test', '$2a$10$NZ5o7r2E.ayT2ZoxgjlI.eJ6OEYqjH7INR/F.mXDbjZJi9HF0YCVG', 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180607/timg.jpg', 'test@qq.com', '测试账号', NULL, '2018-09-29 13:55:30', '2018-09-29 13:55:39', 1);
INSERT INTO `admin` VALUES (3, 'admin', '$2a$10$IsYKhgez9HmV2kXrnNt0OuYWwKFAkSHTBUoWQ5Zq4NJS.LnEO7v3G', 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180607/timg.jpg', 'admin@163.com', '系统管理员', '系统管理员', '2018-10-08 13:32:47', '2019-04-20 12:45:16', 1);
INSERT INTO `admin` VALUES (4, 'like', '$2a$10$k.60GYHp7nK7GT0GxG5MVOcrvZNdnvtBoHarXZPEbd.bVPRU2keKW', 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180607/timg.jpg', 'like@qq.com', 'like', 'like专用', '2019-10-06 15:53:51', '2020-02-03 14:55:55', 1);
INSERT INTO `admin` VALUES (6, 'productAdmin', '$2a$10$6/.J.p.6Bhn7ic4GfoB5D.pGd7xSiD1a9M6ht6yO0fxzlKJPjRAGm', NULL, 'product@qq.com', '商品管理员', '只有商品权限', '2020-02-07 16:15:08', NULL, 1);
INSERT INTO `admin` VALUES (7, 'orderAdmin', '$2a$10$UqEhA9UZXjHHA3B.L9wNG.6aerrBjC6WHTtbv1FdvYPUI.7lkL6E.', NULL, 'order@qq.com', '订单管理员', '只有订单管理权限', '2020-02-07 16:15:50', NULL, 1);

drop table if exists client_details;
create table client_details
(
    client_id               VARCHAR(45) PRIMARY KEY COMMENT '客户端id',
    resource_ids            VARCHAR(255) COMMENT '资源服务器ids(例如后台，api接口)',
    client_secret           VARCHAR(255) COMMENT '客户端密码',
    scope                   VARCHAR(255) COMMENT '范围',
    authorized_grant_types  VARCHAR(255) COMMENT '认证方式例如authorization_code,password,refresh_token',
    web_server_redirect_uri VARCHAR(255) COMMENT '回调地址',
    authorities             VARCHAR(255) COMMENT '权限',
    access_token_validity   integer comment 'token有效时间',
    refresh_token_validity  integer comment 'refresh token有效时间',
    additional_information  VARCHAR(4096) comment '附加信息',
    autoapprove             VARCHAR(255) comment 'auto approve'
) ENGINE = InnoDB COMMENT = '认证客户端详情表';

INSERT INTO client_details
(client_id, client_secret, scope, authorized_grant_types,
 web_server_redirect_uri, authorities, access_token_validity,
 refresh_token_validity, additional_information, autoapprove)
VALUES
('myClient', '123456', 'read_userinfo,read_contacts',
 'password,authorization_code,refresh_token', 'http://127.0.0.1:9090/login', null, 3600, 864000, null, true);


create table if not exists client_token
(
    authentication_id VARCHAR(45) PRIMARY KEY comment '主键id',
    token_id          VARCHAR(255) comment 'token ID',
    username         VARCHAR(64) comment '用户名称',
    client_id         VARCHAR(255) comment 'client_details 表主键'
) ENGINE = InnoDB COMMENT = '客户token表';

create table if not exists token
(
    id VARCHAR(45) PRIMARY KEY comment '主键id',
    access_token             VARCHAR(255) comment 'access token',
    refresh_token     VARCHAR(255) comment  'refresh token',
    username         VARCHAR(64) comment '用户名称',
    client_id         VARCHAR(45) comment 'client_details 表主键',
    authentication    LONG comment '验证'
) ENGINE = InnoDB COMMENT = '访问令牌';

create table if not exists code
(
    code           VARCHAR(5) comment '授权码模式的code',
    authentication VARCHAR(255) comment '验证'
)ENGINE = InnoDB COMMENT = '授权码';

create table if not exists approvals
(
    username         VARCHAR(64) comment '用户名',
    client_id       VARCHAR(45) comment '客户端表主键id',
    scope          VARCHAR(255) comment '使用范围',
    status         VARCHAR(10) comment '状态',
    expiresAt      TIMESTAMP DEFAULT CURRENT_TIMESTAMP comment '过期时间',
    lastModifiedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP comment '最后修改时间'
) ENGINE = InnoDB COMMENT = '批准的用户';

DROP TABLE IF EXISTS `authorities`;
CREATE TABLE `authorities` (
                               `username` varchar(45) NOT NULL comment '用户名称',
                               `authority` varchar(50) NOT NULL comment '权限名称 ROLE_ADMIN',
                               UNIQUE KEY `ix_auth_username` (`username`,`authority`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT = '用户权限';


INSERT INTO `authorities` VALUES ('like', 'ROLE_ADMIN');