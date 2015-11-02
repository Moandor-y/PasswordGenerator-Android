package org.moandor.passwordgenerator;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by Moandor on 11/2/2015.
 */
public class FileManager {
    private static final String SALT_FILE_NAME = "salt.bin";

    public static FileOutputStream openSaltFileWriteStream() throws FileNotFoundException {
        return GlobalContext.getInstance().openFileOutput(SALT_FILE_NAME, Context.MODE_PRIVATE);
    }

    public static FileInputStream openSaltFileReadStream() throws FileNotFoundException {
        return GlobalContext.getInstance().openFileInput(SALT_FILE_NAME);
    }
}
