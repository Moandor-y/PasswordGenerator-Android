package org.moandor.passwordgenerator;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

/**
 * Created by Moandor on 10/30/2015.
 */
public class SaltDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(View.inflate(getActivity(), R.layout.dialog_salt, null));
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //TODO
            }
        });
        builder.setNegativeButton(R.string.cancel, null);
        return builder.create();
    }
}
