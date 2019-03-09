package com.wangyuelin.easybug.handle;

import android.text.TextUtils;

import com.wangyuelin.easybug.db.DBUtils;
import com.wangyuelin.easybug.db.LogManager;
import com.wangyuelin.easybug.info.LogBean;
import com.wangyuelin.easybug.info.LogBeanCache;
import com.wangyuelin.easybug.utils.ParseUtil;

import java.util.Calendar;

/**
 * 主要是对DB做限制：避免频繁db操作和db中数据过多
 */
public class DBStrategy {
    private long maxDBNumber = 10000;//db中存储的数据的最大量
    private int numberStep = 1000; //数据量的操作步：比如是1000的话，如果当前超过的量不足1000则不用删除，否则删除，减少数据库操作次数
    private int maxDBTime = 2;//db中数据存储的最大时间，一般是两天天，因为今天发生bug，明天就会解决了,单位是天

    public DBStrategy(long maxDBNumber, int numberStep, int maxDBTime) {
        this.maxDBNumber = maxDBNumber;
        this.numberStep = numberStep;
        this.maxDBTime = maxDBTime;
    }

    public DBStrategy() {
    }

    /**
     * 反应db中数据的信息，不用每次插入都从数据库查询，节省性能，类似于缓存的功能
     */
    private static class DBInfo {
        public long maxTime;   //数据库里最大时间
        public long minTime;   //数据库里最小时间
        public long num;       //数据库总的条数
    }

    /**
     * 插入到数据库
     *
     * @param logBean
     * @return
     */

    private DBInfo dbInfo;

    private synchronized void initDBInfo() {
        if (dbInfo == null) {
            dbInfo = new DBInfo();
            String maxTimeStr = LogManager.getInstance().queryOne("SELECT MAX(time) FROM " + DBUtils.getTableName());
            if (!TextUtils.isEmpty(maxTimeStr)) {
                dbInfo.maxTime = ParseUtil.parseToLong(maxTimeStr, 0);
            }
            String minTimeStr = LogManager.getInstance().queryOne("SELECT MIN(time) FROM " + DBUtils.getTableName());
            if (!TextUtils.isEmpty(minTimeStr)) {
                dbInfo.minTime = ParseUtil.parseToLong(minTimeStr, 0);
            }

            String numberStr = LogManager.getInstance().queryOne("SELECT COUNT(time) FROM " + DBUtils.getTableName());
            if (!TextUtils.isEmpty(numberStr)) {
                dbInfo.num = ParseUtil.parseToLong(numberStr, 0);
            }
        }
    }

    /**
     * 插入数据库
     * @param logBean
     * @return
     */
    public synchronized boolean insert(LogBean logBean) {
        if (logBean == null) {
            return false;
        }
        initDBInfo();
        boolean result = LogManager.getInstance().insert(logBean);
        //更新缓存信息
        if (result) {
            if (dbInfo.minTime < logBean.time) {
                dbInfo.minTime = logBean.time;
            }
            if (dbInfo.maxTime > logBean.time) {
                dbInfo.maxTime = logBean.time;
            }
            dbInfo.num++;

            checkDB(dbInfo);
        }
        LogBeanCache.getInstance().putBack(logBean);
        return result;
    }


    /**
     * 检查数据库中日志是否需要删除一些
     * @param dbInfo
     */
    private synchronized void checkDB(DBInfo dbInfo) {
        if (dbInfo == null) {
            return;
        }

        long deleteTime = -1;
        //限制数量
        long shouldDelete = dbInfo.num - maxDBNumber;
        if (shouldDelete >= numberStep) {
            deleteTime = (dbInfo.maxTime - dbInfo.minTime) / 3 + dbInfo.minTime;//删除目前1/3时间存储的量
        }
        //限制时间，删除前天的
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -maxDBTime);
        long preTime = cal.getTimeInMillis();
        if (dbInfo.minTime < preTime) {
            deleteTime = preTime;
        }

        if (deleteTime > 0) {//开始删除
            LogManager.getInstance().delete(LogBean.TIME + "<= ?", new String[]{String.valueOf(deleteTime)});
            dbInfo = null;
            initDBInfo();
        }
    }


}
