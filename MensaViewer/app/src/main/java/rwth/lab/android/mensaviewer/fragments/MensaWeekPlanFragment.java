package rwth.lab.android.mensaviewer.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import rwth.lab.android.mensaviewer.R;
import rwth.lab.android.mensaviewer.ViewerInitialActivity;
import rwth.lab.android.mensaviewer.adapters.MenuFragmentPagerAdapter;
import rwth.lab.android.mensaviewer.http.OnResponseListener;
import rwth.lab.android.mensaviewer.http.WeekPlanGetRequest;
import rwth.lab.android.mensaviewer.model.MensaListItem;
import rwth.lab.android.mensaviewer.model.WeekPlan;

/**
 * Created by ekaterina on 03.05.2015.
 */
public class MensaWeekPlanFragment extends Fragment {
    private static final String TAG = "Mensa-Viewer";
    private MensaListItem mensaListItem;
    private WeekPlanGetRequest getRequest;
    private LinearLayout progressIndicator;
    private WeekPlan weekPlan;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mensaListItem = (MensaListItem) getArguments().getSerializable(ViewerInitialActivity.MENSA_ITEM);
        //retain state on configuration change (e.g. on orientation change)
        setRetainInstance(true);
        loadData();
    }

    /**
     * Creates and sends an Http GET request and delegates retrieving
     * of the mensa menu list to the parser
     */
    private void loadData() {
        getRequest = new WeekPlanGetRequest(this.mensaListItem);
        getRequest.setOnResponseListener(new OnResponseListener() {

            @Override
            public void onPreExecute() {
                MensaWeekPlanFragment.this.weekPlan = null;
            }

            @Override
            public void onResponse(WeekPlan weekPlan) {
                MensaWeekPlanFragment.this.weekPlan = weekPlan;
                showWeekPlan(getView());
            }

            @Override
            public void onError(String errorMessage) {
                Log.e(TAG, "Error during HTTP request: " + errorMessage);
            }
        });
        getRequest.send();
    }

    /**
     * Performs filling out the view components with the data (notes, additional dish information)
     * and a menu fragment via adapter
     *
     * @param view a view for the fragment
     */
    private void showWeekPlan(View view) {
        if (view != null) {
            TextView priceNotes = (TextView) view.findViewById(R.id.price_notes);
            priceNotes.setText(this.weekPlan.getPriceNote());
            TextView additivities = (TextView) view.findViewById(R.id.additivities);
            additivities.setText(this.weekPlan.getAddtitives());
            ViewPager viewPager = (ViewPager) view.findViewById(R.id.pager);
            viewPager.setAdapter(new MenuFragmentPagerAdapter(this.weekPlan, ((FragmentActivity) getActivity()).getSupportFragmentManager(),
                    getActivity()));
        }
        if (this.progressIndicator != null) {
            this.progressIndicator.setVisibility(View.GONE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mensa_menu_fragment, container, false);
        if (this.weekPlan == null) {
            // display the busy indicator if the week plan is not yet loaded
            this.progressIndicator = (LinearLayout) view.findViewById(R.id.progressIndicator);
            this.progressIndicator.setVisibility(View.VISIBLE);
        } else {
            showWeekPlan(view);
        }
        return view;
    }
}
