package com.mcc.ghurbo.data.sqlite;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class DbConstants implements BaseColumns {

    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String PRIMARY = "  PRIMARY KEY";
    private static final String COMMA = ",";
    public static final String COLUMN_NAME_NULLABLE = null;

    public static final String FAV_TABLE_NAME = "fav_items";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_IMAGE = "image";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_LOCATION = "location";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_CHECK_IN = "checkin";
    public static final String COLUMN_CHECK_OUT = "checkout";
    public static final String COLUMN_ROOMS = "rooms";
    public static final String COLUMN_ADULT = "adult";
    public static final String COLUMN_CHILD = "child";

    public static final String SQL_CREATE_FAV_ENTRIES =
            "CREATE TABLE " + FAV_TABLE_NAME + " (" +
                    _ID + INTEGER_TYPE + PRIMARY + COMMA +
                    COLUMN_ID + TEXT_TYPE + COMMA +
                    COLUMN_TITLE + TEXT_TYPE + COMMA +
                    COLUMN_IMAGE + TEXT_TYPE + COMMA +
                    COLUMN_PRICE + TEXT_TYPE + COMMA +
                    COLUMN_TYPE + TEXT_TYPE + COMMA +
                    COLUMN_DATE + TEXT_TYPE + COMMA +
                    COLUMN_CHECK_IN + TEXT_TYPE + COMMA +
                    COLUMN_CHECK_OUT + TEXT_TYPE + COMMA +
                    COLUMN_ROOMS + TEXT_TYPE + COMMA +
                    COLUMN_ADULT + TEXT_TYPE + COMMA +
                    COLUMN_CHILD + TEXT_TYPE + COMMA +
                    COLUMN_LOCATION + TEXT_TYPE +
                    " )";

    public static final String SQL_DELETE_FAV_ENTRIES =
            "DROP TABLE IF EXISTS " + FAV_TABLE_NAME;


    private static final String FAV_AUTHORITY = "com.mcc.ghurbo.favorites";
    public static final Uri FAV_CONTENT_URI =
            Uri.parse("content://" + FAV_AUTHORITY).buildUpon().
                    appendPath(FAV_TABLE_NAME).build();
    public static final String CONTENT_FAV_ITEM_TYPE =
            ContentResolver.CURSOR_ITEM_BASE_TYPE + "/"
                    + FAV_AUTHORITY + "/" + FAV_TABLE_NAME;

    public static Uri buildFavUri(long id) {
        return ContentUris.withAppendedId(FAV_CONTENT_URI, id);
    }


    public static final String NOTI_TABLE_NAME = "noti_items";
    public static final String COLUMN_BODY = "body";
    public static final String COLUMN_STATUS = "status";

    public static final String SQL_CREATE_NOTI_ENTRIES =
            "CREATE TABLE " + NOTI_TABLE_NAME + " (" +
                    _ID + INTEGER_TYPE + PRIMARY + COMMA +
                    COLUMN_TITLE + TEXT_TYPE + COMMA +
                    COLUMN_BODY + TEXT_TYPE + COMMA +
                    COLUMN_STATUS + TEXT_TYPE + COMMA +
                    COLUMN_IMAGE + TEXT_TYPE +
                    " )";

    public static final String SQL_DELETE_NOTI_ENTRIES =
            "DROP TABLE IF EXISTS " + NOTI_TABLE_NAME;

    private static final String NOTI_AUTHORITY = "com.mcc.ghurbo.notifications";
    public static final Uri NOTI_CONTENT_URI =
            Uri.parse("content://" + NOTI_AUTHORITY).buildUpon().
                    appendPath(NOTI_TABLE_NAME).build();
    public static final String CONTENT_NOTI_ITEM_TYPE =
            ContentResolver.CURSOR_ITEM_BASE_TYPE + "/"
                    + NOTI_AUTHORITY + "/" + NOTI_TABLE_NAME;

    public static Uri buildNotiUri(long id) {
        return ContentUris.withAppendedId(NOTI_CONTENT_URI, id);
    }

}
