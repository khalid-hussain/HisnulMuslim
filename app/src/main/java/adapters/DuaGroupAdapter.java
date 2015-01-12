package adapters;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.khalid.hisnulmuslim.R;

import java.util.ArrayList;
import java.util.List;

import classes.Dua;
import database.ExternalDbOpenHelper;
import database.HisnDatabaseInfo;

public class DuaGroupAdapter extends BaseAdapter implements Filterable {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<Dua> mList;

    public DuaGroupAdapter(Context context, List<Dua> list) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
        mList = list;
    }

    public void setData(List<Dua> list) {
        mList = list;
        notifyDataSetChanged();
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
                    mList = (List<Dua>) results.values;
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public Dua getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.dua_group_item_card, parent, false);
            holder = new ViewHolder();
            holder.tvReference = (TextView) convertView.findViewById(R.id.txtReference);
            holder.tvDuaName = (TextView) convertView.findViewById(R.id.txtDuaName);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();

        Dua p = getItem(position);
        if (p != null) {
            holder.tvReference.setText("" + p.getReference());
            holder.tvDuaName.setText(p.getTitle());
        }
        return convertView;
    }

    public static class ViewHolder {
        TextView tvDuaName;
        TextView tvReference;
    }
}