package com.wangyuelin.easybug.log;

import com.wangyuelin.easybug.info.LogBean;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * 存放Log实体的队列：使用队列的目的是控制一定时间内产生的log信息数量，太多则丢弃，避免性能损耗
 * 但是使用实体池的那里就控制了，所以这里就不控制了
 */
public class LogQueue {
     public static LinkedBlockingQueue<LogBean> queue = new LinkedBlockingQueue<>();

}
