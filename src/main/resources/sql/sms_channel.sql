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

 Date: 22/07/2021 13:43:39
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sms_channel
-- ----------------------------
DROP TABLE IF EXISTS `sms_channel`;
CREATE TABLE `sms_channel`  (
  `channel_id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint(11) NOT NULL COMMENT '使用用户id',
  `channel_token` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '通道token',
  `channel_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '通道名称',
  `channel_encryption_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '通道加密密码',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '生成时间',
  `is_deleted` tinyint(4) UNSIGNED ZEROFILL NOT NULL DEFAULT 0000 COMMENT '是否被删除',
  PRIMARY KEY (`channel_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
