package com.kun.station.db;

/**
 * Created by admin on 2016/6/3.
 */
public class SqlConstants {
    public static final String Table_Name = "files";
    public static final String Key_FileName = "filename";
    public static final String Key_FilePath = "filepath";
    public static final String Key_Isdir = "isdir";
    public static final String Key_status = "status";

    public static final int Status_Normal = 0;
    public static final int Status_Store = 1;

    public static final String CreateTableSql =
            "create table if not exists files(_id integer primary key,"
                    + "filepath text,"
                    + "filename text,"
                    + "description text,"
                    + "isdir integer,"
                    + "status integer)";

    public static final String StoreQuerySql = "select * from files " +
            "where filepath = ? and filename = ?";

    public static final String GetStoreListSql = "select * from files " +
            "where status = ?";
}
