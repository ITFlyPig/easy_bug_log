package com.wangyuelin.easybug.handle;

import android.util.Log;

import com.wangyuelin.easybug.info.LogBean;
import com.wangyuelin.easybug.info.LogBeanCache;
import com.wangyuelin.easybug.log.LogQueue;

public class HandleTask implements Runnable {
    private volatile boolean isStop;
    private static DBStrategy dbStrategy = new DBStrategy();

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
                Log.d("wyl", "从队列中获取日志bean，然后插入到数据库：" + logBean.toString());
                dbStrategy.insert(logBean);
            }
        }
    }


    /**
     * 停止执行
     *
     * @param stop
     */
    public void setStop(boolean stop) {
        isStop = stop;
    }
}
