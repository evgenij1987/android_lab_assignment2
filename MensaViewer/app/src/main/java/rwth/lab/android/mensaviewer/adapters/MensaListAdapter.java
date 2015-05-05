package rwth.lab.android.mensaviewer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import rwth.lab.android.mensaviewer.R;
import rwth.lab.android.mensaviewer.model.MensaListItem;

/**
 * Created by ekaterina on 30.04.2015.
 */
public class MensaListAdapter extends AbstractListAdapter<MensaListItem> {

    public MensaListAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        TextView text;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            view = inflater.inflate(R.layout.mensa_list_item, parent, false);
        } else {
            view = convertView;
        }

        if (view instanceof TextView) {
            text = (TextView) view;
            MensaListItem item = (MensaListItem) getItem(position);
            text.setText(item.getMensaName());
        }
        return view;
    }
}
