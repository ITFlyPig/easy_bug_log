package com.wangyuelin.easybug.wrapper;

import com.wangyuelin.easybug.info.LogBean;
import com.wangyuelin.easybug.info.LogBeanCache;
import com.wangyuelin.easybug.log.LogQueue;

public class WrapperTask implements Runnable {
    private String className;
    private String methodName;
    private  Object[] args;
    private static volatile boolean isStop;

    public WrapperTask(String className, String methodName, Object[] args) {
        this.className = className;
        this.methodName = methodName;
        this.args = args;
    }

    @Override
    public void run() {
        LogBean logBean = LogBeanCache.getInstance().get();
        if (logBean == null) {//直接丢弃了
            return;
        }
        logBean.className = className;
        logBean.methodName = methodName;
        logBean.time = System.currentTimeMillis();
        logBean.args = args;
        try {
            LogQueue.queue.put(logBean);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
