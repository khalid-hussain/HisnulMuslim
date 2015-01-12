package com.example.khalid.hisnulmuslim;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import adapters.DuaDetailAdapter;
import classes.Dua;
import database.ExternalDbOpenHelper;
import database.HisnDatabaseInfo;
import loader.DuaDetailsLoader;


public class DuaDetailActivity extends ActionBarActivity
        implements LoaderManager.LoaderCallbacks<List<Dua>> {
    private int duaIdFromDuaListActivity;
    private DuaDetailAdapter adapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dua_detail);
        this.listView = (ListView) findViewById(R.id.duaDetailListView);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        duaIdFromDuaListActivity = bundle.getInt("dua_id");
        setTitle(bundle.getString("dua_title"));
        getSupportLoaderManager().initLoader(0, null, this);
        //setFloatingActionButton();
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

    @Override
    public Loader<List<Dua>> onCreateLoader(int id, Bundle args) {
        return new DuaDetailsLoader(DuaDetailActivity.this, duaIdFromDuaListActivity);
    }

    @Override
    public void onLoadFinished(Loader<List<Dua>> loader, List<Dua> data) {
        if (adapter == null) {
            adapter = new DuaDetailAdapter(this, data);
            listView.setAdapter(adapter);
        } else {
            adapter.setData(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Dua>> loader) {
        if (adapter != null) {
            adapter.setData(null);
        }
    }
}