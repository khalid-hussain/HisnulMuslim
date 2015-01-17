package adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bluejamesbond.text.DocumentView;
import com.example.khalid.hisnulmuslim.R;

import java.util.List;

import classes.Dua;

public class DuaDetailAdapter extends BaseAdapter {
    private static Typeface sCachedTypeface = null;

    private List<Dua> mList;
    private LayoutInflater mInflater;

    public DuaDetailAdapter(Context context, List<Dua> items) {
        mInflater = LayoutInflater.from(context);
        mList = items;

        if (sCachedTypeface == null) {
            sCachedTypeface = Typeface.createFromAsset(
                    context.getAssets(), "fonts/Amiri-Regular.ttf");
        }
    }

    public void setData(List<Dua> items) {
        mList = items;
        notifyDataSetChanged();
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
            convertView = mInflater.inflate(R.layout.dua_detail_item_card, parent, false);

            holder = new ViewHolder();
            holder.tvDuaNumber = (TextView) convertView.findViewById(R.id.txtDuaNumber);
            holder.tvDuaArabic = (TextView) convertView.findViewById(R.id.txtDuaArabic);
            holder.tvDuaArabic.setTypeface(sCachedTypeface);
            holder.tvDuaTranslation = (DocumentView) convertView.findViewById(R.id.txtDuaTranslation);
            holder.tvDuaReference = (TextView) convertView.findViewById(R.id.txtDuaReference);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();

        Dua p = getItem(position);
        if (p != null) {
            holder.tvDuaNumber.setText("" + p.getReference());
            holder.tvDuaArabic.setText("" + p.getArabic());

            final Spannable translation = new SpannableString(p.getTranslation());
            holder.tvDuaTranslation.setText(translation);
            holder.tvDuaReference.setText("" + p.getBook_reference());
        }
        return convertView;
    }

    public static class ViewHolder {
        TextView tvDuaNumber;
        TextView tvDuaArabic;
        TextView tvDuaReference;
        DocumentView tvDuaTranslation;
    }
}