package rwth.lab.android.mensaviewer.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.List;

import rwth.lab.android.mensaviewer.R;
import rwth.lab.android.mensaviewer.adapters.MenuListAdapter;
import rwth.lab.android.mensaviewer.model.DayPlan;
import rwth.lab.android.mensaviewer.model.IMenuItem;

/**
 * Created by ekaterina on 01.05.2015.
 */
public class MenuFragment extends ListFragment {
    public static final String DAYPLAN_KEY = "MENUES";

    private List<IMenuItem> menuItems;
    private MenuListAdapter adapter;
    private DayPlan dayPlan;

    /**
     * Creates an instance of a menu fragment passing the day plan to its bundle
     *
     * @param dayPlan a day plan for this mensa
     * @return an instance of a newly created menu fragment
     */
    public static MenuFragment newInstance(DayPlan dayPlan) {
        Bundle args = new Bundle();
        args.putSerializable(DAYPLAN_KEY, (Serializable) dayPlan);
        MenuFragment fragment = new MenuFragment();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.dayPlan = (DayPlan) getArguments().getSerializable(DAYPLAN_KEY);

        if (dayPlan.isMensaOpen()) {
            this.menuItems = dayPlan.getMenuItems();
            this.adapter = new MenuListAdapter(getActivity().getApplicationContext());
            addMenuItemsToAdapter();
            setListAdapter(this.adapter);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.mensa_menu_list_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (!dayPlan.isMensaOpen()) {
            TextView note = (TextView) getView().findViewById(R.id.mensaClosedNote);
            note.setText(dayPlan.getNote());
            note.setVisibility(View.VISIBLE);
        }
        getListView().setChoiceMode(ListView.CHOICE_MODE_NONE);
    }

    /**
     * Adds the menu items (extras and menues)
     * to the menu list adapter
     */
    private void addMenuItemsToAdapter() {
        if (this.adapter != null && this.menuItems != null) {
            for (IMenuItem menu : this.menuItems) {
                this.adapter.add(menu);
            }
        }
    }
}
