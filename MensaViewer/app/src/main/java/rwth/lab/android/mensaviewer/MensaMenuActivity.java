package rwth.lab.android.mensaviewer;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

import rwth.lab.android.mensaviewer.fragments.MensaWeekPlanFragment;
import rwth.lab.android.mensaviewer.model.MensaListItem;

/**
 * Created by ekaterina on 30.04.2015.
 */
public class MensaMenuActivity extends FragmentActivity {
    private final static int MENU_REFRESH = Menu.FIRST;
    private final static String FRAGMENT_TAG = "data";
    private MensaWeekPlanFragment weekPlanFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mensa_week_plan_activity);
        Bundle extras = getIntent().getExtras();
        MensaListItem mensaListItem = (MensaListItem) extras.getSerializable(ViewerInitialActivity.MENSA_ITEM);
        if (mensaListItem != null) {
            setTitle(mensaListItem.getMensaName());
        }

        FragmentManager fragmentManager = getFragmentManager();
        //fetch the fragment if it was saved (e.g. during orientation change)
        this.weekPlanFragment = (MensaWeekPlanFragment) fragmentManager.findFragmentByTag(FRAGMENT_TAG);
        if (weekPlanFragment == null) {
            // add the fragment
            weekPlanFragment = new MensaWeekPlanFragment();
            fragmentManager.beginTransaction().add(R.id.fragment_container, weekPlanFragment, FRAGMENT_TAG).commit();
            weekPlanFragment.setArguments(extras);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(Menu.NONE, MENU_REFRESH, Menu.NONE, R.string.refresh);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MENU_REFRESH:
                refresh();
                return true;
            default:
                return false;
        }
    }

    private void refresh() {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().remove(weekPlanFragment).commit();
        recreate();
    }
}