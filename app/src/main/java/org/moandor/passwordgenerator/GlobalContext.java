package org.moandor.passwordgenerator;

import android.app.Application;
import android.support.annotation.NonNull;

/**
 * Created by Moandor on 10/29/2015.
 */
public class GlobalContext extends Application {
    public static final int SALT_BYTE_COUNT = 64;

    private static GlobalContext sInstance = null;

    @Override
    public void onCreate() {
        sInstance = this;
        super.onCreate();
    }

    @NonNull
    public static GlobalContext getInstance() {
        if (sInstance == null) {
            throw new RuntimeException("GlobalContext used before initialized");
        }
        return sInstance;
    }
}
