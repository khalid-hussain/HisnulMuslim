package com.khalid.hisnulmuslim;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.khalid.hisnulmuslim.R;

import java.util.List;

import adapter.DuaGroupAdapter;
import classes.Dua;
import com.khalid.hisnulmuslim.loader.DuaGroupLoader;

public class DuaGroupActivity extends ActionBarActivity implements
        LoaderManager.LoaderCallbacks<List<Dua>> {
    private DuaGroupAdapter mAdapter;
    private ListView mListView;
    private Toolbar toolbar;
    private View rootView;

    private int primaryColor;
    private int primaryNightModeColor;
    private boolean prefNightMode;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dua_group);

        rootView = findViewById(R.id.root);
        toolbar = (Toolbar) findViewById(R.id.my_action_bar);
        setSupportActionBar(toolbar);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        prefNightMode = sharedPreferences.getBoolean("pref_night_mode", false);

        Resources resources = getResources();
        primaryColor = resources.getColor(R.color.colorPrimary);
        primaryNightModeColor = resources.getColor(R.color.nightModeColorPrimary);

        themeUi();

        mListView = (ListView) findViewById(R.id.duaListView);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent;
                intent = new Intent(getBaseContext(),
                        DuaDetailActivity.class);

                Dua SelectedDua = (Dua) parent.getAdapter().getItem(position);
                int dua_id = SelectedDua.getReference();
                String dua_title = SelectedDua.getTitle();

                intent.putExtra("dua_id", dua_id);
                intent.putExtra("dua_title", dua_title);

                startActivity(intent);
            }
        });

        getSupportLoaderManager().initLoader(0, null, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_dua_group, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                mAdapter.getFilter().filter(s);
                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_night_mode).setChecked(prefNightMode);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, PreferencesActivity.class);
            this.startActivity(intent);
        } else if (id == R.id.action_bookmarks) {
            Intent intent = new Intent(this, BookmarksActivity.class);
            this.startActivity(intent);
        } else if (id == R.id.action_about) {
            Intent intent = new Intent(this, AboutActivity.class);
            this.startActivity(intent);
        } else if (id == R.id.action_night_mode) {
            prefNightMode = !item.isChecked();
            SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
            prefsEditor.putBoolean("pref_night_mode", prefNightMode).apply();
            Toast.makeText(this,"NIGHT MODE " + prefNightMode, Toast.LENGTH_SHORT).show();
            themeUi();
        }
        return super.onOptionsItemSelected(item);
    }

    private void themeUi() {
      if (prefNightMode) {
        toolbar.setBackgroundColor(primaryNightModeColor);
        rootView.setBackgroundColor(Color.BLACK);
      } else {
        toolbar.setBackgroundColor(primaryColor);
        rootView.setBackgroundColor(Color.WHITE);
      }

      if (mAdapter != null) {
        mAdapter.setNightMode(prefNightMode);
        mAdapter.notifyDataSetChanged();
      }
    }

    @Override
    public Loader<List<Dua>> onCreateLoader(int id, Bundle args) {
        return new DuaGroupLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<List<Dua>> loader, List<Dua> data) {
        if (mAdapter == null) {
            mAdapter = new DuaGroupAdapter(this, data, prefNightMode);
            mListView.setAdapter(mAdapter);
        } else {
            mAdapter.setData(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Dua>> loader) {
        mAdapter.setData(null);
    }
}