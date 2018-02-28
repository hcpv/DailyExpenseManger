package com.example.himanshu.dailyexpense;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import com.example.himanshu.dailyexpense.data.ExpenseContract.ExpenseEntry;

public class CatalogAcitvity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int EXPENSE_LOADER=0;
    ExpenseCursorAdapter mCursorAdaptor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog_acitvity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatalogAcitvity.this,EditorActivity.class);
                startActivity(intent);
            }
        });
        final ListView expenseListView = (ListView) findViewById(R.id.list);
        mCursorAdaptor=new ExpenseCursorAdapter(this,null);
        expenseListView.setAdapter(mCursorAdaptor);
        expenseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Uri uri= ContentUris.withAppendedId(ExpenseEntry.CONTENT_URI,id);
                Intent i=new Intent(CatalogAcitvity.this,DailyExpense.class);
                i.setData(uri);
                startActivity(i);
            }
        });
        expenseListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                Uri uri= ContentUris.withAppendedId(ExpenseEntry.CONTENT_URI,id);
                Intent i=new Intent(CatalogAcitvity.this,EditorActivity.class);
                i.setData(uri);
                startActivity(i);
                return true;
            }
        });
        getLoaderManager().initLoader(EXPENSE_LOADER,null,this);
    }

    private void insertExpense()
    {
        ContentValues values=new ContentValues();
        values.put(ExpenseEntry.COLUMN_DATE,"01-01-2018");
        values.put(ExpenseEntry.COLUMN_DAY,1);
        Uri newUri=getContentResolver().insert(ExpenseEntry.CONTENT_URI,values);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_catalog_acitvity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_insert_dummy_data) {
            insertExpense();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String []projection={ExpenseEntry._ID,ExpenseEntry.COLUMN_DATE,ExpenseEntry.COLUMN_DAY};

        return  new CursorLoader(this,ExpenseEntry.CONTENT_URI,projection,null,null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        mCursorAdaptor.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCursorAdaptor.swapCursor(null);
    }
}
