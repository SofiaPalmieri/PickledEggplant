package com.olenickglobal.utils;

import java.io.File;
import java.io.IOException;

public class FileUtils {
    public static File createFile(String filename) throws IOException {
        return createFile(filename, true);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static File createFile(String filename, boolean overwrite) throws IOException {
        File file = new File(filename);
        File path = file.getParentFile();
        if (path == null) {
            throw new IOException("Invalid path for file '" + filename + "'.");
        }
        try {
            path.mkdirs();
        } catch (SecurityException e) {
            throw new IOException("Unable to create path for file '" + filename + "'.", e);
        }
        if (!file.createNewFile() && !overwrite) {
            throw new IOException("File '" + filename + "' already exists.");
        }
        return file;
    }
}
