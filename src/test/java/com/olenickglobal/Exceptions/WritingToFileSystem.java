package com.olenickglobal.Exceptions;

import java.io.IOException;

public class WritingToFileSystem extends RuntimeException {
    public WritingToFileSystem(IOException e) {
    }
}
