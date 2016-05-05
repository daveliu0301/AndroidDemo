package com.liu.dave.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.liu.dave.sqlite.FeedReaderContract.FeedEntry;

/**
 * Created by LiuDong on 2016/5/4.
 */
public class FeedReaderDbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "FeedReader.db";
    private static final int DB_VERSION = 1;
    private static final String TYPE_TEXT = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_TABLE = "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
            FeedEntry._ID + " INTEGER PRIMARY KEY," +
            FeedEntry.COLUMN_NAME_ENTRY_ID + TYPE_TEXT + COMMA_SEP +
            FeedEntry.COLUMN_NAME_SUBTITLE + TYPE_TEXT + COMMA_SEP +
            FeedEntry.COLUMN_NAME_TITLE + TYPE_TEXT + COMMA_SEP +
            " )";
    private static final String SQL_DELETE_TABLE = "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;


    public FeedReaderDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_TABLE);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public static long insert(Context context, String entryid, String subtitle, String title) {
        FeedReaderDbHelper helper = new FeedReaderDbHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FeedEntry.COLUMN_NAME_ENTRY_ID, entryid);
        values.put(FeedEntry.COLUMN_NAME_SUBTITLE, subtitle);
        values.put(FeedEntry.COLUMN_NAME_TITLE, title);

//        return db.insert(FeedEntry.TABLE_NAME, FeedEntry.COLUMN_NAME_NULLABLE, values);
        return db.insert(FeedEntry.TABLE_NAME, null, values);
    }

    //example:
    // Cursor cursor = FeedReaderDbHelper.query(...);
//    cursor.moveToFirst();
//    long itemId = cursor.getLong(
//            cursor.getColumnIndexOrThrow(FeedEntry._ID)
//    );
    public static Cursor query(Context context) {
        FeedReaderDbHelper helper = new FeedReaderDbHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
// Define a projection that specifies which columns from the database
// you will actually use after this query.
        String[] projection = {
                FeedEntry.COLUMN_NAME_SUBTITLE,
                FeedEntry.COLUMN_NAME_ENTRY_ID,
                FeedEntry.COLUMN_NAME_TITLE
        };

// How you want the results sorted in the resulting Cursor
        String sortOrder = FeedEntry.COLUMN_NAME_UPDATED + " DESC";

        return db.query(FeedEntry.TABLE_NAME, projection, null, null, null, null, sortOrder);
    }

    public static int delete(Context context, long rowId) {
        FeedReaderDbHelper helper = new FeedReaderDbHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();

        // Define 'where' part of query.
        String selection = FeedEntry.COLUMN_NAME_ENTRY_ID + " LIKE ?";
// Specify arguments in placeholder order.
        String[] selectionArgs = {String.valueOf(rowId)};
// Issue SQL statement.
        return db.delete(FeedEntry.TABLE_NAME, selection, selectionArgs);
    }

    public static int update(Context context, String title, long rowId) {
        FeedReaderDbHelper helper = new FeedReaderDbHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FeedEntry.COLUMN_NAME_TITLE, title);

        String selection = FeedEntry.COLUMN_NAME_ENTRY_ID + " LIKE ?";
        String[] selectionArgs = {String.valueOf(rowId)};
        return db.update(FeedEntry.TABLE_NAME, values, selection, selectionArgs);

    }

}
