package com.olenickglobal.Exceptions;

import net.sourceforge.tess4j.TesseractException;

public class TesseractOCRException extends RuntimeException {
    public TesseractOCRException(TesseractException e) {
        super("There was an error when performing OCR on the screen",e);
    }
}
