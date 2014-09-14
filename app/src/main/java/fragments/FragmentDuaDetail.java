package fragments;

import android.app.Fragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.khalid.hisnulmuslim.R;

import java.util.ArrayList;

import adapters.DuaDetail;
import database.ExternalDbOpenHelper;

/**
 * Created by Khalid on 23 Jul 2014.
 */
public class FragmentDuaDetail extends Fragment {

    private static final String DB_NAME = "hisnul.sqlite3";

    //A good practice is to define database field names as constants
    private static final String TABLE_NAME = "en_dua";
    private static final String DUA_ID = "_id";
    private static final String DUA_TITLE = "title";

    private static final String DUA_ARABIC = "arabic";
    private static final String DUA_TRANSLATION = "translation";
    private static final String DUA_REFERENCE = "reference";

    private String title;
    private String arabic;
    private String translation;
    private String reference;

    private String dua_from_list_fragment;

    private SQLiteDatabase database;
    private ArrayList duaDetails;

    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_dua_detail, container, false);

        // Display up caret near the app icon
        getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);

        dua_from_list_fragment = getArguments().getString("dua_id");

        //Our key helper
        ExternalDbOpenHelper dbOpenHelper = new ExternalDbOpenHelper(getActivity(), DB_NAME);
        database = dbOpenHelper.openDataBase();
        //That’s it, the database is open!
        fillDua();
        setUpDuaList();

        // Used to fix the up caret action
        setHasOptionsMenu(true);
        return rootView;
    }

    public void fillDua(){
        //Extracting Elements from Database
        duaDetails = new ArrayList<DuaDetail>();
        Cursor friendCursor = database.query(TABLE_NAME, new String[] {DUA_TITLE,
                DUA_ARABIC, DUA_TRANSLATION, DUA_REFERENCE}, DUA_ID + "=" + dua_from_list_fragment, null, null, null, null);
        friendCursor.moveToFirst();
        if(!friendCursor.isAfterLast()) {
            do {
                title = friendCursor.getString(0);
                arabic = friendCursor.getString(1);
                translation = friendCursor.getString(2);
                reference = friendCursor.getString(3);
                duaDetails.add(new DuaDetail(title, arabic, translation, reference));
            } while (friendCursor.moveToNext());
        }
        friendCursor.close();
    }

    public void setUpDuaList(){
        TextView tvDuaTitle = (TextView) rootView.findViewById(R.id.txtDuaTitle);
        TextView tvDuaArabic = (TextView) rootView.findViewById(R.id.txtDuaArabic);
        TextView tvDuaTranslation = (TextView) rootView.findViewById(R.id.txtDuaTranslation);
        TextView tvDuaReference = (TextView) rootView.findViewById(R.id.txtDuaReference);

        Typeface face;
        face = Typeface.createFromAsset(getActivity().getAssets(), "DroidNaskh-Regular.ttf");
        tvDuaArabic.setTypeface(face);

        tvDuaTitle.setText(title);
        tvDuaArabic.setText(arabic);
        tvDuaTranslation.setText(translation);
        tvDuaReference.setText(reference);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Toast.makeText(getActivity(), "Test Caret", Toast.LENGTH_SHORT).show();
                getFragmentManager().popBackStack();
                Log.d("Khalid", "Testing CARET");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onDetach () {
        getActivity().getActionBar().setDisplayHomeAsUpEnabled(false);
        setHasOptionsMenu(false);
        //getActivity().invalidateOptionsMenu(); // <- not sure if needed
    }
}