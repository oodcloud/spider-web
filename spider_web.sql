/*
Navicat MySQL Data Transfer

Source Server         : 65.63
Source Server Version : 50717
Source Host           : 192.168.65.63:3306
Source Database       : spider_web

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-02-01 16:37:04
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for b_site_extopia_comment
-- ----------------------------
DROP TABLE IF EXISTS `b_site_extopia_comment`;
CREATE TABLE `b_site_extopia_comment` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `time` bigint(20) DEFAULT NULL,
  `text` text COMMENT '评论',
  `title` varchar(255) DEFAULT NULL,
  `type` int(11) DEFAULT NULL COMMENT '1:弹幕评论内容2：下面评论内容',
  `author` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `b_site_extopia_time` (`time`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=257651 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for b_site_mat_comment
-- ----------------------------
DROP TABLE IF EXISTS `b_site_mat_comment`;
CREATE TABLE `b_site_mat_comment` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `time` bigint(20) DEFAULT NULL,
  `text` text COMMENT '评论',
  `title` varchar(255) DEFAULT NULL,
  `type` int(11) DEFAULT NULL COMMENT '1:弹幕评论内容2：下面评论内容',
  `author` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `b_site_mat_time` (`time`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=24345 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for b_site_xmqz_comment
-- ----------------------------
DROP TABLE IF EXISTS `b_site_xmqz_comment`;
CREATE TABLE `b_site_xmqz_comment` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `time` bigint(20) DEFAULT NULL,
  `text` text COMMENT '评论',
  `title` varchar(255) DEFAULT NULL,
  `type` int(11) DEFAULT NULL COMMENT '1:弹幕评论内容2：下面评论内容',
  `author` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `b_site_xmqz_time` (`time`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=430386 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for bbs_site_comment
-- ----------------------------
DROP TABLE IF EXISTS `bbs_site_comment`;
CREATE TABLE `bbs_site_comment` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `time` bigint(20) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `text` text,
  `author` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `bbs_site_comment_time` (`time`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=233081 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for spider_config
-- ----------------------------
DROP TABLE IF EXISTS `spider_config`;
CREATE TABLE `spider_config` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `common_url` varchar(255) DEFAULT NULL COMMENT '组名',
  `url_rule` int(255) DEFAULT NULL,
  `growth_pattern` int(255) DEFAULT NULL,
  `start_num` int(255) DEFAULT NULL,
  `end_num` int(11) DEFAULT NULL,
  `group_name` varchar(255) DEFAULT NULL,
  `item_name` varchar(0) DEFAULT NULL,
  `site_name` varchar(0) DEFAULT NULL,
  `domain` varchar(255) DEFAULT NULL,
  `charset` varchar(11) DEFAULT NULL,
  `user_agent` text,
  `cookies` text,
  `sleep_time` int(11) DEFAULT NULL,
  `time_out` int(11) DEFAULT NULL,
  `thread` int(11) DEFAULT NULL,
  `match_fields` text,
  `headers` text,
  `generated_time` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='爬虫信息配置表';

-- ----------------------------
-- Table structure for spider_last_time
-- ----------------------------
DROP TABLE IF EXISTS `spider_last_time`;
CREATE TABLE `spider_last_time` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `spider_name` varchar(255) DEFAULT NULL,
  `last_time` bigint(20) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for tieba_extopia_site_comment
-- ----------------------------
DROP TABLE IF EXISTS `tieba_extopia_site_comment`;
CREATE TABLE `tieba_extopia_site_comment` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `time` bigint(20) DEFAULT NULL,
  `text` text,
  `title` varchar(255) DEFAULT NULL,
  `author` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `tieba_extopia_time` (`time`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=59483 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tieba_mat_site_comment
-- ----------------------------
DROP TABLE IF EXISTS `tieba_mat_site_comment`;
CREATE TABLE `tieba_mat_site_comment` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `time` bigint(20) DEFAULT NULL,
  `text` text,
  `title` varchar(255) DEFAULT NULL,
  `author` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `tieba_mat_time` (`time`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2556551 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tieba_xmqz_site_comment
-- ----------------------------
DROP TABLE IF EXISTS `tieba_xmqz_site_comment`;
CREATE TABLE `tieba_xmqz_site_comment` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `time` bigint(20) DEFAULT NULL,
  `text` text,
  `title` varchar(255) DEFAULT NULL,
  `author` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `tieba_xmqz_time` (`time`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=470052 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_name` varchar(255) DEFAULT NULL,
  `pass_word` varchar(255) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  `generate_time` bigint(20) DEFAULT NULL,
  `last_time` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for wechat_extopia_comment
-- ----------------------------
DROP TABLE IF EXISTS `wechat_extopia_comment`;
CREATE TABLE `wechat_extopia_comment` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `time` bigint(20) DEFAULT NULL,
  `text` text,
  `title` varchar(255) DEFAULT NULL,
  `author` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `wechat_extopia_time` (`time`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=83291 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for wechat_mat_comment
-- ----------------------------
DROP TABLE IF EXISTS `wechat_mat_comment`;
CREATE TABLE `wechat_mat_comment` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `time` bigint(20) DEFAULT NULL,
  `text` text,
  `title` varchar(255) DEFAULT NULL,
  `author` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `wechat_mat_time` (`time`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=38078 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for wechat_xmqz_comment
-- ----------------------------
DROP TABLE IF EXISTS `wechat_xmqz_comment`;
CREATE TABLE `wechat_xmqz_comment` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `time` bigint(20) DEFAULT NULL,
  `text` text,
  `title` varchar(255) DEFAULT NULL,
  `author` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=38078 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for weibo_extopia_site_comment
-- ----------------------------
DROP TABLE IF EXISTS `weibo_extopia_site_comment`;
CREATE TABLE `weibo_extopia_site_comment` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `time` bigint(20) DEFAULT NULL,
  `text` text,
  `title` text,
  `author` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `weibo_extopia_time` (`time`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=20103 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for weibo_mat_site_comment
-- ----------------------------
DROP TABLE IF EXISTS `weibo_mat_site_comment`;
CREATE TABLE `weibo_mat_site_comment` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `time` bigint(20) DEFAULT NULL,
  `text` text,
  `title` text,
  `author` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `weibo_mat_time` (`time`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=77583 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for weibo_producer_site_comment
-- ----------------------------
DROP TABLE IF EXISTS `weibo_producer_site_comment`;
CREATE TABLE `weibo_producer_site_comment` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `time` bigint(20) DEFAULT NULL,
  `text` text,
  `title` text,
  `author` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `weibo_producer_time` (`time`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=103404 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for weibo_xmqz_site_comment
-- ----------------------------
DROP TABLE IF EXISTS `weibo_xmqz_site_comment`;
CREATE TABLE `weibo_xmqz_site_comment` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `time` bigint(20) DEFAULT NULL,
  `text` text,
  `title` text,
  `author` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `weibo_xmqz_time` (`time`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=173596 DEFAULT CHARSET=utf8;
