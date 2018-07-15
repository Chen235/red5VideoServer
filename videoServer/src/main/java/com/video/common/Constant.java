package com.video.common;

/**
 * Author : CJT
 * Date : 2017/6/19
 */
public interface Constant {

    int RED5_MAX_CONNECTION = 150;//RED5 最大连接数

    int RED5_MAX_SURVIVE_TIME = 8 * 60 * 1000;//每个链接最大链接时长为8分钟

    int RED5_TIMER_EXCUTE_TIME = 5 * 1000;//每个5分钟执行一次定时任务，清理过期的链接

}
