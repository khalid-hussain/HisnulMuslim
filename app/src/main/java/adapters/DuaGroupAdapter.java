package adapters;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.khalid.hisnulmuslim.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import classes.Dua;
import database.ExternalDbOpenHelper;
import database.HisnDatabaseInfo;

public class DuaGroupAdapter extends AbsArrayAdapter<Dua> implements Filterable {
    private Context mContext;
    private LayoutInflater mInflater;

    public DuaGroupAdapter(Context context, List<Dua> list) {
        super(context, list);
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public Filter getFilter() {
        // return a filter that filters data based on a constraint
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final ExternalDbOpenHelper helper = ExternalDbOpenHelper.getInstance(mContext);
                final SQLiteDatabase db = helper.openDataBase();

                final List<Dua> duas = new ArrayList<>();
                Cursor c = null;
                try {
                    c = db.query(HisnDatabaseInfo.DuaGroupTable.TABLE_NAME, null,
                            HisnDatabaseInfo.DuaGroupTable.ENGLISH_TITLE + " like ?",
                            new String[] { "%" + constraint + "%" }, null, null, null);
                    if (c != null && c.moveToFirst()) {
                        do {
                            final Dua dua = new Dua(c.getInt(0), c.getString(2));
                            duas.add(dua);
                        } while (c.moveToNext());
                    }
                } finally {
                    if (c != null) {
                        c.close();
                    }
                }

                final FilterResults results = new FilterResults();
                results.values = duas;
                results.count = duas.size();
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, Filter.FilterResults results) {
                if (results.count > 0) {
                    clear();
                    addAll((List<Dua>) results.values);
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };
    }


    @Override
    public View getView(LayoutInflater inflater, int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            v = mInflater.inflate(R.layout.dua_list_item_card, parent, false);
        }

        Dua p = getItem(position);

        if (p != null) {
            TextView tvReference = (TextView) v.findViewById(R.id.txtReference);
            TextView tvDuaName = (TextView) v.findViewById(R.id.txtDuaName);

            if (tvReference != null) {
                tvReference.setText("" + p.getReference());
            }
            if (tvDuaName != null) {
                tvDuaName.setText(p.getTitle());
            }
        }
        return v;
    }

    @Override
    public boolean isFilteredOut(Dua dua, CharSequence constraint) {
        return !dua.getGroup().toLowerCase(Locale.US).contains(constraint.toString().toLowerCase(Locale.US));
    }
}