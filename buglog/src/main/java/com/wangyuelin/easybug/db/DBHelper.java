
package com.wangyuelin.easybug.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.liulishuo.okdownload.OkDownloadProvider;
import com.wangyuelin.easybug.info.LogBean;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class DBHelper extends SQLiteOpenHelper {

    private static final int DB_CACHE_VERSION = 1;

    static final Lock lock = new ReentrantLock();

    private TableEntity downloadTableEntity = new TableEntity(DBUtils.getTableName());

    DBHelper() {
        this(OkDownloadProvider.context);//这个解决方案不太好
    }

    DBHelper(Context context) {
        super(context, DBUtils.getTableName(), null, DB_CACHE_VERSION);


        downloadTableEntity
                .addColumn(new ColumnEntity(LogBean.CLASS_NAME, "VARCHAR"))
                .addColumn(new ColumnEntity(LogBean.METHOD_NAME, "VARCHAR"))
                .addColumn(new ColumnEntity(LogBean.TIME, "INTEGER"))
                .addColumn(new ColumnEntity(LogBean.ARGS, "VARCHAR"))
                .addColumn(new ColumnEntity(LogBean.EXECPTION, "VARCHAR"))
                .addColumn(new ColumnEntity(LogBean.ERROR, "VARCHAR"));
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(downloadTableEntity.buildTableString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (DBUtils.isNeedUpgradeTable(db, downloadTableEntity)) db.execSQL("DROP TABLE IF EXISTS " + DBUtils.getTableName());
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }


}
