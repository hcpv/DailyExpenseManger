package com.example.himanshu.dailyexpense;

import android.app.DatePickerDialog;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import com.example.himanshu.dailyexpense.data.ExpenseContract.ExpenseEntry;

public class EditorActivity extends AppCompatActivity {
    private EditText mDateEditText;

    private DatePickerDialog DatePickerDialog;

    private SimpleDateFormat dateFormatter;

    private Spinner mDaySpinner;
    private int mDay=0;



    private boolean mExpenseHasChanged = false;

    private Uri uri;
    // OnTouchListener that listens for any user touches on a View, implying that they are modifying
// the view, and we change the mPetHasChanged boolean to true.

    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            mExpenseHasChanged = true;
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        mDaySpinner=(Spinner)findViewById(R.id.spinner_day);
        mDateEditText=(EditText) findViewById(R.id.Birthday);
        mDateEditText.setInputType(InputType.TYPE_NULL);
        mDateEditText.requestFocus();
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        setupSpinner();
        mDaySpinner.setOnTouchListener(mTouchListener);


        setDateTimeField();
        Intent intent=getIntent();
        uri=intent.getData();
        if(uri==null) {
            setTitle("Add a new entry");
            // Invalidate the options menu, so the "Delete" menu option can be hidden.
            // (It doesn't make sense to delete a pet that hasn't been created yet.)
            invalidateOptionsMenu();
        }
        else {
            setTitle("Edit the entry");
            String[] projection = {ExpenseEntry._ID, ExpenseEntry.COLUMN_DATE, ExpenseEntry.COLUMN_DAY};
            Cursor cursor = getContentResolver().query(uri,projection,null,null,null);
            if(cursor.moveToFirst())
            {
                int dateColumnIndex=cursor.getColumnIndex(ExpenseEntry.COLUMN_DATE);
                int dayColumnIndex=cursor.getColumnIndex(ExpenseEntry.COLUMN_DAY);
                String date=cursor.getString(dateColumnIndex);
                int day=cursor.getInt(dayColumnIndex);
                mDateEditText.setText(date);
                switch (day) {
                    case ExpenseEntry.DAY_MONDAY:
                        mDaySpinner.setSelection(1);
                        break;
                    case ExpenseEntry.DAY_TUESDAY:
                        mDaySpinner.setSelection(2);
                        break;
                    case ExpenseEntry.DAY_WEDNESDAY:
                        mDaySpinner.setSelection(3);
                        break;
                    case ExpenseEntry.DAY_THURSDAY:
                        mDaySpinner.setSelection(4);
                        break;
                    case ExpenseEntry.DAY_FRIDAY:
                        mDaySpinner.setSelection(5);
                        break;
                    case ExpenseEntry.DAY_SATURDAY:
                        mDaySpinner.setSelection(6);
                        break;
                    case ExpenseEntry.DAY_SUNDAY:
                        mDaySpinner.setSelection(7);
                        break;
                }

            }

        }

    }
    private void showUnsavedChangesDialog(
            DialogInterface.OnClickListener discardButtonClickListener) {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the positive and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.unsaved_changes_dialog_msg);
        builder.setPositiveButton(R.string.discard, discardButtonClickListener);
        builder.setNegativeButton(R.string.keep_editing, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Keep editing" button, so dismiss the dialog
                // and continue editing the pet.
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        // If the pet hasn't changed, continue with handling back button press
        if (!mExpenseHasChanged) {
            super.onBackPressed();
            return;
        }

        // Otherwise if there are unsaved changes, setup a dialog to warn the user.
        // Create a click listener to handle the user confirming that changes should be discarded.
        DialogInterface.OnClickListener discardButtonClickListener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // User clicked "Discard" button, close the current activity.
                        finish();
                    }
                };

        // Show dialog that there are unsaved changes
        showUnsavedChangesDialog(discardButtonClickListener);
    }
    private void setDateTimeField() {
        mDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view == mDateEditText) {
                    DatePickerDialog.show();
                }
            }
        });
        Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog = new DatePickerDialog(this, new android.app.DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                mDateEditText.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));


    }
    private void setupSpinner() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter genderSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_day_options, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        genderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        mDaySpinner.setAdapter(genderSpinnerAdapter);

        // Set the integer mSelected to the constant values
        mDaySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.day_monday))) {
                        mDay = ExpenseEntry.DAY_MONDAY;
                    } else if (selection.equals(getString(R.string.day_tuesday))) {
                        mDay = ExpenseEntry.DAY_TUESDAY;}
                    else if (selection.equals(getString(R.string.day_wednesday))) {
                        mDay = ExpenseEntry.DAY_WEDNESDAY;}
                    else if (selection.equals(getString(R.string.day_thursday))) {
                        mDay = ExpenseEntry.DAY_THURSDAY;}
                    else if (selection.equals(getString(R.string.day_friday))) {
                        mDay = ExpenseEntry.DAY_FRIDAY;}
                    else if (selection.equals(getString(R.string.day_saturday))) {
                        mDay = ExpenseEntry.DAY_SATURDAY;}
                    else if (selection.equals(getString(R.string.day_sunday))) {
                        mDay = ExpenseEntry.DAY_SUNDAY;}
                    else {
                        mDay = ExpenseEntry.DAY_UNKNOWN; // Unknown
                    }
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mDay = 0; // Unknown
            }
        });
    }
    private void saveDate()
    {
        String dateString=mDateEditText.getText().toString().trim();
        ContentValues values=new ContentValues();
        if (TextUtils.isEmpty(dateString) ) {return;}
        values.put(ExpenseEntry.COLUMN_DATE,dateString);
        int w = 0;
        values.put(ExpenseEntry.COLUMN_DAY,mDay);
        if(uri==null) {

            Uri newUri = getContentResolver().insert(ExpenseEntry.CONTENT_URI, values);
            if (newUri == null) {
                // If the new content URI is null, then there was an error with insertion.
                Toast.makeText(this, getString(R.string.editor_insert_pet_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the insertion was successful and we can display a toast.
                Toast.makeText(this, getString(R.string.editor_insert_pet_successful),
                        Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            int newUri = getContentResolver().update(uri, values,null,null);
            if (newUri == 0) {
                // If the new content URI is null, then there was an error with insertion.
                Toast.makeText(this, getString(R.string.editor_insert_pet_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the insertion was successful and we can display a toast.
                Toast.makeText(this, getString(R.string.editor_insert_pet_successful),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void showDeleteConfirmationDialog() {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the postivie and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_dialog_msg);
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Delete" button, so delete the pet.
                deleteExpense();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Cancel" button, so dismiss the dialog
                // and continue editing the pet.
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    private void deleteExpense() {
        // TODO: Implement this method
        if(uri!=null) {
            int n = getContentResolver().delete(uri, null, null);
            if (n == 0)
                Toast.makeText(this, R.string.editor_delete_pet_failed, Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, R.string.editor_delete_pet_successful, Toast.LENGTH_SHORT).show();
        }
        finish();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                saveDate();
                finish();
                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.action_delete:
                // Do nothing for now
                // Show a dialog that notifies the user they have unsaved changes
                showDeleteConfirmationDialog();
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // If the pet hasn't changed, continue with navigating up to parent activity
                // which is the {@link CatalogActivity}.
                if (!mExpenseHasChanged) {
                    NavUtils.navigateUpFromSameTask(EditorActivity.this);
                    return true;
                }

                // Otherwise if there are unsaved changes, setup a dialog to warn the user.
                // Create a click listener to handle the user confirming that
                // changes should be discarded.
                DialogInterface.OnClickListener discardButtonClickListener =
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // User clicked "Discard" button, navigate to parent activity.
                                NavUtils.navigateUpFromSameTask(EditorActivity.this);
                            }
                        };

                // Show a dialog that notifies the user they have unsaved changes
                showUnsavedChangesDialog(discardButtonClickListener);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        // If this is a new pet, hide the "Delete" menu item.
        if (uri == null) {
            MenuItem menuItem = menu.findItem(R.id.action_delete);
            menuItem.setVisible(false);
        }
        return true;
    }
}
