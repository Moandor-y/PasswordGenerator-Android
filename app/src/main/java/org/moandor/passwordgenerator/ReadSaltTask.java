package org.moandor.passwordgenerator;

import android.os.AsyncTask;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by Moandor on 11/2/2015.
 */
public class ReadSaltTask extends AsyncTask<Void, Void, byte[]> {
    private OnFinishListener mListener;

    @Override
    protected byte[] doInBackground(Void... params) {
        byte[] data = new byte[GlobalContext.SALT_BYTE_COUNT];
        try (FileInputStream stream = FileManager.openSaltFileReadStream()) {
            int count = stream.read(data);
            if (count != GlobalContext.SALT_BYTE_COUNT) {
                cancel(true);
            }
        } catch (IOException e) {
            cancel(true);
        }
        return data;
    }

    @Override
    protected void onPostExecute(byte[] result) {
        super.onPostExecute(result);
        if (mListener != null) {
            mListener.onFinish(true, result);
        }
    }

    @Override
    protected void onCancelled(byte[] result) {
        super.onCancelled(result);
        if (mListener != null) {
            mListener.onFinish(false, result);
        }
    }

    public void setOnFinishListener(OnFinishListener listener) {
        mListener = listener;
    }

    public interface OnFinishListener {
        void onFinish(boolean succeeded, byte[] salt);
    }
}
