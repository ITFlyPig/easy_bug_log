package com.wangyuelin.easybug.handle;

import com.wangyuelin.easybug.info.LogBean;
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
//                boolean result = LogManager.getInstance().insert(logBean);
//                Log.d("easylog", "插入数据库的结果：" + result + "===================================================");
//                Log.d("easylog", "插入数据库的数据：" + logBean.toString());
//                //放回池中
//                Log.e("wyl", "将使用完的Bean实例放回池中");
//                LogBeanCache.getInstance().putBack(logBean);
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
