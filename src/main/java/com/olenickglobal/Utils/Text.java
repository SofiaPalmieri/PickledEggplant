package com.olenickglobal.Utils;

import net.sourceforge.tess4j.ITessAPI;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.TesseractException;
import net.sourceforge.tess4j.Word;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Screen;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

public class Text extends ScreenE {

    private final String textToFind;

    Screen screen;

    PatternHandler patternHandler;

    public Text(String textToFind){

        this.textToFind = textToFind;
    }

    public Text(Rectangle readLocation) throws FindFailedHandler {
        try {
            ITesseract tesseract = DependencyHandler.getInstance().getTesseract();
            BufferedImage screen = new PickledRobot().getCroppedScreen(readLocation);
            this.textToFind = tesseract.doOCR(screen);
        } catch (TesseractException | FindFailedHandler | AWTException t){
            throw new FindFailedHandler(screen, patternHandler);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Rectangle find(double wait) throws FindFailedHandler, IOException, AWTException {

        long limit = this.getTimeoutLimit(wait);
        while(this.notTimedOut(limit)) {
            BufferedImage screen = new PickledRobot().getCurrentScreen();
            ITesseract instance = DependencyHandler.getInstance().getTesseract();

            java.util.List<Word> wordBoxes = instance.getWords(screen, ITessAPI.TessPageIteratorLevel.RIL_WORD);
            java.util.List<String> wordsToFind = java.util.List.of(this.textToFind.split(" "));
            Rectangle rect =  findBox(null,wordBoxes,wordsToFind);
            if (rect != null) return rect;
        }

        throw new FindFailedHandler(screen, patternHandler);
    }


    private Rectangle findBox(Rectangle startingRect, java.util.List<Word> availableWords, java.util.List<String> wordsToFind){
        if (wordsToFind.size() == 0) return startingRect;

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

                if (Math.sqrt(Math.pow(startingRect.x + startingRect.width - matchRectangle.x, 2) + Math.pow(startingRect.y + startingRect.height - matchRectangle.y, 2))/(matchRectangle.height) < 5) {
                    return findBox(new Rectangle(startingRect.x,startingRect.y,matchRectangle.x - startingRect.x + matchRectangle.width,matchRectangle.y - startingRect.y + matchRectangle.height),availableWords,wordsToFind.subList(1,wordsToFind.size()));
                }

            }
        }

        return null;
    }
    public void click(int timeout,ActionTypeText type) throws FindFailedHandler, IOException, FindFailed, AWTException {
        Rectangle rect = this.find(timeout);
        PickledRobot robot = new PickledRobot();
        robot.moveTo(rect);
        type.executeAction(robot, textToFind);
    }

    public String executeAction(ActionTypeText type) throws FindFailedHandler, IOException, FindFailed, AWTException {
        PickledRobot robot = new PickledRobot(textToFind);
        return  type.executeAction(robot, textToFind);
    }

}