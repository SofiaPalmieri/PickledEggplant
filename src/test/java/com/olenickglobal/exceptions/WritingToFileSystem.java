package com.olenickglobal.exceptions;

import java.io.IOException;

public class WritingToFileSystem extends RuntimeException {
    public WritingToFileSystem(IOException e) {
        super(e);
    }
}
