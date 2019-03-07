package com.wangyuelin.easybug.info;

import java.util.concurrent.LinkedBlockingQueue;

public class LogBeanCache {
    private LinkedBlockingQueue<LogBean> pool = new LinkedBlockingQueue<>();

    private int totalSize = 100;//总的容量，默认的容量是100个实体
    private int outSize;//被拿出去的个数

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
    public synchronized LogBean get() {
        LogBean logBean = null;
        if (pool.size() == 0) {//没有可用的实体
            if (outSize >= totalSize) {//借出去的和总数一样了，不能继续借了
                try {
                    logBean = pool.take();//等待有新的实例
                    outSize++;
                    return logBean;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {//还没达到上限，可以新创建实例
                logBean = new LogBean();
                outSize++;
                return logBean;
            }
        } else {//还有可用的实体
            try {
                logBean = pool.take();
                outSize++;
                return logBean;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return null;

    }

    /**
     * 将不使用的放入池中
     *
     * @param logBean
     */
    public synchronized void putBack(LogBean logBean) {
        if (logBean == null) {
            return;
        }

        logBean.reset();
        try {
            pool.put(logBean);
            outSize--;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
