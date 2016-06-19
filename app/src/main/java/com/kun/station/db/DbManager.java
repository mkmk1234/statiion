package com.kun.station.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;

/**
 * Created by admin on 2016/6/3.
 */
public class DbManager {
    private DbOpenHelper mDbOpenHelper;
    private Context mContext;

    public DbManager(Context context){
        mContext = context;
        if (mDbOpenHelper == null){
            mDbOpenHelper = new DbOpenHelper(context);
        }
    }

    public void insertItemFile(String filePath, String fileName, boolean isStore, boolean isDir){
        if (isStore){
            ContentValues itemValues = new ContentValues();
            itemValues.put(SqlConstants.Key_FilePath, filePath);
            itemValues.put(SqlConstants.Key_FileName, fileName);
            itemValues.put(SqlConstants.Key_Isdir, isDir);
            itemValues.put(SqlConstants.Key_status, isStore ? SqlConstants.Status_Store : SqlConstants.Status_Normal);
            mDbOpenHelper.insert(SqlConstants.Table_Name, itemValues);
        } else {
            mDbOpenHelper.delete(SqlConstants.Table_Name, SqlConstants.Key_FileName + "= ? and " + SqlConstants.Key_FilePath + "= ?"
                ,new String[]{fileName, filePath});
        }
    }

    public void insertDownloadedFile(String filePath, String fileName, String version){
        ContentValues itemValues = new ContentValues();
        itemValues.put(SqlConstants.Key_FilePath, filePath);
        itemValues.put(SqlConstants.Key_FileName, fileName);
        itemValues.put(SqlConstants.Key_Isdir, false);
        itemValues.put(SqlConstants.Key_status, SqlConstants.Status_Downloaded);
        itemValues.put(SqlConstants.Key_version, version);
        mDbOpenHelper.insert(SqlConstants.Table_Name, itemValues);
    }

    public boolean isStoreFile(String filePath, String fileName){
        if(TextUtils.isEmpty(filePath) || TextUtils.isEmpty(fileName)){
            return false;
        }
        Cursor mCursor = mDbOpenHelper.query(SqlConstants.StoreQuerySql, filePath, fileName);
        if (mCursor != null && mCursor.getCount() > 0){
            mCursor.close();
            return true;
        }
        return false;
    }

    public boolean isDownLoadedFile(String filePath, String fileName, String version){
        if(TextUtils.isEmpty(filePath) || TextUtils.isEmpty(fileName)){
            return false;
        }
        Cursor mCursor = mDbOpenHelper.query(SqlConstants.DownLoadFileQuerySql, filePath, fileName, "1", String.valueOf(SqlConstants.Status_Downloaded));
        if (mCursor != null && mCursor.getCount() > 0){
            mCursor.close();
            return true;
        }
        return false;
    }

    public Cursor getStoreList(boolean isStore){
        return mDbOpenHelper.query(SqlConstants.GetStoreListSql, String.valueOf(isStore ? SqlConstants.Status_Store : SqlConstants.Status_Normal));
    }
}
