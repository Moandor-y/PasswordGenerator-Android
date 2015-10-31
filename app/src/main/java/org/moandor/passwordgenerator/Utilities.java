package org.moandor.passwordgenerator;

import android.support.annotation.NonNull;

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
}
