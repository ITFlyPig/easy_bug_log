package com.wangyuelin.easybug.cache;

/**
 * 实体池的配置
 */
public class PoolConf {
    private int maxSize;//最大数量

    public int getMaxSize() {
        return maxSize;
    }

    public PoolConf(int maxSize) {
        this.maxSize = maxSize;
    }

    public static class Builder{

        private int maxSize;//最大数量

        public Builder setMaxSize(int maxSize) {
            this.maxSize = maxSize;
            return this;
        }

        public PoolConf build(){
            return new PoolConf(maxSize);

        }
    }
}
