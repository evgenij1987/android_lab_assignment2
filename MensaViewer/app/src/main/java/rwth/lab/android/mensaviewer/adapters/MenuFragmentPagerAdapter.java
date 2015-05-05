package rwth.lab.android.mensaviewer.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import rwth.lab.android.mensaviewer.fragments.MenuFragment;
import rwth.lab.android.mensaviewer.model.WeekPlan;

/**
 * Created by ekaterina on 01.05.2015.
 */
public class MenuFragmentPagerAdapter extends FragmentStatePagerAdapter {
    private Context context;
    private WeekPlan weekPlan;

    public MenuFragmentPagerAdapter(WeekPlan weekPlan, FragmentManager fm, Context context) {
        super(fm);
        this.weekPlan = weekPlan;
        this.context = context;
    }

    @Override
    public int getCount() {
        return weekPlan.getDayList().size();
    }

    @Override
    public Fragment getItem(int position) {
        return MenuFragment.newInstance(weekPlan.getDayList().get(position));
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return weekPlan.getDayList().get(position).getHeader();
    }
}
