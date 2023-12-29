package com.olenickglobal.entities;

import com.olenickglobal.configuration.ConfigReader;
import com.olenickglobal.elements.RectangleElement;
import com.olenickglobal.exceptions.ImageNotFoundException;
import com.olenickglobal.exceptions.InteractionFailedException;
import com.olenickglobal.exceptions.NotImplementedYetError;
import com.olenickglobal.exceptions.OCRException;
import com.olenickglobal.exceptions.TextNotFoundException;
import net.sourceforge.tess4j.ITessAPI;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.TesseractException;
import net.sourceforge.tess4j.Word;
import org.sikuli.basics.Settings;
import org.sikuli.script.FindFailed;
import org.sikuli.script.ImagePath;
import org.sikuli.script.Location;
import org.sikuli.script.Match;
import org.sikuli.script.Pattern;
import org.sikuli.script.Region;

import javax.swing.JOptionPane;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * TODO: DELETE THIS COMMENT.
 * Settings.DefaultHighlightTime: N/D.
 * Settings.DelayAfterDrag: specifies the waiting time after mouse down at the source location as a decimal value (seconds).
 * Settings.DelayValue: N/D.
 * Settings.LogTime: N/D.
 * Settings.MoveMouseDelay: Control the time taken for mouse movement to a target location by setting this value to a decimal value (default 0.5). The unit is seconds. Setting it to 0 will switch off any animation (the mouse will “jump” to the target location).
 * Settings.ObserveScanRate: Specify the number of times actual search operations are performed per second while waiting for a pattern to appear or vanish.
 * Settings.SlowMotionDelay: Control the duration of the visual effect (seconds).
 * Settings.TypeDelay: Delay between the key presses in seconds as 0.nnn. This only applies to the next type and is then reset to 0 again. A value > 1 is cut to 1.0 (max delay of 1 second).
 * Settings.UserLogTime: N/D.
 * Settings.WaitAfterHighlight: N/D.
 * Settings.WaitScanRate: Specify the number of times actual search operations are performed per second while waiting for a pattern to appear or vanish.
 */

public class Screen {
    protected static final long OCR_POLLING_TIME_MS = 10;
    protected static final long OCR_POLLING_TIME_NS = OCR_POLLING_TIME_MS * 1_000_000;
    protected static final double SEARCH_SCALE_FACTOR = 5.0;

    // TODO: See if we should move this somewhere safer.
    static {
        System.setProperty("sun.java2d.uiScale.enabled", "false");
    }

    @FunctionalInterface
    protected interface BiFunctionWithFF<T, U, V> {
        V apply(T t, U u) throws FindFailed;
    }

    protected final org.sikuli.script.Screen sikuliXScreen;

    public Screen() {
        // TODO: Allow for different screens and unions.
        sikuliXScreen = new org.sikuli.script.Screen();
    }

    public BufferedImage capture(int x, int y, int width, int height) {
        return sikuliXScreen.capture(x, y, width, height).getImage();
    }

    public BufferedImage capture(Rectangle rectangle) {
        return sikuliXScreen.capture(rectangle).getImage();
    }

    public BufferedImage captureFullScreen() {
        return sikuliXScreen.capture().getImage();
    }

    public void click(Point target) throws InteractionFailedException {
        click(target, 0);
    }

    public void click(Point target, int modifiers) throws InteractionFailedException {
        click(target, modifiers, 0.0);
    }

    public void click(Point target, int modifiers, double delay) throws InteractionFailedException {
        performDelayedClickAction(sikuliXScreen::click, target, modifiers, delay);
    }

    public void doubleClick(Point target) throws InteractionFailedException {
        doubleClick(target, 0);
    }

    public void doubleClick(Point target, int modifiers) throws InteractionFailedException {
        doubleClick(target, modifiers, 0.0);
    }

    public void doubleClick(Point target, int modifiers, double delay) throws InteractionFailedException {
        performDelayedClickAction(sikuliXScreen::doubleClick, target, modifiers, delay);
    }

    public void dragDrop(Point origin, Point destination) throws InteractionFailedException {
        dragDrop(origin, 0, destination, Settings.DelayBeforeMouseDown, Settings.DelayBeforeDrag, Settings.DelayBeforeDrop);
    }

    public void dragDrop(Point origin, int modifiers, Point destination) throws InteractionFailedException {
        dragDrop(origin, modifiers, destination, Settings.DelayBeforeMouseDown, Settings.DelayBeforeDrag, Settings.DelayBeforeDrop);
    }

    public void dragDrop(Point origin, int modifiers, Point destination, double delayMouseDown, double delayDrag, double delayDrop) throws InteractionFailedException {
        if (modifiers != 0) { throw new NotImplementedYetError(); }
        try {
            // Settings.DelayBeforeDrag: specifies the waiting time after mouse down at the source location as a decimal value (seconds).
            // Settings.DelayBeforeDrop: specifies the waiting time before mouse up at the target location as a decimal value (seconds).
            // Settings.DelayBeforeMouseDown: specifies the waiting time before mouse down at the source location as a decimal value (seconds).
            Settings.DelayBeforeMouseDown = delayMouseDown;
            Settings.DelayBeforeDrag = delayDrag;
            Settings.DelayBeforeDrop = delayDrop;
            if (sikuliXScreen.dragDrop(new Location(origin), new Location(destination)) != 1) {
                throw new InteractionFailedException();
            }
        } catch (FindFailed e) {
            // TODO: This should never happen. See what to do with this exception.
            throw new RuntimeException(e);
        } finally {
            // TODO: Check these are reset as the usual interaction with SikuliX
            Settings.DelayBeforeMouseDown = Settings.DelayValue; // This is getting reset on normal action. We only make sure it'll do the same on failure.
            Settings.DelayBeforeDrag = Settings.DelayValue; // This is getting reset on normal action. We only make sure it'll do the same on failure.
            Settings.DelayBeforeDrop = Settings.DelayValue; // This is getting reset on normal action. We only make sure it'll do the same on failure.
        }
    }

    public Rectangle getBounds() {
        return sikuliXScreen.getBounds();
    }

    public Rectangle internalFindImage(double timeout, BufferedImage image, double minSimilarity) throws ImageNotFoundException, FindFailed {
        return internalFindImage(timeout, null, image, minSimilarity);
    }

    public Rectangle internalFindImage(double timeout, Rectangle area, BufferedImage image, double minSimilarity) throws ImageNotFoundException, FindFailed {
            Match match = getRegion(area).wait(asPattern(image, minSimilarity), timeout);
            if (match == null || !match.isValid()) {
                throw new ImageNotFoundException("Unable to find image: Match invalid.");
            }
            return match.getRect();

    }

    public Rectangle internalFindImage(double timeout, String path, double minSimilarity) throws ImageNotFoundException, FindFailed {
        return internalFindImage(timeout, null, path, minSimilarity);
    }

    public Rectangle internalFindImage(double timeout, Rectangle area, String path, double minSimilarity) throws ImageNotFoundException, FindFailed {
            Match match;
            File file = new File(path);
            if (!file.exists()) {
                throw new ImageNotFoundException("File '" + path + "' does not exist.");
            }
            Region region = getRegion(area);
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                if (files == null || files.length == 0) {
                    throw new ImageNotFoundException("Directory '" + path + "' is empty.");
                }
                // TODO: Filter for actual images.
                List<Object> imageFiles = Arrays.stream(files).filter(File::isFile).filter(File::canRead)
                        .map(f -> (Object) asPattern(f, minSimilarity)).toList();
                if (imageFiles.isEmpty()) {
                    throw new ImageNotFoundException("Directory '" + path + "' does not contain any images.");
                }
                match = region.waitBestList(timeout, imageFiles);
            } else {
                match = region.wait(asPattern(file, minSimilarity), timeout);
            }
            if (match == null || !match.isValid()) {
                throw new ImageNotFoundException("Unable to find '" + path + "': Match invalid.");
            }
            return match.getRect();

    }

    public Rectangle findImage(double timeout, Rectangle area, String path, double minSimilarity) throws FindFailed, InterruptedException {
        try{
           return this.internalFindImage(timeout,area,path, minSimilarity);
        }catch(FindFailed e){
            String[] options = new String[]{"Try again", "Cancel"};

            int optionPane = JOptionPane.showOptionDialog(null,
                    "Image " + path + " was not found",
                    "Find Failed",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    options,
                    options[0]);

            if (optionPane == 0){
                Settings.setImageCache(0);
                ImagePath.reset(path);
                Thread.sleep(2000);
                this.findImage(timeout,area,path, minSimilarity);
                Settings.setImageCache(64);
                return this.internalFindImage(timeout,area,path, minSimilarity);

            }  else{
                throw  new FindFailed("Find Failed");
            }

        }
    }

    public Rectangle internalFindText(double timeout, String text) {
        return internalFindText(timeout, null, text);
    }

    @SuppressWarnings("BusyWait") // No point of using wait-notify here.
    public Rectangle internalFindText(double timeout, Rectangle area, String text) {
        // TODO: Check if this can be done more accurately.
        boolean found = false;
        List<String> words = List.of(text.split(" "));
        long timeoutNanos = System.nanoTime() + (long)(timeout * 1000_000_000);
        Rectangle searchArea = area == null ? getBounds() : area;
        do {
            BufferedImage fullScreen = capture(searchArea);
            ITesseract tesseract = ConfigReader.getInstance().getTesseract();
            List<Word> ocrWords = tesseract.getWords(fullScreen, ITessAPI.TessPageIteratorLevel.RIL_WORD);
            Rectangle rect = getTextMatch(null, words, ocrWords);
            if (rect != null) {
                return rect;
            }
            if (System.nanoTime() - timeoutNanos > OCR_POLLING_TIME_NS) {
                try {
                    Thread.sleep(OCR_POLLING_TIME_MS);
                } catch (InterruptedException ignore) {
                }
            }
        } while (!found && System.nanoTime() < timeoutNanos);
        throw new TextNotFoundException(text);
    }

    public String getText() {
        return getText(getBounds());
    }

    public String getText(Rectangle rectangle) throws OCRException {
        ITesseract tesseract = ConfigReader.getInstance().getTesseract();
        String text;
        try {
            text = tesseract.doOCR(capture(rectangle));
        } catch (TesseractException e) {
            throw new OCRException(e);
        }
        return text;
    }

    public void hover(Point target) throws InteractionFailedException {
        hover(target, 0);
    }

    public void hover(Point target, int modifiers) throws InteractionFailedException {
        hover(target, modifiers, 0.0);
    }

    public void hover(Point target, int modifiers, double lingerTime) throws InteractionFailedException {
        if (modifiers != 0) { throw new NotImplementedYetError(); }
        try {
            sikuliXScreen.hover(new Location(target));
            linger(lingerTime);
        } catch (FindFailed e) {
            // TODO: This should never happen. See what to do with this exception.
            throw new RuntimeException(e);
        }
    }

    public RectangleElement interactivelyCreateElement() {
        return new RectangleElement(interactivelySelectRegion("Click and drag to select region of interaction"), true);
    }

    public Rectangle interactivelySelectRegion(String selectionMessage) {
        return sikuliXScreen.selectRegion(selectionMessage).getRect();
    }

    public void rightClick(Point target) {
        rightClick(target, 0);
    }

    public void rightClick(Point target, int modifiers) {
        rightClick(target, modifiers, 0.0);
    }

    public void rightClick(Point target, int modifiers, double delay) {
        performDelayedClickAction(sikuliXScreen::rightClick, target, modifiers, delay);
    }

    public void type(Point target, String text) throws FindFailed {
        sikuliXScreen.type(target,text);
    }

    public void type(String text) throws FindFailed {
        sikuliXScreen.type(text);
    }

    public void type(Point target, String text, int modifiers) throws FindFailed {
        sikuliXScreen.type(target,text,modifiers);
    }

    public void type(Point target, String text, String modifiers) throws FindFailed {
        sikuliXScreen.type(target,text,modifiers);
    }

    public void type(String text, String modifiers) throws FindFailed {
        sikuliXScreen.type(text,modifiers);
    }

    public void wait(Double seconds) throws FindFailed {
        sikuliXScreen.wait(seconds);
    }

    /*
    // FIXME: Do something with all this.
    public String getIDString() {
        sikuliXScreen.getIdFromPoint(0, 0);
        sikuliXScreen.getLastScreenImage();
        sikuliXScreen.has("", 0);
        sikuliXScreen.newLocation(0, 0);
        // ANDROID
        sikuliXScreen.aInput("");
        sikuliXScreen.aKey(0);
        sikuliXScreen.aSwipeDown();
        sikuliXScreen.aSwipeLeft();
        sikuliXScreen.aSwipeRight();
        sikuliXScreen.aSwipeUp();
        // WINDOWS?
        sikuliXScreen.userCapture();
        sikuliXScreen.checkMatch();
        sikuliXScreen.click();
        try {
            // ANDROID
            sikuliXScreen.aSwipe(new Object(), new Object());
            sikuliXScreen.aTap(new Object());
            // WINDOWS?
            sikuliXScreen.click("", 0);
            sikuliXScreen.doubleClick("", 0);
            sikuliXScreen.findAll("");
        } catch (FindFailed e) {
            throw new RuntimeException(e);
        }
        sikuliXScreen.contains(new Region(0, 0, 1, 1));
        sikuliXScreen.containsMouse();
        sikuliXScreen.delayClick(0);
        sikuliXScreen.delayType(0);
        sikuliXScreen.existsT("");
        sikuliXScreen.exists("", 0);
        sikuliXScreen.existsText("", 0);
        sikuliXScreen.findAllT("");
        sikuliXScreen.findAny("", "");
        sikuliXScreen.findBest("", "");
        sikuliXScreen.findAnyList(Collections.emptyList());
        sikuliXScreen.findBestList(Collections.emptyList());
        sikuliXScreen.findLine("");
        sikuliXScreen.findLines("");
        sikuliXScreen.findAllText("");
        sikuliXScreen.findWord("");
        sikuliXScreen.findWords("");
        return sikuliXScreen.getIDString();
    }
    */

    protected Pattern asPattern(BufferedImage image, double minSimilarity) {
        Pattern pattern = new Pattern(image);
        pattern.similar(minSimilarity);
        return pattern;
    }

    protected Pattern asPattern(File image, double minSimilarity) {
        Pattern pattern = new Pattern(image.getAbsolutePath());
        pattern.similar(minSimilarity);
        return pattern;
    }

    protected Region getRegion(Rectangle area) {
        return area == null ? sikuliXScreen : new Region(area.x, area.y, area.width, area.height, sikuliXScreen);
    }

    protected Rectangle getTextMatch(Rectangle startingRect, List<String> words, List<Word> ocrWords) {
        // TODO: Check this method out.
        if (words.isEmpty()) {
            return startingRect;
        }
        String word = words.get(0);
        List<Word> matches = ocrWords.stream().filter((Word w) -> w.getText().toLowerCase().contains(word.toLowerCase())).toList();
        for (Word match : matches) {
            if (startingRect == null) {
                Rectangle boundingRectangle = getTextMatch(match.getBoundingBox(), words.subList(1, words.size()), ocrWords);
                if (boundingRectangle != null) {
                    return boundingRectangle;
                }
            } else {
                Rectangle matchRectangle = match.getBoundingBox();
                if (Math.sqrt(Math.pow(startingRect.x + startingRect.width - matchRectangle.x, 2) + Math.pow(startingRect.y + startingRect.height - matchRectangle.y, 2)) / (matchRectangle.height) < SEARCH_SCALE_FACTOR) {
                    return getTextMatch(
                            new Rectangle(startingRect.x, startingRect.y, matchRectangle.x - startingRect.x + matchRectangle.width, matchRectangle.y - startingRect.y + matchRectangle.height),
                            words.subList(1, words.size()),
                            ocrWords);
                }
            }
        }
        return null;
    }

    protected void linger(double lingerTime) {
        try {
            Thread.sleep((long) (lingerTime * 1000));
        } catch (InterruptedException e) {
            // TODO: See what to do with this exception.
            throw new RuntimeException(e);
        }
    }

    protected void performDelayedClickAction(BiFunctionWithFF<Location, Integer, Integer> method, Point target, int modifiers, double delay) throws InteractionFailedException {
        try {
            // Settings.ClickDelay: Delay between the mouse down and up in seconds as 0.nnn. This only applies to the next click action and is then reset to 0 again. A value > 1 is cut to 1.0 (max delay of 1 second).
            Settings.ClickDelay = delay;
            if (method.apply(new Location(target), modifiers) != 1) {
                throw new InteractionFailedException();
            }
        } catch (FindFailed e) {
            // TODO: This should never happen. See what to do with this exception.
            throw new RuntimeException(e);
        } finally {
            Settings.ClickDelay = 0; // This is getting reset on normal action. We only make sure it'll do the same on failure.
        }
    }
}
