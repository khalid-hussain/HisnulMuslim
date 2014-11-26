package com.example.khalid.hisnulmuslim;

import android.app.ActionBar;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.nhaarman.listviewanimations.swinginadapters.prepared.SwingBottomInAnimationAdapter;

import java.util.ArrayList;

import adapters.Dua;
import adapters.DuaDetailAdapter;
import database.ExternalDbOpenHelper;


public class DuaDetail extends ActionBarActivity {
    private static final String DB_NAME = "hisnul.sqlite3";

    //A good practice is to define database field names as constants
    private static final String TABLE_NAME = "dua";
    private static final String GRP_ID = "grp_id";

    private static final String DUA_NUMBER = "dua_id";
    private static final String DUA_ARABIC = "ar_dua";
    private static final String DUA_TRANSLATION = "en_dua";
    private static final String DUA_REFERENCE = "en_ref";

    private String title;
    private int reference;
    private String arabic;
    private String translation;
    private String book_reference;

    private String dua_from_list_fragment;
    private String dua_title_from_list_fragment;

    private SQLiteDatabase database;
    private ArrayList duaDetails;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dua_detail);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        // 1. get passed intent
        Intent intent = getIntent();

        // 2. get bundle from intent
        Bundle bundle = intent.getExtras();

        // 3. get status value from bundle
        dua_from_list_fragment = bundle.getString("dua_id");
        dua_title_from_list_fragment = bundle.getString("dua_title");

        //Our key helper
        ExternalDbOpenHelper dbOpenHelper = new ExternalDbOpenHelper(this, DB_NAME);
        database = dbOpenHelper.openDataBase();

        //That’s it, the database is open!
        fillDua();

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        //actionBar.setDisplayShowTitleEnabled(false);

        this.setTitle(dua_title_from_list_fragment);

        setUpDuaList();
    }

    public void fillDua(){
        //Extracting Elements from Database
        duaDetails = new ArrayList<Dua>();
        Cursor friendCursor = database.query(TABLE_NAME, new String[]
                        {DUA_NUMBER, DUA_ARABIC, DUA_TRANSLATION,DUA_REFERENCE},
                        GRP_ID + "=" + dua_from_list_fragment,
                        null, null, null, null);
        friendCursor.moveToFirst();
        if(!friendCursor.isAfterLast()) {
            do {
                reference = Integer.parseInt(friendCursor.getString(0));
                arabic = friendCursor.getString(1);
                translation = friendCursor.getString(2);
                book_reference = friendCursor.getString(3);
                duaDetails.add(new Dua(reference, arabic, translation, book_reference));
                //duaDetails.add(new Dua(title, arabic, translation, book_reference));
            } while (friendCursor.moveToNext());
        }
        friendCursor.close();
    }

    public void setUpDuaList(){
        final ListView listView;
        listView = (ListView) findViewById(R.id.duaDetailView);

        DuaDetailAdapter customAdapter;
        customAdapter = new DuaDetailAdapter(this, duaDetails);

        SwingBottomInAnimationAdapter swingBottomInAnimationAdapter =
                new SwingBottomInAnimationAdapter(customAdapter);

        // Assign the ListView to the AnimationAdapter and vice versa
        swingBottomInAnimationAdapter.setAbsListView(listView);
        listView.setAdapter(swingBottomInAnimationAdapter);

        // Disable clicking on dua items.
        listView.setClickable(false);
        //listView.setOnClickListener(null);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dua_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            /*case android.R.id.home:
                return true;*/
        }
        return super.onOptionsItemSelected(item);
    }
}