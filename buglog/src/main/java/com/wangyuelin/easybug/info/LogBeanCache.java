package com.wangyuelin.easybug.info;

import android.util.Log;

import com.wangyuelin.easybug.EasyLog;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class LogBeanCache {
    private LinkedList<LogBean> pool = new LinkedList<>();
    private ReentrantLock lock = new ReentrantLock();
    private Condition empty = lock.newCondition();

    private int totalSize;//总的容量，默认的容量是100个实体
    private int count;//统计new出的bean的个数

    private LogBeanCache() {
        totalSize = EasyLog.getInstance().getLogConf().getMaxPoolSize();
    }

    private static class Holder {
        private static LogBeanCache logBeanCache = new LogBeanCache();
    }

    public static LogBeanCache getInstance() {
        return Holder.logBeanCache;
    }

    /**
     * 获取一个可用的实例
     *
     * @return
     */
    public  LogBean get() {
        lock.lock();
        try {
            LogBean logBean = null;
            while (pool.size() == 0 && count >= totalSize) {//借出去的和总数一样了，不能继续借了
                try {
                    Log.e("wyl", "bean不够，开始阻塞");
                    empty.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

            //可以借
            if (pool.size() == 0) {
                logBean = new LogBean();
                count++;
                Log.e("wyl", "新new出bean实例");
            } else {
                logBean = pool.remove(0);
                Log.e("wyl", "bean够，从池中获取");

            }
            return logBean;
        } finally {
            lock.unlock();
        }


    }

    /**
     * 将不使用的放入池中
     *
     * @param logBean
     */
    public  void putBack(LogBean logBean) {
        if (logBean == null) {
            return;
        }
        lock.lock();
        try {
            logBean.reset();
            pool.add(logBean);
            empty.signal();
            Log.e("wyl", "将使用完的bean实例放入池中");

        } finally {
            lock.unlock();
        }


    }

}
