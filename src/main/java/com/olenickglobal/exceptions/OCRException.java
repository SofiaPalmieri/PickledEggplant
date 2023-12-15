package com.olenickglobal.exceptions;

import net.sourceforge.tess4j.TesseractException;

public class OCRException extends RuntimeException {
    public OCRException(Throwable cause) {
        super("There was an error when performing OCR on the screen.", cause);
    }
}
