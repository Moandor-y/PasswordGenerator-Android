package org.moandor.passwordgenerator;

import android.os.AsyncTask;
import android.support.annotation.Nullable;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Moandor on 11/2/2015.
 */
public class WriteSaltTask extends AsyncTask<byte[], Void, Void> {
    @Nullable
    private OnFinishListener mListener;

    @Override
    protected Void doInBackground(byte[]... params) {
        try (FileOutputStream stream = FileManager.openSaltFileWriteStream()) {
            stream.write(params[0]);
        } catch (IOException e) {
            cancel(true);
        }
        return null;
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        if (mListener != null) {
            mListener.onFinish(false);
        }
    }

    @Override
    protected void onPostExecute(Void v) {
        super.onPostExecute(v);
        if (mListener != null) {
            mListener.onFinish(true);
        }
    }

    public void setOnFinishListener(OnFinishListener listener) {
        mListener = listener;
    }

    public interface OnFinishListener {
        void onFinish(boolean succeeded);
    }
}
