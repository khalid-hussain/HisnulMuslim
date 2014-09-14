package fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.khalid.hisnulmuslim.R;
import com.nhaarman.listviewanimations.swinginadapters.prepared.SwingBottomInAnimationAdapter;

import java.util.ArrayList;

import adapters.Dua;
import adapters.DuaListAdapter;
import database.ExternalDbOpenHelper;

public class FragmentDuaList extends Fragment {

    private static final String DB_NAME = "hisnul.sqlite3";

    //A good practice is to define database field names as constants
    private static final String TABLE_NAME = "en_dua";
    private static final String DUA_ID = "_id";
    private static final String DUA_TITLE = "title";

    private SQLiteDatabase database;
    private ArrayList duas;

    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_dua_list, container, false);

        // Disable displaying up caret near the app icon
        // getActivity().getActionBar().setDisplayHomeAsUpEnabled(false);

        //Our key helper
        ExternalDbOpenHelper dbOpenHelper = new ExternalDbOpenHelper(getActivity(), DB_NAME);
        database = dbOpenHelper.openDataBase();

        //That’s it, the database is open!
        fillDua();
        setUpDuaList();

        return rootView;
    }

    public void fillDua(){
        //Extracting Elements from Database
        duas = new ArrayList<Dua>();
        Cursor friendCursor = database.query(TABLE_NAME, new String[] {DUA_ID,
                DUA_TITLE}, null, null, null, null, DUA_ID);
        friendCursor.moveToFirst();
        if(!friendCursor.isAfterLast()) {
            do {
                String reference = friendCursor.getString(0);
                String name = friendCursor.getString(1);
                duas.add(new Dua(Integer.parseInt(reference), name));
            } while (friendCursor.moveToNext());
        }
        friendCursor.close();
    }

    public void setUpDuaList(){
        final ListView listView;
        listView = (ListView) rootView.findViewById(R.id.duaListView);

        DuaListAdapter customAdapter;
        customAdapter = new DuaListAdapter(getActivity(), duas);

        SwingBottomInAnimationAdapter swingBottomInAnimationAdapter =
                new SwingBottomInAnimationAdapter(customAdapter);

        // Assign the ListView to the AnimationAdapter and vice versa
        swingBottomInAnimationAdapter.setAbsListView(listView);
        listView.setAdapter(swingBottomInAnimationAdapter);

        //Let’s set a message shown upon tapping an item
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position,long id) {
                FragmentDuaDetail DuaDetailFragment = new FragmentDuaDetail();

                // Send the id of the position clicked to the detail fragment
                Bundle bundle = new Bundle();
                bundle.putString("dua_id", position+1 +"");
                DuaDetailFragment.setArguments(bundle);

                FragmentTransaction ft = getFragmentManager().beginTransaction();

                // Replace whatever is in the fragment_container view with this fragment,
                // and add the transaction to the back stack
                ft.replace(R.id.container, DuaDetailFragment, "DetailListFragment");
                ft.addToBackStack(null);

                // Commit the transaction
                ft.commit();
            }
        });
    }
}