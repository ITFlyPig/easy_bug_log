package com.wangyuelin.easybug.info;


import com.wangyuelin.easybug.cache.BasePooledObjectFactory;
import com.wangyuelin.easybug.cache.ObjectPool;
import com.wangyuelin.easybug.cache.PoolConf;

/**
 * 缓存bean的实例
 */
public class LogBeanCache {

    public ObjectPool<LogBean> logbeanPool;

    private LogBeanCache() {
        PoolConf config = new PoolConf.Builder()
                .setMaxSize(300)
                .build();
        logbeanPool = new ObjectPool<LogBean>(config, new LogBeanFactory());
    }

    private static class Holder {
        private static LogBeanCache logBeanCache = new LogBeanCache();
    }

    public static LogBeanCache getInstance() {
        return Holder.logBeanCache;
    }


    private static class LogBeanFactory extends BasePooledObjectFactory<LogBean> {

        @Override
        public LogBean create()  {
            return new LogBean();
        }

        @Override
        public void reset(LogBean logBean) {
            if (logBean == null) {
                return;
            }
            logBean.reset();
        }


    }

    /**
     * 借出实体
     * @return
     */
    public LogBean borrowObject(){
        return logbeanPool.borrowObject();
    }

    /**
     * 归还实体
     * @param logBean
     */
    public void returnObject(LogBean logBean) {
        logbeanPool.returnObject(logBean);
    }




}
