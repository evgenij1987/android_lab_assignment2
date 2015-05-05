package rwth.lab.android.mensaviewer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import rwth.lab.android.mensaviewer.R;
import rwth.lab.android.mensaviewer.model.IMenuItem;

/**
 * Created by ekaterina on 01.05.2015.
 */
public class MenuListAdapter extends AbstractListAdapter<IMenuItem> {

    public MenuListAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            view = inflater.inflate(R.layout.menu_plan_list_item, parent, false);
        } else {
            view = convertView;
        }

        if (view instanceof LinearLayout) {
            IMenuItem item = (IMenuItem) getItem(position);
            TextView category = (TextView)view.findViewById(R.id.category);
            category.setText(item.getCategory());
            TextView dish = (TextView)view.findViewById(R.id.dish);
            dish.setText(item.getDish());
            TextView price = (TextView)view.findViewById(R.id.price);
            price.setText(item.getPrice());
        }
        return view;
    }
}
