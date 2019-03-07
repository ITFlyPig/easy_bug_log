package com.wangyuelin.easybug.handle;

import com.wangyuelin.easybug.info.LogBean;
import com.wangyuelin.easybug.log.LogQueue;

public class HandleTask implements Runnable {
    private volatile boolean isStop;

    @Override
    public void run() {
        while (!isStop) {
            LogBean logBean = null;
            try {
                logBean = LogQueue.queue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (logBean != null) {//加工处理，然后存入数据库

            }
        }
    }
}
