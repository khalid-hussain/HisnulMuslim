package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.khalid.hisnulmuslim.R;

import java.util.List;
import java.util.Locale;

import classes.Dua;

public class DuaGroupAdapter extends AbsArrayAdapter<Dua> implements Filterable {

    public DuaGroupAdapter(Context activity) {
        super(activity);
    }

    public DuaGroupAdapter(Context activity, List<Dua> list) {
        super(activity, list);
    }

    @Override
    public Filter getFilter() {
        // return a filter that filters data based on a constraint
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                return null;
            }

            @Override
            protected void publishResults(CharSequence constraint, Filter.FilterResults results) {
            }
        };
    }


    @Override
    public View getView(LayoutInflater inflater, int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            // LayoutInflater vi;
            inflater = LayoutInflater.from(getContext());
            v = inflater.inflate(R.layout.dua_list_item_card, null);
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