package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.khalid.hisnulmuslim.R;

import java.util.List;

import classes.Dua;
import classes.TextViewEx;

public class DuaDetailAdapter extends BaseAdapter {
    private List<Dua> mList;
    private LayoutInflater mInflater;

    public DuaDetailAdapter(Context context, List<Dua> items) {
        mInflater = LayoutInflater.from(context);
        mList = items;
    }

    @Override
    public int getCount() {
        return mList.size();
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
            holder.tvDuaTranslation = (TextViewEx) convertView.findViewById(R.id.txtDuaTranslation);
            holder.tvDuaReference = (TextView) convertView.findViewById(R.id.txtDuaReference);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();

        Dua p = getItem(position);
        if (p != null) {
            holder.tvDuaNumber.setText("" + p.getReference());
            holder.tvDuaArabic.setText("" + p.getArabic());
            holder.tvDuaTranslation.setText("" + p.getTranslation());
            holder.tvDuaReference.setText("" + p.getBook_reference());
        }
        return convertView;
    }

    public static class ViewHolder {
        TextView tvDuaNumber;
        TextView tvDuaArabic;
        TextViewEx tvDuaTranslation;
        TextView tvDuaReference;
    }
}