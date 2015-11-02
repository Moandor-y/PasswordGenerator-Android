package org.moandor.passwordgenerator;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Created by Moandor on 10/31/2015.
 */
public class Utilities {
    public static byte[] hexToBytes(@NonNull String hex) {
        if (hex.length() % 2 != 0) {
            hex += "0";
        }
        byte[] data = new byte[hex.length() / 2];
        for (int i = 0; i < hex.length(); i += 2) {
            data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                    + Character.digit(hex.charAt(i + 1), 16));
        }
        return data;
    }

    @SafeVarargs
    public static <Params> void executeAsyncTask(AsyncTask<Params, ?, ?> task, Params... params) {
        task.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, params);
    }

    public static void setEnabledForAllViews(@NonNull View view, boolean enabled) {
        if (view instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) view;
            for (int i = 0; i < group.getChildCount(); i++) {
                setEnabledForAllViews(group.getChildAt(i), enabled);
            }
        } else {
            view.setEnabled(enabled);
        }
    }

    public static void showToast(@StringRes int stringRes) {
        Toast.makeText(GlobalContext.getInstance(), stringRes, Toast.LENGTH_SHORT).show();
    }
}
