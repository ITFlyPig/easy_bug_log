package com.wangyuelin.easybuglog;

import android.app.Application;

import com.wangyuelin.easybug.EasyLog;
import com.wangyuelin.easybug.LogConf;

public class MyApplication extends Application {
    private static MyApplication application;

    public static Application getInstance() {
        return application;

    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;


        init();
    }

    private void init() {
        LogConf logConf = EasyLog.getInstance().getDefaultConf(this);
        EasyLog.getInstance().init(logConf);
        EasyLog.getInstance().start();//打开收集的标志
    }
}
