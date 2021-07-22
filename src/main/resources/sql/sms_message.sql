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

 Date: 22/07/2021 16:25:28
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sms_message
-- ----------------------------
DROP TABLE IF EXISTS `sms_message`;
CREATE TABLE `sms_message`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '总id（自增',
  `channel_token` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '通道名',
  `from_place` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '来源手机',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '内容',
  `timestamp` datetime NULL DEFAULT NULL COMMENT '插入时间',
  `sign` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '签名',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '插入时间',
  `is_deleted` tinyint(255) NULL DEFAULT 0 COMMENT '是否被删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 56 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
