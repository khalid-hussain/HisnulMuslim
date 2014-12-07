package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.khalid.hisnulmuslim.R;

import java.util.List;

import classes.Dua;

public class DuaGroupAdapter extends ArrayAdapter<Dua> {
    public DuaGroupAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public DuaGroupAdapter(Context context, int resource, List<Dua> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.dua_list_item_card, null);
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
}