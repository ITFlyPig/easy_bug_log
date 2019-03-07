/*
 * Copyright 2016 jeasonlzy(廖子尧)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wangyuelin.easybug.db;

import android.content.ContentValues;
import android.database.Cursor;

import com.wangyuelin.easybug.info.LogBean;

import java.util.List;


public class LogManager extends BaseDao<LogBean> {


    private LogManager() {
        super(new DBHelper());
    }

    public static LogManager getInstance() {
        return DownloadManagerHolder.instance;
    }

    private static class DownloadManagerHolder {
        private static final LogManager instance = new LogManager();
    }

    @Override
    public LogBean parseCursorToBean(Cursor cursor) {
        return LogBean.parseCursorToBean(cursor);
    }

    @Override
    public ContentValues getContentValues(LogBean bean) {
        return LogBean.buildContentValues(bean);
    }

    @Override
    public String getTableName() {
        return DBUtils.getTableName();
    }

    @Override
    public void unInit() {
    }


    /**
     * 获取特定时间范围内的数据
     * @param startTime
     * @param endTime
     * @return
     */
    public List<LogBean> query(long startTime, long endTime) {
       return query(LogBean.TIME + ">? AND" + LogBean.TIME +" <?", new String[]{String.valueOf(startTime), String.valueOf(endTime)});
    }


}
