package com.wangyuelin.easybug;

import android.content.Context;

import com.wangyuelin.easybug.handle.HandleTask;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 程序的入口
 */
public class EasyLog {
    private LogConf logConf;//存储配置信息
    private volatile boolean isNeedLog;//标志是否需要收集日志

    private Executor threadPool = Executors.newFixedThreadPool(3);

    private EasyLog(){}

    private static class Holder{
        private static EasyLog easyLog = new EasyLog();

    }

    /**
     * 获取实例
     * @return
     */
    public static EasyLog getInstance() {
        return Holder.easyLog;
    }

    /**
     * 使用配置信息初始化
     * @param logConf
     */
    public void init(LogConf logConf) {
        if (logConf == null) {
            throw new IllegalArgumentException("传入的配置LogConf不能为空");
        }
        this.logConf = logConf;

    }

    /**
     * 获得默认的配置
     * @return
     */
    public LogConf getDefaultConf(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("传入的Context不能为空");
        }

        LogConf logConf = new LogConf.Builder().setContext(context)
                .setMaxPoolSize(3)
                .setUploadInterval(1000 * 60 )//1分钟上报一次
                .build();
        return logConf;
    }

    public LogConf getLogConf() {
        return logConf;
    }

    /**
     * 开始收集Log
     */
    public void start() {
        if (logConf == null) {
            throw new IllegalStateException("开始之前必须先设置相关配置");
        }

        isNeedLog = true;

        execute(new HandleTask());//开启处理日志的线程
    }

    /**
     * 是否需要收集日志
     * @return
     */
    public boolean isNeedLog() {
        return isNeedLog;
    }

    /**
     * 将任务提交到线程池
     * @param runnable
     */
    public void execute(Runnable runnable) {
        threadPool.execute(runnable);
    }

}
