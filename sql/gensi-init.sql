/*
Navicat MySQL Data Transfer

Source Server         : 192.168.100.122
Source Server Version : 50636
Source Host           : 192.168.100.122:3306
Source Database       : ftoulcloud

Target Server Type    : MYSQL
Target Server Version : 50636
File Encoding         : 65001

Date: 2017-08-07 10:14:34
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for clientinfo
-- ----------------------------
DROP TABLE IF EXISTS `clientinfo`;
CREATE TABLE `clientinfo`  (
`sysId` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '系统标识',
`sysName` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '系统名称',
`privateKey` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '私有key',
`publicKey` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '公有Key',
`authedIp` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '授权的ip，多个ip以英文分号隔开',
`userName` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '系统用户名称',
`pwd` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '系统用户密码',
`notifyUrl` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '消息推送路径',
`notifyParam` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '消息推送参数',
`deleteFlag` varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '删除标识(预留)',
`reserve1` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '预留字段1',
`reserve2` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '预留字段2',
PRIMARY KEY (`sysId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of clientinfo
-- ----------------------------
INSERT INTO `clientinfo` VALUES ('MyApp', 'TulingMall-client-example', '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', NULL, NULL, NULL, 'http://localhost:8890/client/receiveMessage', NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for `hisrequest`
-- ----------------------------
DROP TABLE IF EXISTS `hisrequest`;
CREATE TABLE `hisrequest` (
  `transId` varchar(64) NOT NULL,
  `reqBody` longtext,
  `rspBody` longtext,
  `intime` varchar(32) DEFAULT NULL,
  `serviceCode` varchar(32) NOT NULL,
  `rsptime` varchar(32) DEFAULT NULL,
  `sysId` varchar(16) DEFAULT NULL,
  PRIMARY KEY (`transId`,`serviceCode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of hisrequest
-- ----------------------------
