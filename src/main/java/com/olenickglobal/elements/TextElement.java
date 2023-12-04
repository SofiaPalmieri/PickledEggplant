package com.olenickglobal.elements;

import com.olenickglobal.entities.Screen;
import com.olenickglobal.exceptions.ElementNotFoundException;
import com.olenickglobal.exceptions.OCRException;
import com.olenickglobal.configuration.ConfigReader;
import com.olenickglobal.entities.ScreenCapture;
import net.sourceforge.tess4j.ITessAPI;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.TesseractException;
import net.sourceforge.tess4j.Word;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class TextElement extends ScreenElement {
    protected static final double SEARCH_SCALE_FACTOR = 5;

    protected final String textToFind;
    protected ScreenCapture capture;

    public TextElement(String textToFind) {
        this(textToFind, new FixedOffset(Alignment.CENTER, 0, 0));
    }

    public TextElement(String textToFind, Offset offset) {
        // TODO: Different screens? Inner rectangles? Parent element?
        super(new Screen(), offset);
        this.textToFind = textToFind;
    }

    // FIXME: ???
    public TextElement(ScreenCapture capture) {
        super(new Screen(), new FixedOffset(Alignment.CENTER, 0, 0));
        ITesseract tesseract = ConfigReader.getInstance().getTesseract();
        try {
            this.textToFind = tesseract.doOCR(capture.getImage());
        } catch (TesseractException e) {
            throw new OCRException(e);
        }
    }

    @Override
    protected Rectangle getMatch(double timeout) throws ElementNotFoundException {
        long limit = this.getTimeoutLimit(timeout);
        while (this.notTimedOut(limit)) {
            BufferedImage fullScreen = capture.getImage();
            ITesseract instance = ConfigReader.getInstance().getTesseract();
            List<Word> wordBoxes = instance.getWords(fullScreen, ITessAPI.TessPageIteratorLevel.RIL_WORD);
            List<String> wordsToFind = List.of(this.textToFind.split(" "));
            Rectangle rect = getMatch(null, wordBoxes, wordsToFind);
            if (rect != null) return rect;
        }
        throw new ElementNotFoundException("Text not found: " + this.textToFind);
    }

    protected Rectangle getMatch(Rectangle startingRect, List<Word> availableWords, List<String> wordsToFind) {
        if (wordsToFind.isEmpty()) return startingRect;
        String wordToFind = wordsToFind.get(0);
        List<Word> matches = availableWords.stream().filter((Word w) -> w.getText().toLowerCase().contains(wordToFind.toLowerCase())).toList();
        for (Word match : matches) {
            if (startingRect == null) {
                Rectangle boundingRectangle = getMatch(match.getBoundingBox(), availableWords, wordsToFind.subList(1, wordsToFind.size()));
                if (boundingRectangle != null) {
                    return boundingRectangle;
                }
            } else {
                Rectangle matchRectangle = match.getBoundingBox();
                if (Math.sqrt(Math.pow(startingRect.x + startingRect.width - matchRectangle.x, 2) + Math.pow(startingRect.y + startingRect.height - matchRectangle.y, 2)) / (matchRectangle.height) < SEARCH_SCALE_FACTOR) {
                    return getMatch(new Rectangle(startingRect.x, startingRect.y, matchRectangle.x - startingRect.x + matchRectangle.width, matchRectangle.y - startingRect.y + matchRectangle.height), availableWords, wordsToFind.subList(1, wordsToFind.size()));
                }
            }
        }
        return null;
    }

    private long getTimeoutLimit(double wait) {
        long t = java.lang.System.currentTimeMillis();
        long MILLISECONDS_IN_SECOND = 1000;
        return (long) (t + wait * MILLISECONDS_IN_SECOND);
    }

    // FIXME: ???
    private boolean notTimedOut(long limit) {
        return java.lang.System.currentTimeMillis() < limit;
    }
}
