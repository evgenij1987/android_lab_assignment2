package rwth.lab.android.mensaviewer.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import rwth.lab.android.mensaviewer.R;
import rwth.lab.android.mensaviewer.ViewerInitialActivity;

/**
 * Created by ekaterina on 30.04.2015.
 */
public class InformationDialogFragment extends DialogFragment {
    public static InformationDialogFragment newInstance() {
        return new InformationDialogFragment();
    }

    // Build AlertDialog using AlertDialog.Builder
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setMessage(R.string.message)

                        // User cannot dismiss dialog by hitting back button
                .setCancelable(false)

                        // Set up browse Button
                .setPositiveButton(R.string.visitgitlab,
                        new DialogInterface.OnClickListener() {
                            public void onClick(final DialogInterface dialog,
                                                int id) {
                                ((ViewerInitialActivity) getActivity())
                                        .startImplicitActivation();
                            }
                        })

                        // Set up Not now Button
                .setNegativeButton(R.string.notnow, null).create();
    }
}
