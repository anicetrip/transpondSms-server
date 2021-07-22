/*
 Navicat Premium Data Transfer

 Source Server         : dscsms
 Source Server Type    : MySQL
 Source Server Version : 50650
 Source Host           : 23.106.158.242:3306
 Source Schema         : dashucunsms_cyou

 Target Server Type    : MySQL
 Target Server Version : 50650
 File Encoding         : 65001

 Date: 22/07/2021 13:44:18
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sms_user
-- ----------------------------
DROP TABLE IF EXISTS `sms_user`;
CREATE TABLE `sms_user`  (
  `user_id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `login_phone` bigint(20) NULL DEFAULT NULL COMMENT '手机号',
  `login_email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `token` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '信息存储用token',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(4) NOT NULL COMMENT '是否被删除',
  `secret_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '找回密码用密钥',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
