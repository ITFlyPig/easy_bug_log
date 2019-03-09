package com.wangyuelin.easybug;

import android.content.Context;

public class LogConf {
    private Context context;
    private long uploadInterval;//上报Log的间隔
    private int maxPoolSize;   //实体池的最大量，可以限制一定时间内产生的Log数

    private LogConf(Builder builder) {
        context = builder.context;
        uploadInterval = builder.uploadInterval;
        maxPoolSize = builder.maxPoolSize;
    }


    public static class Builder{
        private Context context;
        private long uploadInterval;
        private int maxPoolSize;

        public Builder setContext(Context context) {
            this.context = context;
            return this;
        }

        public Builder setUploadInterval(long uploadInterval) {
            this.uploadInterval = uploadInterval;
            return this;

        }

        public Builder setMaxPoolSize(int maxPoolSize) {
            this.maxPoolSize = maxPoolSize;
            return this;

        }

        public LogConf build(){
            return new LogConf(this);
        }
    }


    public Context getContext() {
        return context;
    }

    public long getUploadInterval() {
        return uploadInterval;
    }

    public int getMaxPoolSize() {
        return maxPoolSize;
    }
}
