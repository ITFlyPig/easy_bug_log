package com.wangyuelin.easybug.wrapper;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.wangyuelin.easybug.info.LogBean;
import com.wangyuelin.easybug.info.LogBeanCache;
import com.wangyuelin.easybug.log.LogQueue;

public class WrapperTask implements Runnable {
    private String className;
    private String methodName;
    private Object[] args;
    public Exception exception;//异常
    public Error error;        //错误

    public WrapperTask(String className, String methodName, Object[] args, Exception exception, Error error) {
        this.className = className;
        this.methodName = methodName;
        this.args = args;
        this.exception = exception;
        this.error = error;
    }

    @Override
    public void run() {
        LogBean logBean = null;
        logBean = LogBeanCache.getInstance().borrowObject();
        if (logBean == null) {//直接丢弃了
            return;
        }
        logBean.className = className;
        logBean.methodName = methodName;
        logBean.time = System.currentTimeMillis();
        logBean.args = args;
        logBean.exception = exception;
        logBean.error = error;
        if (logBean.error != null) {
            logBean.errorStr = JSON.toJSONString(logBean.error);
        }
        if (logBean.exception != null) {
            logBean.execptionStr = JSON.toJSONString(logBean.exception);
        }
        if (logBean.args != null) {
            logBean.argsStr = JSON.toJSONString(logBean.args);
        }

        Log.d("wyl", "将新产生的bean放入队列：" + logBean.toString());
        try {
            LogQueue.queue.put(logBean);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
