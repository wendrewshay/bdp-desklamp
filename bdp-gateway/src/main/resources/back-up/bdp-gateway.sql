/*
Navicat MySQL Data Transfer

Source Server         : 本地
Source Server Version : 50721
Source Host           : localhost:3306
Source Database       : bdp-gateway

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2019-03-26 22:11:00
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for client_account
-- ----------------------------
DROP TABLE IF EXISTS `client_account`;
CREATE TABLE `client_account` (
  `userid` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(100) NOT NULL COMMENT '用户密码',
  `enabled` tinyint(4) DEFAULT '1' COMMENT '是否可用:1-可用；0-禁用',
  `accountNonExpired` tinyint(4) DEFAULT '1' COMMENT '账户未过期：1-是；0-否',
  `credentialsNonExpired` tinyint(4) DEFAULT '1' COMMENT '凭证未过期：1-是；0-否',
  `accountNonLocked` tinyint(4) DEFAULT '1' COMMENT '账户未锁定：1-是；0-否',
  `createTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`userid`),
  UNIQUE KEY `ACCOUNT_IDX` (`username`) USING BTREE COMMENT '用户名索引'
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='客户账户表';

-- ----------------------------
-- Records of client_account
-- ----------------------------
INSERT INTO `client_account` VALUES ('1', 'joney', '$2a$04$yIimQwF9RPG3sgzk0j8Z8uRSQMxtgqQVTqrrjsafaT4Z4GQTnmEtq', '1', '1', '1', '1', '2019-03-26 16:06:35');

-- ----------------------------
-- Table structure for client_authority
-- ----------------------------
DROP TABLE IF EXISTS `client_authority`;
CREATE TABLE `client_authority` (
  `authorityid` int(11) NOT NULL AUTO_INCREMENT COMMENT '权限ID',
  `authorityName` varchar(50) DEFAULT NULL COMMENT '权限名称',
  PRIMARY KEY (`authorityid`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='客户权限表';

-- ----------------------------
-- Records of client_authority
-- ----------------------------
INSERT INTO `client_authority` VALUES ('1', '/enterprise-data');
INSERT INTO `client_authority` VALUES ('2', '/enterprise-portrait');

-- ----------------------------
-- Table structure for client_relations
-- ----------------------------
DROP TABLE IF EXISTS `client_relations`;
CREATE TABLE `client_relations` (
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `authorityName` varchar(50) NOT NULL COMMENT '权限名称',
  KEY `U_IDX` (`username`) USING BTREE COMMENT '用户索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='客户账户权限关系表';

-- ----------------------------
-- Records of client_relations
-- ----------------------------
INSERT INTO `client_relations` VALUES ('joney', '/enterprise-data');
INSERT INTO `client_relations` VALUES ('joney', '/enterprise-portrait');
