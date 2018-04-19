package com.mcc.ghurbo.data.sqlite;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class DbConstants implements BaseColumns {

    private static final String CONTENT_AUTHORITY = "com.mcc.ghurbo.favorites";
    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

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

    public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
            .appendPath(FAV_TABLE_NAME).build();
    public static final String CONTENT_ITEM_TYPE =
            ContentResolver.CURSOR_ITEM_BASE_TYPE +"/" + CONTENT_AUTHORITY + "/" + FAV_TABLE_NAME;

    public static Uri buildFavUri(long id){
        return ContentUris.withAppendedId(CONTENT_URI, id);
    }

}
