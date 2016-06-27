package com.kun.station.db;

/**
 * Created by admin on 2016/6/3.
 */
public class SqlConstants {
    public static final String Table_Name = "files";
    public static final String Key_FileName = "filename";
    public static final String Key_FilePath = "filepath";
    public static final String Key_Isdir = "isdir";
    public static final String Key_Download = "download";
    public static final String Key_Show = "show";
    public static final String Key_Store = "store";
    public static final String Key_Url = "url";
    public static final String Key_Detail = "detail";
    public static final String Key_Description = "description";
    public static final String Key_version = "version";

    public static final String CreateTableSql =
            "create table if not exists files(_id integer primary key,"
                    + "filepath text,"
                    + "filename text,"
                    + "description text,"
                    + "url text,"
                    + "detail text,"
                    + "version integer,"
                    + "isdir integer,"
                    + "download integer,"
                    + "show integer,"
                    + "store integer)";

    public static final String StoreQuerySql = "select * from files " +
            "where filepath = ? and filename = ? and store = ?";
    public static final String SingleQuerySql = "select * from files " +
            "where filepath = ? and filename = ?";

    public static final String DownLoadFileQuerySql = "select * from files " +
            "where filepath = ? and filename = ? and version = ? and download = ?";

    public static final String GetStoreListSql = "select * from files " +
            "where store = ? and download = 1";
    public static final String GetDownloadListSql = "select * from files " +
            "where download = ?";
    public static final String UPDATESTORE = "UPDATE files SET store = ? WHERE filepath = ? and filename = ?";
}
