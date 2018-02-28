package com.example.himanshu.dailyexpense;

import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.himanshu.dailyexpense.data.ExpenseContract;

/**
 * Created by himanshu on 4/2/18.
 */

public class ExpenseCursorAdapter extends CursorAdapter{
    /**
     * Constructs a new {@link ExpenseCursorAdapter}.
     *
     * @param context The context
     * @param c       The cursor from which to get the data.
     */
    public ExpenseCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }

    /**
     * Makes a new blank list item view. No data is set (or bound) to the views yet.
     *
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already
     *                moved to the correct position.
     * @param parent  The parent to which the new view is attached to
     * @return the newly created list item view.
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return mInflater.inflate(R.layout.list_item,parent,false);
    }

    /**
     * This method binds the pet data (in the current row pointed to by cursor) to the given
     * list item layout. For example, the name for the current pet can be set on the name TextView
     * in the list item layout.
     *
     * @param view    Existing view, returned earlier by newView() method
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already moved to the
     *                correct row.
     */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView dateTextView=(TextView)view.findViewById(R.id.date);
        TextView dayTextView=(TextView)view.findViewById(R.id.day);
        String date=cursor.getString(cursor.getColumnIndex(ExpenseContract.ExpenseEntry.COLUMN_DATE));
        int day=cursor.getInt(cursor.getColumnIndex(ExpenseContract.ExpenseEntry.COLUMN_DAY));
        String day_name=null;
        switch (day) {
            case ExpenseContract.ExpenseEntry.DAY_MONDAY:
                day_name="Monday";
                break;
            case ExpenseContract.ExpenseEntry.DAY_TUESDAY:
                day_name="Tuesday";
                break;
            case ExpenseContract.ExpenseEntry.DAY_WEDNESDAY:
                day_name="Wednesday";
                break;
            case ExpenseContract.ExpenseEntry.DAY_THURSDAY:
                day_name="Thursday";
                break;
            case ExpenseContract.ExpenseEntry.DAY_FRIDAY:
                day_name="Friday";
                break;
            case ExpenseContract.ExpenseEntry.DAY_SATURDAY:
                day_name="Saturday";
                break;
            case ExpenseContract.ExpenseEntry.DAY_SUNDAY:
                day_name="Sunday";
                break;
        }
        dateTextView.setText(date);
        dayTextView.setText(day_name);

    }
}
