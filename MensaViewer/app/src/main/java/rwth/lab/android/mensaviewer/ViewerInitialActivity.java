package rwth.lab.android.mensaviewer;

import android.app.DialogFragment;
import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import rwth.lab.android.mensaviewer.adapters.MensaListAdapter;
import rwth.lab.android.mensaviewer.fragments.InformationDialogFragment;
import rwth.lab.android.mensaviewer.model.MensaListItem;

/**
 * Created by ekaterina on 30.04.2015.
 */
public class ViewerInitialActivity extends ListActivity {

    private static final int MENU_INFORMATION = Menu.FIRST;
    private static final String TAG = "Mensa-Viewer";
    public static final String MENSA_ITEM = "item";

    private DialogFragment dialog;
    private MensaListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create a new Adapter containing a list of mensas
        // Set the adapter on this ListActivity's built-in ListView
        this.adapter = new MensaListAdapter(getApplicationContext());
        addMensaItemsToAdapter();
        setListAdapter(this.adapter);

        ListView lv = getListView();

        // Enable filtering when the user types in the virtual keyboard
        lv.setTextFilterEnabled(true);

        // Set an setOnItemClickListener on the ListView
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Object item = adapter.getItem(position);
                if (item instanceof MensaListItem) {
                    MensaListItem mensaListItem = (MensaListItem) item;
                    Intent intent = new Intent(ViewerInitialActivity.this, MensaMenuActivity.class);
                    intent.putExtra(MENSA_ITEM, mensaListItem);
                    startActivity(intent);
                }
            }
        });
    }

    private void addMensaItemsToAdapter() {
        if (this.adapter != null) {
            this.adapter.add(new MensaListItem(getString(R.string.mensa_academica), getString(R.string.mensa_acadamica_url)));
            this.adapter.add(new MensaListItem(getString(R.string.mensa_ahorn), getString(R.string.mensa_ahorn_url)));
            this.adapter.add(new MensaListItem(getString(R.string.mensa_templergraben), getString(R.string.mensa_templergraben_url)));
            this.adapter.add(new MensaListItem(getString(R.string.mensa_bayernallee), getString(R.string.mensa_bayernalle_url)));
        }      this.adapter.add(new MensaListItem(getString(R.string.mensa_vita), getString(R.string.mensa_vita_url)));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        menu.add(Menu.NONE, MENU_INFORMATION, Menu.NONE, R.string.information);
        return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MENU_INFORMATION:
                showDialogFragment();
                return true;
            default:
                return false;
        }
    }

    private void showDialogFragment() {
        this.dialog = InformationDialogFragment.newInstance();
        this.dialog.show(getFragmentManager(), "Alert");
    }

    /**
     * Start a Browser Activity to view a web page or its URL.
     */
    public void startImplicitActivation() {
        Log.i(TAG, "Entered startImplicitActivation()");

        // Create a base intent for viewing a URL
        Uri webpage = Uri.parse(getString(R.string.group_url));
        Intent baseIntent = new Intent(Intent.ACTION_VIEW, webpage);
        startActivity(baseIntent);
    }
}
