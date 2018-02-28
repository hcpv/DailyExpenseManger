package com.example.himanshu.dailyexpense;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.himanshu.dailyexpense.data.ExpenseContract;

public class NewExpense extends AppCompatActivity {
    private EditText mDescriptionText;
    private EditText mAmount;
    private int mCategory;
    private Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_expense);
        mDescriptionText=(EditText)findViewById(R.id.edit_description);
        mAmount=(EditText)findViewById(R.id.edit_amount);
        Intent i=getIntent();
        uri=i.getData();
    }
    private void saveExpense()
    {
        String descriptionString=mDescriptionText.getText().toString().trim();
        ContentValues values=new ContentValues();
        if (TextUtils.isEmpty(descriptionString) ) {return;}
        values.put(ExpenseContract.ExpenseEntry.COLUMN_DESCRIPTION,descriptionString);
        int amount=Integer.parseInt(mAmount.getText().toString().trim());
        values.put(ExpenseContract.ExpenseEntry.COLUMN_DAY,amount);
        String []projection={ExpenseContract.ExpenseEntry._ID, ExpenseContract.ExpenseEntry.COLUMN_DATE, ExpenseContract.ExpenseEntry.COLUMN_DAY};
        Cursor cursor=getContentResolver().query(uri,projection,null,null,null);
        String date=cursor.getString(cursor.getColumnIndex(ExpenseContract.ExpenseEntry.COLUMN_DATE));
        values.put(ExpenseContract.ExpenseEntry.COLUMN_DATE,date);
        String day=cursor.getString(cursor.getColumnIndex(ExpenseContract.ExpenseEntry.COLUMN_DAY));
        values.put(ExpenseContract.ExpenseEntry.COLUMN_DATE,day);
        //if(uri==null) {

            Uri newUri = getContentResolver().insert(ExpenseContract.ExpenseEntry.CONTENT_URI_2, values);
            if (newUri == null) {
                // If the new content URI is null, then there was an error with insertion.
                Toast.makeText(this, getString(R.string.editor_insert_pet_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the insertion was successful and we can display a toast.
                Toast.makeText(this, getString(R.string.editor_insert_pet_successful),
                        Toast.LENGTH_SHORT).show();
            }
       // }
        /*else
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
        }*/
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_new_expense, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                saveExpense();
                finish();
                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.action_delete:
                // Do nothing for now
                // Show a dialog that notifies the user they have unsaved changes
                //showDeleteConfirmationDialog();
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // If the pet hasn't changed, continue with navigating up to parent activity
                // which is the {@link CatalogActivity}.
               /* if (!mExpenseHasChanged) {
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
                showUnsavedChangesDialog(discardButtonClickListener);*/
                return true;

        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        /*// If this is a new pet, hide the "Delete" menu item.
        if (uri == null) {
            MenuItem menuItem = menu.findItem(R.id.action_delete_2);
            menuItem.setVisible(false);
        }
        return true;*/
        return true;
    }

}
