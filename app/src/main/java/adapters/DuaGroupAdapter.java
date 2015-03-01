package adapters;

import com.example.khalid.hisnulmuslim.R;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import classes.Dua;
import database.ExternalDbOpenHelper;
import database.HisnDatabaseInfo;

public class DuaGroupAdapter extends BaseAdapter implements Filterable {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<Dua> mList;
    private CharSequence mSearchText = "";
    private boolean mIsNightMode;
    private int mAccentColor;
    private int mTextColor;
    private int mTextColorNightMode;
    private int mCircleColor;
    private int mCircleNightColor;

    public DuaGroupAdapter(Context context, List<Dua> list, boolean isNightMode) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
        mList = list;
        mIsNightMode = isNightMode;

        Resources resources = context.getResources();
        mAccentColor = resources.getColor(R.color.colorAccent);
        mTextColor = resources.getColor(R.color.material_sub_bg_text);
        mTextColorNightMode = resources.getColor(R.color.material_sub_bg);
        mCircleColor = resources.getColor(R.color.colorPrimary);
        mCircleNightColor = resources.getColor(R.color.nightModeColorPrimary);
    }

    public void setData(List<Dua> list) {
        mList = list;
        notifyDataSetChanged();
    }

    public void setNightMode(boolean isNightMode) {
      mIsNightMode = isNightMode;
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
                            new String[]{"%" + constraint + "%"}, null, null, null);
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
                mSearchText = constraint;
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
            holder.shape = (GradientDrawable) holder.tvReference.getBackground();
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        Dua p = getItem(position);
        if (p != null) {
            holder.tvReference.setText("" + p.getReference());
            holder.tvDuaName.setText(p.getTitle());
            holder.tvDuaName.setTextColor(mIsNightMode ? mTextColorNightMode : mTextColor);
            holder.shape.setColor(mIsNightMode ? mCircleNightColor : mCircleColor);

            String filter = mSearchText.toString();
            String itemValue = holder.tvDuaName.getText().toString();

            int startPos = itemValue.toLowerCase(Locale.US).indexOf(filter.toLowerCase(Locale.US));
            int endPos = startPos + filter.length();

            if (startPos != -1) { // This should always be true, just a sanity check
                Spannable spannable = new SpannableString(itemValue);
                ForegroundColorSpan highlightSpan = new ForegroundColorSpan(mAccentColor);
                spannable.setSpan(highlightSpan, startPos,
                    endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                StyleSpan s = new StyleSpan(Typeface.BOLD);
                spannable.setSpan(s, startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                holder.tvDuaName.setText(spannable);
            } else {
              holder.tvDuaName.setText(itemValue);
            }
        }
        return convertView;
    }

    public static class ViewHolder {
        TextView tvDuaName;
        TextView tvReference;
        GradientDrawable shape;
    }
}