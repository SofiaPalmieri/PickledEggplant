package com.olenickglobal.Utils;

import com.olenickglobal.Exceptions.TesseractOCRException;
import net.sourceforge.tess4j.ITessAPI;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.TesseractException;
import net.sourceforge.tess4j.Word;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Location;
import org.sikuli.script.Screen;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class TextElement implements ScreenElement {

    private static final double SEARCH_SCALE_FACTOR = 5;
    private  String textToFind;

    private ScreenCapture capture;

    Screen screen;

    public TextElement(String textToFind){
        this.textToFind = textToFind;
    }

    public TextElement(ScreenCapture capture) throws FindFailed{
        ITesseract tesseract = ConfigReader.getInstance().getTesseract();

        try {
            this.textToFind = tesseract.doOCR(capture.getImage());
        } catch (TesseractException e) {
            throw new TesseractOCRException(e);
        }


    }



    @Override
    public void click(Integer timeout) throws FindFailed {
        Rectangle found = this.find(timeout);
        new Location(found.x,found.y).click();
    }

    @Override
    public void doubleClick(Integer timeout) throws FindFailed {
        Rectangle found = this.find(timeout);
        new Location(found.x,found.y).doubleClick();
    }

    @Override
    public void rightClick(Integer timeout) throws FindFailed {
        Rectangle found = this.find(timeout);
        new Location(found.x,found.y).rightClick();
    }

    @Override
    public void moveTo(Integer timeout) throws FindFailed {
        Rectangle found = this.find(timeout);
        new Location(found.x,found.y).hover();
    }

    @Override
    public Boolean isFound(Integer timeout) {
        try {
            this.find(timeout);
            return true;
        } catch (FindFailed findFailed) {
            return false;
        }
    }

    @Override
    public void waitFor(Integer timeout) throws FindFailed {
        this.find(timeout);
    }

    @Override
    public void dragTo(ScreenElement scr, Integer timeout) throws FindFailed {
        Location targetLocation = scr.getLocation(timeout);
        Rectangle found = this.find(timeout);
        Location destinationLocation = new Location(found.x,found.y);
        this.screen.dragDrop(targetLocation,destinationLocation);
    }

    @Override
    public Location getLocation(Integer timeout) throws FindFailed {
        Rectangle found = this.find(timeout);
        return new Location(found.x,found.y);
    }


    private Rectangle find(Integer timeout) throws FindFailed {
        long limit = this.getTimeoutLimit(timeout);
        while (this.notTimedOut(limit)) {
            BufferedImage screen = capture.getImage();
            ITesseract instance = ConfigReader.getInstance().getTesseract();

            List<Word> wordBoxes = instance.getWords(screen, ITessAPI.TessPageIteratorLevel.RIL_WORD);
            List<String> wordsToFind = List.of(this.textToFind.split(" "));
            Rectangle rect = findBox(null, wordBoxes, wordsToFind);
            if (rect != null) return rect;
        }

        throw new FindFailed("Text not found: " + this.textToFind);
    }


    private Rectangle findBox(Rectangle startingRect, java.util.List<Word> availableWords, java.util.List<String> wordsToFind){
        if (wordsToFind.isEmpty()) return startingRect;
        String wordToFind = wordsToFind.get(0);
        List<Word> matches = availableWords.stream().filter((Word w) -> w.getText().toLowerCase().contains(wordToFind.toLowerCase())).toList();
        for(Word match : matches) {
            if (startingRect == null) {
                Rectangle boundingRectangle = findBox(match.getBoundingBox(),availableWords,wordsToFind.subList(1,wordsToFind.size()));
                if (boundingRectangle != null) {
                    return boundingRectangle;
                }
            } else {
                Rectangle matchRectangle = match.getBoundingBox();
                if (Math.sqrt(Math.pow(startingRect.x + startingRect.width - matchRectangle.x, 2) + Math.pow(startingRect.y + startingRect.height - matchRectangle.y, 2))/(matchRectangle.height) < SEARCH_SCALE_FACTOR) {
                    return findBox(new Rectangle(startingRect.x,startingRect.y,matchRectangle.x - startingRect.x + matchRectangle.width,matchRectangle.y - startingRect.y + matchRectangle.height),availableWords,wordsToFind.subList(1,wordsToFind.size()));
                }
            }
        }

        return null;
    }


    private long getTimeoutLimit(double wait){
        long t= java.lang.System.currentTimeMillis();
        long MILLISECONDS_IN_SECOND = 1000;
        return (long) (t+wait * MILLISECONDS_IN_SECOND);
    }

    private boolean notTimedOut(long limit){
        return java.lang.System.currentTimeMillis() < limit;
    }
}