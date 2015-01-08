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
import database.HisnDatabaseInfo;


public class DuaDetailActivity extends ActionBarActivity {
    private String duaIdFromDuaListActivity;
    private String duaTitleFromDuaListActivity;

    private SQLiteDatabase database;
    private ArrayList<Dua> duaDetails = new ArrayList<Dua>();

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

        ExternalDbOpenHelper dbOpenHelper = ExternalDbOpenHelper.getInstance(this);
        database = dbOpenHelper.openDataBase();

        fromDBtoArrayList();
        fromArrayListToListView();
        //setFloatingActionButton();

        this.setTitle(duaTitleFromDuaListActivity);
    }

    public void fromDBtoArrayList(){
        Cursor duaDetailCursor = null;

        try {
            duaDetailCursor = database.query(HisnDatabaseInfo.DuaTable.TABLE_NAME,
                    new String[]{HisnDatabaseInfo.DuaTable._ID,
                            HisnDatabaseInfo.DuaTable.ARABIC_DUA,
                            HisnDatabaseInfo.DuaTable.ENGLISH_TRANSLATION,
                            HisnDatabaseInfo.DuaTable.ENGLISH_REFERENCE},
                    HisnDatabaseInfo.DuaTable.GROUP_ID + "=" + duaIdFromDuaListActivity,
                    null, null, null, null);
            if (duaDetailCursor != null && duaDetailCursor.moveToFirst()) {
                do {
                    int reference = Integer.parseInt(duaDetailCursor.getString(0));
                    String arabic = duaDetailCursor.getString(1);
                    String translation = duaDetailCursor.getString(2);
                    String book_reference = duaDetailCursor.getString(3);
                    duaDetails.add(new Dua(reference, arabic, translation, book_reference));
                } while (duaDetailCursor.moveToNext());
            }
        } finally {
            if (duaDetailCursor != null) {
                duaDetailCursor.close();
            }
        }
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