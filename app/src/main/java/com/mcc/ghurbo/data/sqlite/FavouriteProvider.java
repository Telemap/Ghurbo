package com.mcc.ghurbo.data.sqlite;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class FavouriteProvider extends ContentProvider {

    private SQLiteDatabase db;

    @Override
    public boolean onCreate() {
        db = DbHelper.getInstance(getContext()).getWritableDatabase();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        return db.query(
                DbConstants.FAV_TABLE_NAME,  // The table name to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return DbConstants.CONTENT_ITEM_TYPE;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        Uri returnUri;
        long _id = db.insert(
                DbConstants.FAV_TABLE_NAME,
                DbConstants.COLUMN_NAME_NULLABLE,
                contentValues);

        if (_id > 0) {
            returnUri = DbConstants.buildFavUri(_id);
        } else {
            throw new android.database.SQLException("Failed to insert row into: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {

        db.beginTransaction();

        // keep track of successful inserts
        int numInserted = 0;
        try {
            for (ContentValues value : values) {
                if (value == null) {
                    throw new IllegalArgumentException("Cannot have null content values");
                }
                long id = -1;
                try {
                    id = db.insertOrThrow(DbConstants.FAV_TABLE_NAME,
                            null, value);
                } catch (SQLiteConstraintException e) {
                    e.printStackTrace();
                }
                if (id != -1) {
                    numInserted++;
                }
            }
            if (numInserted > 0) {
                db.setTransactionSuccessful();
            }
        } finally {
            db.endTransaction();
        }
        if (numInserted > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return numInserted;

    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return db.delete(
                DbConstants.FAV_TABLE_NAME,
                selection,
                selectionArgs);
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String selection, @Nullable String[] selectionArgs) {
        int numUpdated;

        if (contentValues == null) {
            throw new IllegalArgumentException("Cannot have null content values");
        }
        numUpdated = db.update(DbConstants.FAV_TABLE_NAME,
                contentValues,
                selection,
                selectionArgs);

        if (numUpdated > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return numUpdated;
    }
}
