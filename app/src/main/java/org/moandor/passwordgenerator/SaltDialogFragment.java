package org.moandor.passwordgenerator;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;

/**
 * Created by Moandor on 10/30/2015.
 */
public class SaltDialogFragment extends DialogFragment {
    @Nullable
    private OnFinishListener mListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final View view = View.inflate(getActivity(), R.layout.dialog_salt, null);
        final EditText saltEditText = (EditText) view.findViewById(R.id.salt);
        builder.setView(view);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String saltHex = saltEditText.getText().toString();
                if (mListener != null) {
                    byte[] dest = new byte[GlobalContext.SALT_BYTE_COUNT];
                    byte[] source = Utilities.hexToBytes(saltHex);
                    System.arraycopy(
                            source,
                            0,
                            dest,
                            0,
                            Math.min(GlobalContext.SALT_BYTE_COUNT, source.length));
                    mListener.onFinish(dest);
                }
            }
        });
        builder.setNegativeButton(R.string.cancel, null);
        Utilities.setEnabledForAllViews(view, false);
        ReadSaltTask task = new ReadSaltTask();
        task.setOnFinishListener(new ReadSaltTask.OnFinishListener() {
            @Override
            public void onFinish(boolean succeeded, byte[] salt, @Nullable Exception exception) {
                Utilities.setEnabledForAllViews(view, true);
                if (succeeded) {
                    saltEditText.setText(Utilities.bytesToHex(salt));
                }
            }
        });
        Utilities.executeAsyncTask(task);
        return builder.create();
    }

    public void setFinishListener(OnFinishListener listener) {
        mListener = listener;
    }

    public interface OnFinishListener {
        void onFinish(@NonNull byte[] salt);
    }
}
