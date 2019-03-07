
package com.wangyuelin.easybug.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.liulishuo.okdownload.OkDownloadProvider;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class DBHelper extends SQLiteOpenHelper {

    private static final String DB_CACHE_NAME = "download_file.db";
    private static final int DB_CACHE_VERSION = 1;
    static String TABLE_DOWNLOAD_PRE = "download";

    static final Lock lock = new ReentrantLock();

    private TableEntity downloadTableEntity = new TableEntity(DBUtils.getTableName());

    DBHelper() {
        this(OkDownloadProvider.context);//这个解决方案不太好
    }

    DBHelper(Context context) {
        super(context, DB_CACHE_NAME, null, DB_CACHE_VERSION);


        downloadTableEntity.addColumn(new ColumnEntity(DownloadStateTask.TAG, "VARCHAR", true, false))//
                .addColumn(new ColumnEntity(DownloadStateTask.ID, "INTEGER"))
                .addColumn(new ColumnEntity(DownloadStateTask.FILE_NAME, "VARCHAR"))
                .addColumn(new ColumnEntity(DownloadStateTask.SAVE_NAME, "VARCHAR"))
                .addColumn(new ColumnEntity(DownloadStateTask.CURRENT_SZIE, "INTEGER"))
                .addColumn(new ColumnEntity(DownloadStateTask.TOTAL_SIZE, "INTEGER"))
                .addColumn(new ColumnEntity(DownloadStateTask.PATH, "VARCHAR"))
                .addColumn(new ColumnEntity(DownloadStateTask.STATE, "INTEGER"))
                .addColumn(new ColumnEntity(DownloadStateTask.URL, "INTEGER"))
                .addColumn(new ColumnEntity(DownloadStateTask.DATE, "INTEGER"))
                .addColumn(new ColumnEntity(DownloadStateTask.FILE_TYPE, "VARCHAR"))
                .addColumn(new ColumnEntity(DownloadStateTask.EXTRA, "VARCHAR"));
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
