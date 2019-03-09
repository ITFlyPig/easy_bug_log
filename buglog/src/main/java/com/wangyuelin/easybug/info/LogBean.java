package com.wangyuelin.easybug.info;

import android.content.ContentValues;
import android.database.Cursor;

public class LogBean {
    public String className;  //类名
    public String methodName; //方法名
    public Object[] args;     //方法参数
    public long time;         //log产生的时间
    public Exception exception;//异常
    public Error error;        //错误

    public String argsStr;
    public String execptionStr;
    public String errorStr;

    /**
     * 重置字段
     */
    public void reset() {
        className = "";
        methodName = "";
        args = null;
        time = 0;
        exception = null;
        error = null;

    }

    public static final String CLASS_NAME = "class_name";
    public static final String METHOD_NAME = "method_name";
    public static final String ARGS = "args";
    public static final String TIME = "time";
    public static final String EXECPTION = "execption";
    public static final String ERROR = "error";


    public static LogBean parseCursorToBean(Cursor cursor) {
        if (cursor == null) {
            return null;
        }
        LogBean bean = new LogBean();
        bean.className = cursor.getString(cursor.getColumnIndex(LogBean.CLASS_NAME));
        bean.methodName = cursor.getString(cursor.getColumnIndex(LogBean.METHOD_NAME));
        bean.argsStr = cursor.getString(cursor.getColumnIndex(LogBean.ARGS));
        bean.execptionStr = cursor.getString(cursor.getColumnIndex(LogBean.EXECPTION));
        bean.errorStr = cursor.getString(cursor.getColumnIndex(LogBean.ERROR));
        bean.time = cursor.getLong(cursor.getColumnIndex(LogBean.CLASS_NAME));
        return bean;

    }

    public static ContentValues buildContentValues(LogBean bean) {
        if (bean == null) {
            return null;
        }
        ContentValues values = new ContentValues();
        values.put(CLASS_NAME, bean.className);
        values.put(METHOD_NAME, bean.methodName);
        values.put(ARGS, bean.argsStr);
        values.put(EXECPTION, bean.execptionStr);
        values.put(ERROR, bean.errorStr);
        values.put(TIME, bean.time);
        return values;
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("LogBean:[ className = " + className + "] [methodName=" + methodName + "] [time=" + time + "] [argsStr=" + (argsStr == null ? "" : argsStr)
                + "] [execptionStr=" + (execptionStr == null ? "" : execptionStr) + "] 【errorStr=" + (errorStr == null ? "" : errorStr) + "]");
        return buffer.toString();
    }
}
