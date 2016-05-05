package com.liu.dave.sqlite;

import android.provider.BaseColumns;

/**
 * Created by LiuDong on 2016/5/4.
 */
public final class FeedReaderContract {

    public FeedReaderContract() {
    }

    public static abstract class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "entry";
        public static final String COLUMN_NAME_ENTRY_ID = "entryid";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_SUBTITLE = "subtitle";
        public static final String COLUMN_NAME_NULLABLE = "empty";
        public static final String COLUMN_NAME_UPDATED = "updated";

    }
}
