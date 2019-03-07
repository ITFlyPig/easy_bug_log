package com.wangyuelin.easybug.log;

public class AopLog {
    /**
     * 进入方法开始记录日志
     * @param args
     * @param className
     * @param methodName
     */
    public static void methodEnter(String className, String methodName, Object[] args) {
        System.out.println(methodName + ":" + className);

    }
}
