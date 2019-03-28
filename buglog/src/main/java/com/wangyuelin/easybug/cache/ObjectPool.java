package com.wangyuelin.easybug.cache;

import com.wangyuelin.easybug.db.MyLogger;

import java.util.LinkedList;

public class ObjectPool<T> {
    private PoolConf poolConf;
    private BasePooledObjectFactory<T> factory;
    private LinkedList<T> list;
    private int curSize;//当前的视图数量

    public ObjectPool(PoolConf poolConf, BasePooledObjectFactory<T> factory) {
        this.poolConf = poolConf;
        this.factory = factory;
        list = new LinkedList<>();
    }

    /**
     * 借出实体
     * @return
     */
    public synchronized T borrowObject(){
        if (list.size() > 0) {//有空闲的实体
            T t = list.get(0);
            list.remove(0);
            MyLogger.d("tt","返回已空闲的实体");
            return t;
        }else {//检查是否还可以新建实体
            if (curSize < poolConf.getMaxSize()) {//创建新的实体
                curSize++;
                MyLogger.d("tt","创建新的实体返回");
                return factory.create();
            } else {//已经达到最大的数量了，没法借出
                MyLogger.d("tt","已经达到最大的数量了，没法借出");
                return null;
            }
        }
    }

    /**
     * 归还实体
     * @param t
     */
    public synchronized void returnObject(T t) {
        if (t == null) {
            return;
        }
        factory.reset(t);//重置实体，防止脏数据
        list.add(t);
        MyLogger.d("tt","归还实体");
    }

    /**
     * 注销
     */
    public void release() {
        list.clear();
    }


}
