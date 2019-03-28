package com.wangyuelin.easybug.cache;

public abstract class BasePooledObjectFactory<T> {
    public abstract T create();
    public abstract void reset(T t);


}
