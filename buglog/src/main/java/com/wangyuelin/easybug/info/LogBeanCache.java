package com.wangyuelin.easybug.info;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

public class LogBeanCache {

    public ObjectPool<LogBean> logbeanPool;

    private LogBeanCache() {
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        config.setMaxTotal(100);
        logbeanPool = new GenericObjectPool<>(new LogBeanFactory(), config);
    }

    private static class Holder {
        private static LogBeanCache logBeanCache = new LogBeanCache();
    }

    public static LogBeanCache getInstance() {
        return Holder.logBeanCache;
    }


    private static class LogBeanFactory extends BasePooledObjectFactory<LogBean> {

        @Override
        public LogBean create() throws Exception {
            return new LogBean();
        }

        @Override
        public PooledObject<LogBean> wrap(LogBean obj) {
            return new DefaultPooledObject<>(obj);
        }

    }

}
