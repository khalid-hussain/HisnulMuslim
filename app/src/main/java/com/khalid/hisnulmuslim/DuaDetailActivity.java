package com.khalid.hisnulmuslim;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.khalid.hisnulmuslim.R;
import com.khalid.hisnulmuslim.adapter.DuaDetailAdapter;
import com.khalid.hisnulmuslim.loader.DuaDetailsLoader;
import com.khalid.hisnulmuslim.model.Dua;

import java.util.List;


public class DuaDetailActivity extends ActionBarActivity
        implements LoaderManager.LoaderCallbacks<List<Dua>> {
    private int duaIdFromDuaListActivity;
    private DuaDetailAdapter adapter;
    private ListView listView;

    private int primaryColor;
    private int primaryNightModeColor;
    private boolean prefNightMode;
    private SharedPreferences sharedPreferences;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dua_detail);

        toolbar = (Toolbar) findViewById(R.id.my_action_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.listView = (ListView) findViewById(R.id.duaDetailListView);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        duaIdFromDuaListActivity = bundle.getInt("dua_id");
        setTitle(bundle.getString("dua_title"));
        getSupportLoaderManager().initLoader(0, null, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_dua_detail, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        prefNightMode = sharedPreferences.getBoolean("pref_night_mode", false);
        // menu.findItem(R.id.action_night_mode).setChecked(prefNightMode);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, PreferencesActivity.class);
            this.startActivity(intent);
        } /*else if (id == R.id.action_night_mode) {
            SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
            prefsEditor.putBoolean("pref_night_mode", !item.isChecked()).commit();
            Toast.makeText(this, "NIGHT MODE " + !item.isChecked(), Toast.LENGTH_SHORT).show();
        }*/
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