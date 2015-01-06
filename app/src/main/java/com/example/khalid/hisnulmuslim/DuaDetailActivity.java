package com.example.khalid.hisnulmuslim;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

import adapters.DuaDetailAdapter;
import classes.Dua;
import database.ExternalDbOpenHelper;
import database.mySqliteDatabase;


public class DuaDetailActivity extends ActionBarActivity {
    private static final String DB_NAME = "hisnul.sqlite3";

    private mySqliteDatabase myDB = new mySqliteDatabase();

    private String duaIdFromDuaListActivity;
    private String duaTitleFromDuaListActivity;

    private SQLiteDatabase database;
    private ArrayList duaDetails = new ArrayList<Dua>();

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dua_detail);
        this.listView = (ListView) findViewById(R.id.duaDetailListView);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        duaIdFromDuaListActivity = bundle.getString("dua_id");
        duaTitleFromDuaListActivity = bundle.getString("dua_title");

        ExternalDbOpenHelper dbOpenHelper = new ExternalDbOpenHelper(this, DB_NAME);
        database = dbOpenHelper.openDataBase();

        fromDBtoArrayList();
        fromArrayListToListView();
        //setFloatingActionButton();

        this.setTitle(duaTitleFromDuaListActivity);
    }

    public void fromDBtoArrayList(){
        Cursor duaDetailCursor = database.query(myDB.TABLE_DUA,
                              new String[] {myDB.TABLE_DUA_ID,
                                            myDB.TABLE_DUA_AR,
                                            myDB.TABLE_DUA_EN_TRANS,
                                            myDB.TABLE_DUA_EN_REFERENCE},
                              myDB.TABLE_DUA_FK_GROUP_ID + "=" + duaIdFromDuaListActivity,
                              null,
                              null,
                              null,
                              null);

        duaDetailCursor.moveToFirst();

        if(!duaDetailCursor.isAfterLast()) {
            do {
                int reference = Integer.parseInt(duaDetailCursor.getString(0));
                String arabic = duaDetailCursor.getString(1);
                String translation = duaDetailCursor.getString(2);
                String book_reference = duaDetailCursor.getString(3);
                duaDetails.add(new Dua(reference, arabic, translation, book_reference));
            } while (duaDetailCursor.moveToNext());
        }
        duaDetailCursor.close();
    }

    public void fromArrayListToListView() {
        DuaDetailAdapter mAdapter;
        mAdapter = new DuaDetailAdapter(this, R.layout.dua_detail_item_card, duaDetails);
        this.listView.setAdapter(mAdapter);
    }

    /*public void setFloatingActionButton() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.initBackground(); // Use after setting new values
        fab.setImageResource(R.drawable.ic_action_favorite);// Standard imageView method
        this.listView.setOnTouchListener(new ShowHideOnScroll(fab));
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dua_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (item.getItemId()) {
            /*case android.R.id.home:
                return true;*/
        }
        return super.onOptionsItemSelected(item);
    }
}