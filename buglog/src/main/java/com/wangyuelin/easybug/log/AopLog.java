package com.wangyuelin.easybug.log;

import com.wangyuelin.easybug.EasyLog;
import com.wangyuelin.easybug.wrapper.WrapperTask;

public class AopLog {
    /**
     * 进入方法开始记录日志
     * @param args
     * @param className
     * @param methodName
     */
    public static void methodEnter(String className, String methodName, Object[] args) {
        if (!EasyLog.getInstance().isNeedLog()) {
            return;
        }
        //将日志提交到待处理队列
        EasyLog.getInstance().execute(new WrapperTask(className, methodName, args, null, null));

    }

    public static void methodEnter(String className, String methodName, Object[] args, Exception exception, Error error) {
        if (!EasyLog.getInstance().isNeedLog()) {
            return;
        }
        //将日志提交到待处理队列
        EasyLog.getInstance().execute(new WrapperTask(className, methodName, args, exception, error));

    }
}
