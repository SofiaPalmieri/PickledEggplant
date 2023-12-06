package com.olenickglobal.elements;

import com.olenickglobal.entities.Screen;
import com.olenickglobal.exceptions.ElementNotFoundException;
import com.olenickglobal.exceptions.ImageNotFoundException;
import com.olenickglobal.exceptions.OCRException;
import com.olenickglobal.configuration.ConfigReader;
import com.olenickglobal.entities.ScreenCapture;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.TesseractException;

import java.awt.*;

public class TextElement extends ScreenElement {
    protected final String text;

    public TextElement(String text) {
        this(text, new FixedOffset(Alignment.CENTER, 0, 0));
    }

    public TextElement(String text, Offset offset) {
        // TODO: Different screens? Inner rectangles? Parent element?
        super(new Screen(), offset);
        this.text = text;
    }

    // FIXME: ???
    public TextElement(ScreenCapture capture) {
        super(new Screen(), new FixedOffset(Alignment.CENTER, 0, 0));
        ITesseract tesseract = ConfigReader.getInstance().getTesseract();
        try {
            this.text = tesseract.doOCR(capture.image());
        } catch (TesseractException e) {
            throw new OCRException(e);
        }
    }

    @Override
    protected Rectangle getMatch(double timeout) throws ElementNotFoundException {
        try {
            return screen.findText(timeout, text);
        } catch (ImageNotFoundException e) {
            throw new ElementNotFoundException(e);
        }
    }
}
