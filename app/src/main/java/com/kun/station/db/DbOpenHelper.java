package com.kun.station.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbOpenHelper extends SQLiteOpenHelper{
    private static final String DB_NAME = "station.db";
    private static final int DB_VERSION = 1;

    public DbOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SqlConstants.CreateTableSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public long insert(String table,ContentValues values){
        return getWritableDatabase().insert(table, null, values);
    }

    public int update(String table,ContentValues values,
                      String where,String...whereArgs){
        return getWritableDatabase().update(table, values, where, whereArgs);
    }

    public int delete(String table,String where,String...whereArgs){
        return getWritableDatabase().delete(table, where, whereArgs);
    }

    public Cursor query(String table,String[] columns,String orderBy,
                        String selection,String...selectionArgs){
        return getReadableDatabase().query(table, columns, selection, selectionArgs, null, null, orderBy);
    }

    public Cursor query(String sql,String...args){
        return getReadableDatabase().rawQuery(sql, args);
    }
}
