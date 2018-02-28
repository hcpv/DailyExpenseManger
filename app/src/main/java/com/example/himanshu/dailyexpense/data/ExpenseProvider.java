package com.example.himanshu.dailyexpense.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import com.example.himanshu.dailyexpense.data.ExpenseContract.ExpenseEntry;

/**
 * Created by himanshu on 4/2/18.
 */

public class ExpenseProvider extends ContentProvider {
    private static final int EXPENSE = 100;

    /** URI matcher code for the content URI for a single pet in the pets table */
    private static final int EXPENSE_ID = 101;
    private static final int DAILY_EXPENSE = 102;

    /** URI matcher code for the content URI for a single pet in the pets table */
    private static final int DAILY_EXPENSE_ID = 103;

    /**
     * UriMatcher object to match a content URI to a corresponding code.
     * The input passed into the constructor represents the code to return for the root URI.
     * It's common to use NO_MATCH as the input for this case.
     */
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    // Static initializer. This is run the first time anything is called from this class.
    static {
        /*
        The calls to addURI() go here, for all of the content URI patterns that the provider
        should recognize. All paths added to the UriMatcher have a corresponding code to return
        when a match is found.
        TODO: Add 2 content URIs to URI matcher
        */
        sUriMatcher.addURI(ExpenseContract.CONTENT_AUTHORITY,ExpenseContract.PATH_EXPENSE,EXPENSE);
        sUriMatcher.addURI(ExpenseContract.CONTENT_AUTHORITY,ExpenseContract.PATH_EXPENSE+"/#",EXPENSE_ID);
        sUriMatcher.addURI(ExpenseContract.CONTENT_AUTHORITY,ExpenseContract.PATH_DAILY_EXPENSE,DAILY_EXPENSE);
        sUriMatcher.addURI(ExpenseContract.CONTENT_AUTHORITY,ExpenseContract.PATH_DAILY_EXPENSE+"/#",DAILY_EXPENSE_ID);

    }
    public ExpenseDbHelper mDbHelper;
    public static final String LOG_TAG = ExpenseProvider.class.getSimpleName();

    /**
     * Initialize the provider and the database helper object.
     */
    @Override
    public boolean onCreate() {
        mDbHelper=new ExpenseDbHelper(getContext());
        return true;
    }

    /**
     * Perform the query for the given URI. Use the given projection, selection, selection arguments, and sort order.
     */
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        // Get readable database
        SQLiteDatabase database = mDbHelper.getReadableDatabase();

        // This cursor will hold the result of the query
        Cursor cursor;

        // Figure out if the URI matcher can match the URI to a specific code
        int match = sUriMatcher.match(uri);
        switch (match) {
            case EXPENSE:
                // For the PETS code, query the pets table directly with the given
                // projection, selection, selection arguments, and sort order. The cursor
                // could contain multiple rows of the pets table.
                // TODO: Perform database query on pets table

                cursor=database.query(ExpenseEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case EXPENSE_ID:
                // For the PET_ID code, extract out the ID from the URI.
                // For an example URI such as "content://com.example.android.pets/pets/3",
                // the selection will be "_id=?" and the selection argument will be a
                // String array containing the actual ID of 3 in this case.
                //
                // For every "?" in the selection, we need to have an element in the selection
                // arguments that will fill in the "?". Since we have 1 question mark in the
                // selection, we have 1 String in the selection arguments' String array.
                selection = ExpenseEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };

                // This will perform a query on the pets table where the _id equals 3 to return a
                // Cursor containing that row of the table.
                cursor = database.query(ExpenseEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case DAILY_EXPENSE:
                // For the PETS code, query the pets table directly with the given
                // projection, selection, selection arguments, and sort order. The cursor
                // could contain multiple rows of the pets table.
                // TODO: Perform database query on pets table

                cursor=database.query(ExpenseEntry.TABLE_NAME_2,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case DAILY_EXPENSE_ID:
                // For the PET_ID code, extract out the ID from the URI.
                // For an example URI such as "content://com.example.android.pets/pets/3",
                // the selection will be "_id=?" and the selection argument will be a
                // String array containing the actual ID of 3 in this case.
                //
                // For every "?" in the selection, we need to have an element in the selection
                // arguments that will fill in the "?". Since we have 1 question mark in the
                // selection, we have 1 String in the selection arguments' String array.
                selection = ExpenseEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };

                // This will perform a query on the pets table where the _id equals 3 to return a
                // Cursor containing that row of the table.
                cursor = database.query(ExpenseEntry.TABLE_NAME_2, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(),uri);
        return cursor;
    }

    /**
     * Insert new data into the provider with the given ContentValues.
     */
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case EXPENSE:
                return insertExpense(uri, contentValues);
            case DAILY_EXPENSE:
                return insertDailyExpense(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    /**
     * Insert a pet into the database with the given content values. Return the new content URI
     * for that specific row in the database.
     */
    private Uri insertDailyExpense(Uri uri, ContentValues values) {

        // TODO: Insert a new pet into the pets database table with the given ContentValues

        // Once we know the ID of the new row in the table,
        // return the new URI with the ID appended to the end of it
        // Check that the name is not null
        String name = values.getAsString(ExpenseEntry.COLUMN_DATE);
        if (name == null) {
            throw new IllegalArgumentException("Entry requires a date");
        }

        // Check that the gender is valid
        Integer day = values.getAsInteger(ExpenseEntry.COLUMN_DAY);
        if (day == null || !ExpenseEntry.isValidDay(day)) {
            throw new IllegalArgumentException("Entry requires valid day");
        }
        String description = values.getAsString(ExpenseEntry.COLUMN_DESCRIPTION);
        if (description == null) {
            throw new IllegalArgumentException("Entry requires a description");
        }

        // Check that the gender is valid
        Integer amount = values.getAsInteger(ExpenseEntry.COLUMN_AMOUNT);
        if (amount == null) {
            throw new IllegalArgumentException("Entry requires amount");
        }

        SQLiteDatabase db=mDbHelper.getWritableDatabase();
        long id=db.insert(ExpenseEntry.TABLE_NAME_2,null,values);
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return ContentUris.withAppendedId(uri, id);
    }
    private Uri insertExpense(Uri uri, ContentValues values) {

        // TODO: Insert a new pet into the pets database table with the given ContentValues

        // Once we know the ID of the new row in the table,
        // return the new URI with the ID appended to the end of it
        // Check that the name is not null
        String name = values.getAsString(ExpenseEntry.COLUMN_DATE);
        if (name == null) {
            throw new IllegalArgumentException("Entry requires a date");
        }

        // Check that the gender is valid
        Integer day = values.getAsInteger(ExpenseEntry.COLUMN_DAY);
        if (day == null || !ExpenseEntry.isValidDay(day)) {
            throw new IllegalArgumentException("Entry requires valid day");
        }

        SQLiteDatabase db=mDbHelper.getWritableDatabase();
        long id=db.insert(ExpenseEntry.TABLE_NAME,null,values);
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return ContentUris.withAppendedId(uri, id);
    }
    /**
     * Updates the data at the given selection and selection arguments, with the new ContentValues.
     */

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection,
                      String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case EXPENSE:
                return updateExpense(uri, contentValues, selection, selectionArgs);
            case EXPENSE_ID:
                // For the PET_ID code, extract out the ID from the URI,
                // so we know which row to update. Selection will be "_id=?" and selection
                // arguments will be a String array containing the actual ID.
                selection = ExpenseEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                return updateExpense(uri, contentValues, selection, selectionArgs);
            case DAILY_EXPENSE:
                return updateDailyExpense(uri, contentValues, selection, selectionArgs);
            case DAILY_EXPENSE_ID:
                // For the PET_ID code, extract out the ID from the URI,
                // so we know which row to update. Selection will be "_id=?" and selection
                // arguments will be a String array containing the actual ID.
                selection = ExpenseEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                return updateDailyExpense(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    /**
     * Update pets in the database with the given content values. Apply the changes to the rows
     * specified in the selection and selection arguments (which could be 0 or 1 or more pets).
     * Return the number of rows that were successfully updated.
     */
    private int updateExpense(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        // check that the name value is not null.containsKey is used to check whether given column is to updated
        if (values.containsKey(ExpenseEntry.COLUMN_DATE)) {
            String name = values.getAsString(ExpenseEntry.COLUMN_DATE);
            if (name == null) {
                throw new IllegalArgumentException("Entry requires a Date");
            }
        }

        // If the {@link PetEntry#COLUMN_PET_GENDER} key is present,
        // check that the gender value is valid.
        if (values.containsKey(ExpenseEntry.COLUMN_DAY)) {
            Integer day = values.getAsInteger(ExpenseEntry.COLUMN_DAY);
            if (day == null || !ExpenseEntry.isValidDay(day)) {
                throw new IllegalArgumentException("Entry requires valid day");
            }
        }


        // If there are no values to update, then don't try to update the database
        if (values.size() == 0) {
            return 0;
        }


        SQLiteDatabase db=mDbHelper.getWritableDatabase();
        int n=db.update(ExpenseEntry.TABLE_NAME,values,selection,selectionArgs);
        // If 1 or more rows were updated, then notify all listeners that the data at the
        // given URI has changed
        if (n != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return n;
    }
    private int updateDailyExpense(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        // check that the name value is not null.containsKey is used to check whether given column is to updated
        if (values.containsKey(ExpenseEntry.COLUMN_DESCRIPTION)) {
            String description = values.getAsString(ExpenseEntry.COLUMN_DESCRIPTION);
            if (description == null) {
                throw new IllegalArgumentException("Entry requires a Description");
            }
        }

        // If the {@link PetEntry#COLUMN_PET_GENDER} key is present,
        // check that the gender value is valid.
        if (values.containsKey(ExpenseEntry.COLUMN_AMOUNT)) {
            Integer amount= values.getAsInteger(ExpenseEntry.COLUMN_AMOUNT);
            if (amount== null) {
                throw new IllegalArgumentException("Entry requires amount");
            }
        }


        // If there are no values to update, then don't try to update the database
        if (values.size() == 0) {
            return 0;
        }


        SQLiteDatabase db=mDbHelper.getWritableDatabase();
        int n=db.update(ExpenseEntry.TABLE_NAME_2,values,selection,selectionArgs);
        // If 1 or more rows were updated, then notify all listeners that the data at the
        // given URI has changed
        if (n != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return n;
    }

    /**
     * Delete the data at the given selection and selection arguments.
     */
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Get writeable database
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        final int match = sUriMatcher.match(uri);
        int rowsDeleted;
        switch (match) {
            case EXPENSE:
                // Delete all rows that match the selection and selection args
                rowsDeleted=database.delete(ExpenseEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case EXPENSE_ID:
                // Delete a single row given by the ID in the URI
                selection = ExpenseEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted=database.delete(ExpenseEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case DAILY_EXPENSE:
                // Delete all rows that match the selection and selection args
                rowsDeleted=database.delete(ExpenseEntry.TABLE_NAME_2, selection, selectionArgs);
                break;
            case DAILY_EXPENSE_ID:
                // Delete a single row given by the ID in the URI
                selection = ExpenseEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted=database.delete(ExpenseEntry.TABLE_NAME_2, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }
        // If 1 or more rows were deleted, then notify all listeners that the data at the
        // given URI has changed
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);}
        return rowsDeleted;
    }
    /**
     * Returns the MIME type of data for the content URI.
     */
    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case EXPENSE:
                return ExpenseEntry.CONTENT_LIST_TYPE;
            case EXPENSE_ID:
                return ExpenseEntry.CONTENT_ITEM_TYPE;
            case DAILY_EXPENSE:
                return ExpenseEntry.CONTENT_LIST_TYPE_2;
            case DAILY_EXPENSE_ID:
                return ExpenseEntry.CONTENT_ITEM_TYPE_2;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }
}
