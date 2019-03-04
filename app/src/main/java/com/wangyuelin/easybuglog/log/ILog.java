package com.wangyuelin.easybuglog.log;

import java.util.List;

public interface ILog {
    /**
     * 当方法进入的的处理
     * @param args
     * @param methodName
     * @param belongClass
     */
    void methodEnter(List<Object> args, String methodName, String belongClass);

    /**
     * 当方法退出的处理
     * @param args
     * @param methodName
     * @param belongClass
     */
    void methodExit(List<Object> args, String methodName, String belongClass);
}
