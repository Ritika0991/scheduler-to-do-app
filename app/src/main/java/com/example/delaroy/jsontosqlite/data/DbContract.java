package com.example.delaroy.jsontosqlite.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by delaroy on 7/22/17.
 */

public class DbContract {

    public static final String CONTENT_AUTHORITY = "com.example.delaroy.jsontosqlite";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    private static final String PATH_ENTRIES = "entries";

    public static class MenuEntry implements BaseColumns {

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.jsontosqlite.entries";

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd.jsontosqlite.entry";

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_ENTRIES).build();

        public static final String TABLE1 = "tasks";
        public static final String TABLE2 = "subtasks";
        public static final String TASK_ID = "task_id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_SCHEDULED = "scheduled";


    }
}
