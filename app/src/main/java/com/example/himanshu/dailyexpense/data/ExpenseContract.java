package com.example.himanshu.dailyexpense.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by himanshu on 3/2/18.
 */

public final class ExpenseContract {
    public static final String CONTENT_AUTHORITY = "com.example.himanshu.dailyexpense";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_EXPENSE = "expense";
    public static final String PATH_DAILY_EXPENSE="DailyExpense";
    public static abstract class ExpenseEntry implements BaseColumns{
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_EXPENSE);
        public static final Uri CONTENT_URI_2 = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_DAILY_EXPENSE);

        /**
         * The MIME type of the {@link #CONTENT_URI} for a list of pets.
         */
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_EXPENSE;

        public static final String CONTENT_LIST_TYPE_2 =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_DAILY_EXPENSE;

        /**
         * The MIME type of the {@link #CONTENT_URI} for a single pet.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_EXPENSE;
        public static final String CONTENT_ITEM_TYPE_2 =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_DAILY_EXPENSE;
        public static final String TABLE_NAME="Expense";
        public static final String _ID=BaseColumns._ID;
        public static final String COLUMN_DATE="Date";
        public static final String COLUMN_DAY="Day";

        public static final String TABLE_NAME_2="DailyExpense";
        public static final String COLUMN_DESCRIPTION="Description";
        public static final String COLUMN_AMOUNT="Amount";


        public static final int DAY_UNKNOWN=0;
        public static final int DAY_MONDAY=1;
        public static final int DAY_TUESDAY=2;
        public static final int DAY_WEDNESDAY=3;
        public static final int DAY_THURSDAY=4;
        public static final int DAY_FRIDAY=5;
        public static final int DAY_SATURDAY=6;
        public static final int DAY_SUNDAY=7;

        public static boolean isValidDay(int day) {
            if (day == DAY_MONDAY || day == DAY_TUESDAY || day == DAY_WEDNESDAY || day== DAY_THURSDAY || day== DAY_FRIDAY || day== DAY_SATURDAY || day== DAY_SUNDAY) {
                return true;
            }
            return false;
        }
    }
}
