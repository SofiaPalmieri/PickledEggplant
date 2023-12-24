package com.olenickglobal.entities;

import com.olenickglobal.configuration.ConfigReader;
import com.olenickglobal.elements.RectangleElement;
import com.olenickglobal.elements.events.CaptureData;
import com.olenickglobal.elements.events.ClickData;
import com.olenickglobal.elements.events.ClickData.ClickActionType;
import com.olenickglobal.elements.events.DragData;
import com.olenickglobal.elements.events.Event;
import com.olenickglobal.elements.events.EventEmitter;
import com.olenickglobal.elements.events.EventListener;
import com.olenickglobal.elements.events.EventType;
import com.olenickglobal.elements.events.HoverData;
import com.olenickglobal.elements.events.ImageLocator;
import com.olenickglobal.elements.events.InteractiveRectCreateData;
import com.olenickglobal.elements.events.InteractiveRectSelectData;
import com.olenickglobal.elements.events.LocatingData;
import com.olenickglobal.elements.events.OCRData;
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
import org.sikuli.script.Location;
import org.sikuli.script.Match;
import org.sikuli.script.Pattern;
import org.sikuli.script.Region;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.time.LocalDateTime;
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

    protected final EventEmitter eventEmitter;
    protected final org.sikuli.script.Screen sikuliXScreen;
    protected final SUT sut;

    public Screen(SUT sut) {
        eventEmitter = new EventEmitter();
        this.sut = sut;
        // TODO: Allow for different screens and unions.
        sikuliXScreen = new org.sikuli.script.Screen();
    }

    /**
     * Add an event listener.
     * @param type Event type.
     * @param listener Event listener for the event type.
     * @return True if the listener was added, false otherwise.
     */
    public boolean addEventListener(EventType type, EventListener<?, ?> listener) {
        return eventEmitter.addEventListener(type, listener);
    }

    public BufferedImage capture(int x, int y, int width, int height) {
        return capture(new Rectangle(x, y, width, height));
    }

    public BufferedImage capture(Rectangle rectangle) {
        EventType endEventType = EventType.AFTER_CAPTURE;
        BufferedImage image = null;
        RuntimeException error = null;
        LocalDateTime startTime = LocalDateTime.now();
        eventEmitter.emit(new Event<>(startTime, EventType.BEFORE_CAPTURE, new CaptureData(rectangle)));
        try {
            image = sikuliXScreen.capture(rectangle).getImage();
        } catch (RuntimeException e) {
            endEventType = EventType.CAPTURE_ERROR;
            throw error = e;
        } finally {
            eventEmitter.emit(new Event<>(startTime, LocalDateTime.now(), endEventType, new CaptureData(rectangle, image), error));
        }
        return image;
    }

    public BufferedImage captureFullScreen() {
        return capture(sikuliXScreen.getRect());
    }

    public void click(Point target) throws InteractionFailedException {
        click(target, 0);
    }

    public void click(Point target, int modifiers) throws InteractionFailedException {
        click(target, modifiers, 0.0);
    }

    public void click(Point target, int modifiers, double delay) throws InteractionFailedException {
        performDelayedClickAction(ClickActionType.LEFT_CLICK, sikuliXScreen::click, target, modifiers, delay);
    }

    public void doubleClick(Point target) throws InteractionFailedException {
        doubleClick(target, 0);
    }

    public void doubleClick(Point target, int modifiers) throws InteractionFailedException {
        doubleClick(target, modifiers, 0.0);
    }

    public void doubleClick(Point target, int modifiers, double delay) throws InteractionFailedException {
        performDelayedClickAction(ClickActionType.DOUBLE_CLICK, sikuliXScreen::doubleClick, target, modifiers, delay);
    }

    public void dragDrop(Point origin, Point destination) throws InteractionFailedException {
        dragDrop(origin, 0, destination, Settings.DelayBeforeMouseDown, Settings.DelayBeforeDrag, Settings.DelayBeforeDrop);
    }

    public void dragDrop(Point origin, int modifiers, Point destination) throws InteractionFailedException {
        dragDrop(origin, modifiers, destination, Settings.DelayBeforeMouseDown, Settings.DelayBeforeDrag, Settings.DelayBeforeDrop);
    }

    public void dragDrop(Point origin, int modifiers, Point destination, double delayMouseDown, double delayDrag, double delayDrop) throws InteractionFailedException {
        if (modifiers != 0) { throw new NotImplementedYetError(); }
        EventType endEventType = EventType.AFTER_DRAG_DROP;
        RuntimeException error = null;
        DragData eventData = new DragData(modifiers, origin, destination, delayMouseDown, delayDrag, delayDrop);
        LocalDateTime startTime = LocalDateTime.now();
        eventEmitter.emit(new Event<>(startTime, EventType.BEFORE_DRAG_DROP, eventData));
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
            endEventType = EventType.DRAG_DROP_ERROR;
            throw error = new RuntimeException(e);
        } finally {
            // TODO: Check these are reset as the usual interaction with SikuliX
            Settings.DelayBeforeMouseDown = Settings.DelayValue; // This is getting reset on normal action. We only make sure it'll do the same on failure.
            Settings.DelayBeforeDrag = Settings.DelayValue; // This is getting reset on normal action. We only make sure it'll do the same on failure.
            Settings.DelayBeforeDrop = Settings.DelayValue; // This is getting reset on normal action. We only make sure it'll do the same on failure.
            eventEmitter.emit(new Event<>(startTime, LocalDateTime.now(), endEventType, eventData, error));
        }
    }

    public Rectangle getBounds() {
        return sikuliXScreen.getBounds();
    }

    public Rectangle findImage(double timeout, BufferedImage image, double minSimilarity) throws ImageNotFoundException {
        return findImage(timeout, null, image, minSimilarity);
    }

    public Rectangle findImage(double timeout, Rectangle area, BufferedImage image, double minSimilarity) throws ImageNotFoundException {
        EventType endEventType = EventType.AFTER_LOCATING;
        BufferedImage bestMatch = null;
        Rectangle matchRect = null;
        RuntimeException error = null;
        LocalDateTime startTime = LocalDateTime.now();
        eventEmitter.emit(new Event<>(startTime, EventType.BEFORE_LOCATING, new LocatingData<>(timeout,
                new ImageLocator(image, minSimilarity), area)));
        try {
            Match match = getRegion(area).wait(asPattern(image, minSimilarity), timeout);
            if (match == null || !match.isValid()) {
                endEventType = EventType.LOCATING_ERROR;
                throw error = new ImageNotFoundException("Unable to find image: Match invalid.");
            }
            bestMatch = match.getImage().get();
            return matchRect = match.getRect();
        } catch (FindFailed e) {
            endEventType = EventType.LOCATING_ERROR;
            throw error = new ImageNotFoundException(e);
        } catch (RuntimeException e) { // SikuliX throws this. Blame them.
            endEventType = EventType.LOCATING_ERROR;
            String message = e.getMessage();
            if (message != null && message.startsWith("SikuliX:")) {
                throw error = new ImageNotFoundException(e);
            }
            throw error = e;
        } finally {
            eventEmitter.emit(new Event<>(startTime, LocalDateTime.now(), endEventType, new LocatingData<>(timeout,
                    new ImageLocator(image, minSimilarity, bestMatch), area, null, matchRect), error));
        }
    }

    public Rectangle findImage(double timeout, String path, double minSimilarity) throws ImageNotFoundException {
        return findImage(timeout, null, path, minSimilarity);
    }

    public Rectangle findImage(double timeout, Rectangle area, String path, double minSimilarity) throws ImageNotFoundException {
        EventType endEventType = EventType.AFTER_LOCATING;
        BufferedImage bestMatch = null;
        Rectangle matchRect = null;
        Throwable error = null;
        LocalDateTime startTime = LocalDateTime.now();
        eventEmitter.emit(new Event<>(startTime, EventType.BEFORE_LOCATING, new LocatingData<>(timeout,
                new ImageLocator(path, minSimilarity), area)));
        try {
            Match match;
            File file = new File(path);
            if (!file.exists()) {
                throw (ImageNotFoundException)(error = new ImageNotFoundException("File '" + path + "' does not exist."));
            }
            Region region = getRegion(area);
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                if (files == null || files.length == 0) {
                    throw (ImageNotFoundException)(error = new ImageNotFoundException("Directory '" + path + "' is empty."));
                }
                // TODO: Filter for actual images.
                List<Object> imageFiles = Arrays.stream(files).filter(File::isFile).filter(File::canRead)
                        .map(f -> (Object) asPattern(f, minSimilarity)).toList();
                if (imageFiles.isEmpty()) {
                    throw (ImageNotFoundException)(error = new ImageNotFoundException("Directory '" + path + "' does not contain any images."));
                }
                match = region.waitBestList(timeout, imageFiles);
            } else {
                match = region.wait(asPattern(file, minSimilarity), timeout);
            }
            if (match == null || !match.isValid()) {
                throw (ImageNotFoundException)(error = new ImageNotFoundException("Unable to find '" + path + "': Match invalid."));
            }
            bestMatch = match.getImage().get();
            return matchRect = match.getRect();
        } catch (FindFailed e) {
            endEventType = EventType.LOCATING_ERROR;
            throw (ImageNotFoundException)(error = new ImageNotFoundException(e));
        } catch (RuntimeException e) { // SikuliX throws this. Blame them.
            endEventType = EventType.LOCATING_ERROR;
            String message = e.getMessage();
            if (message != null && message.startsWith("SikuliX:")) {
                throw (ImageNotFoundException)(error = new ImageNotFoundException(e));
            }
            throw (RuntimeException)(error = e);
        } finally {
            eventEmitter.emit(new Event<>(startTime, LocalDateTime.now(), endEventType, new LocatingData<>(timeout,
                    new ImageLocator(path, minSimilarity, bestMatch), area, null, matchRect), error));
        }
    }

    public Rectangle findText(double timeout, String text) {
        return findText(timeout, null, text);
    }

    @SuppressWarnings("BusyWait") // No point of using wait-notify here.
    public Rectangle findText(double timeout, Rectangle area, String text) {
        EventType endEventType = EventType.AFTER_LOCATING;
        Rectangle matchRect = null;
        Throwable error = null;
        LocalDateTime startTime = LocalDateTime.now();
        eventEmitter.emit(new Event<>(startTime, EventType.BEFORE_LOCATING, new LocatingData<>(timeout, text, area)));
        try {
            // TODO: Check if this can be done more accurately.
            boolean found = false;
            List<String> words = List.of(text.split(" "));
            long timeoutNanos = System.nanoTime() + (long) (timeout * 1000_000_000);
            Rectangle searchArea = area == null ? getBounds() : area;
            do {
                BufferedImage fullScreen = capture(searchArea);
                ITesseract tesseract = ConfigReader.getInstance().getTesseract();
                List<Word> ocrWords = tesseract.getWords(fullScreen, ITessAPI.TessPageIteratorLevel.RIL_WORD);
                matchRect = getTextMatch(null, words, ocrWords);
                if (matchRect != null) {
                    return matchRect;
                }
                if (System.nanoTime() - timeoutNanos > OCR_POLLING_TIME_NS) {
                    try {
                        Thread.sleep(OCR_POLLING_TIME_MS);
                    } catch (InterruptedException ignore) {
                        // TODO: Should we enforce sleeping here?
                    }
                }
            } while (!found && System.nanoTime() < timeoutNanos);
            throw new TextNotFoundException(text);
        } catch (RuntimeException exception) {
            throw (RuntimeException)(error = exception);
        } finally {
            eventEmitter.emit(new Event<>(startTime, LocalDateTime.now(), endEventType, new LocatingData<>(timeout,
                    text, area, null, matchRect), error));
        }
    }

    public SUT getSUT() {
        return sut;
    }

    public String getText() {
        return getText(getBounds());
    }

    public String getText(Rectangle rectangle) throws OCRException {
        EventType endEventType = EventType.AFTER_OCR;
        String text = null;
        OCRException error = null;
        LocalDateTime startTime = LocalDateTime.now();
        eventEmitter.emit(new Event<>(startTime, EventType.BEFORE_OCR, new OCRData(rectangle)));
        ITesseract tesseract = ConfigReader.getInstance().getTesseract();
        try {
            text = tesseract.doOCR(capture(rectangle));
        } catch (TesseractException e) {
            endEventType = EventType.OCR_ERROR;
            throw error = new OCRException(e);
        } finally {
            eventEmitter.emit(new Event<>(startTime, LocalDateTime.now(), endEventType, new OCRData(rectangle, text), error));
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
        EventType endEventType = EventType.AFTER_HOVER;
        InteractionFailedException error = null;
        LocalDateTime startTime = LocalDateTime.now();
        eventEmitter.emit(new Event<>(startTime, EventType.BEFORE_HOVER, new HoverData(modifiers, target, lingerTime)));
        try {
            sikuliXScreen.hover(new Location(target));
            linger(lingerTime);
        } catch (FindFailed e) {
            // TODO: This should never happen. See what to do with this exception.
            endEventType  = EventType.HOVER_ERROR;
            throw error = new InteractionFailedException(e);
        } finally {
            eventEmitter.emit(new Event<>(startTime, LocalDateTime.now(), endEventType, new HoverData(modifiers, target, lingerTime), error));
        }
    }

    public RectangleElement interactivelyCreateElement() throws InteractionFailedException {
        EventType endEventType = EventType.AFTER_INTERACT_RECT_CREATE;
        InteractionFailedException error = null;
        Rectangle rectangle = null;
        RectangleElement element = null;
        LocalDateTime startTime = LocalDateTime.now();
        eventEmitter.emit(new Event<>(startTime, EventType.BEFORE_INTERACT_RECT_CREATE, new InteractiveRectCreateData(this)));
        try {
            rectangle = interactivelySelectRegion("Click and drag to select region of interaction");
            element = new RectangleElement(rectangle, true);
        } catch (InteractionFailedException e) {
            endEventType = EventType.INTERACT_RECT_CREATE_ERROR;
            throw error = e;
        } finally {
            eventEmitter.emit(new Event<>(startTime, LocalDateTime.now(), endEventType, new InteractiveRectCreateData(this, rectangle, element), error));
        }
        return element;
    }

    public Rectangle interactivelySelectRegion(String selectionMessage) throws InteractionFailedException {
        EventType endEventType = EventType.AFTER_INTERACT_RECT_SELECT;
        InteractionFailedException error = null;
        Rectangle rectangle = null;
        LocalDateTime startTime = LocalDateTime.now();
        eventEmitter.emit(new Event<>(startTime, EventType.BEFORE_INTERACT_RECT_SELECT, new InteractiveRectSelectData(this)));
        try {
            rectangle = sikuliXScreen.selectRegion(selectionMessage).getRect();
        } catch (RuntimeException e) { // TODO: Check this
            endEventType = EventType.INTERACT_RECT_SELECT_ERROR;
            throw error = new InteractionFailedException(e);
        } finally {
            eventEmitter.emit(new Event<>(startTime, LocalDateTime.now(), endEventType, new InteractiveRectSelectData(this, rectangle), error));
        }
        return rectangle;
    }

    /**
     * Remove a pre-existing event listener.
     * @param type Event type.
     * @param listener Event listener for the event type.
     * @return True if the listener was listening for events, false otherwise.
     */
    public boolean removeEventListener(EventType type, EventListener<?, ?> listener) {
        return eventEmitter.removeEventListener(type, listener);
    }

    public void rightClick(Point target) {
        rightClick(target, 0);
    }

    public void rightClick(Point target, int modifiers) {
        rightClick(target, modifiers, 0.0);
    }

    public void rightClick(Point target, int modifiers, double delay) {
        performDelayedClickAction(ClickActionType.RIGHT_CLICK, sikuliXScreen::rightClick, target, modifiers, delay);
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

    protected void performDelayedClickAction(ClickActionType clickAction,
                                             BiFunctionWithFF<Location, Integer, Integer> method, Point target,
                                             int modifiers, double delay) throws InteractionFailedException {
        EventType endEventType = EventType.AFTER_CLICK;
        RuntimeException error = null;
        LocalDateTime startTime = LocalDateTime.now();
        eventEmitter.emit(new Event<>(startTime, EventType.BEFORE_CLICK, new ClickData(clickAction, modifiers, target, delay)));
        try {
            // Settings.ClickDelay: Delay between the mouse down and up in seconds as 0.nnn. This only applies to the next click action and is then reset to 0 again. A value > 1 is cut to 1.0 (max delay of 1 second).
            Settings.ClickDelay = delay;
            if (method.apply(new Location(target), modifiers) != 1) {
                throw new InteractionFailedException();
            }
        } catch (FindFailed e) {
            // TODO: This should never happen. See what to do with this exception.
            endEventType = EventType.CLICK_ERROR;
            throw error = new RuntimeException(e);
        } finally {
            Settings.ClickDelay = 0; // This is getting reset on normal action. We only make sure it'll do the same on failure.
            eventEmitter.emit(new Event<>(startTime, LocalDateTime.now(), endEventType, new ClickData(clickAction, modifiers, target, delay), error));
        }
    }
}
