package com.olenickglobal.entities;

import com.olenickglobal.exceptions.ImageNotFoundException;
import com.olenickglobal.exceptions.InteractionFailedException;
import com.olenickglobal.exceptions.NotImplementedYetError;
import org.sikuli.basics.Settings;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Location;
import org.sikuli.script.Match;

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
    @FunctionalInterface
    private interface BiFunctionWithFF<T, U, V> {
        V apply(T t, U u) throws FindFailed;
    }

    private final org.sikuli.script.Screen sikuliXScreen;

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

    public Rectangle findImage(double timeout, BufferedImage image) throws ImageNotFoundException {
        try {
            Match match = sikuliXScreen.wait(image, timeout);
            if (!match.isValid()) {
                throw new ImageNotFoundException("Unable to find image: Match invalid.");
            }
            return match.getRect();
        } catch (FindFailed e) {
            throw new ImageNotFoundException(e);
        } catch (RuntimeException e) { // SikuliX throws this. Blame them.
            String message = e.getMessage();
            if (message != null && message.startsWith("SikuliX:")) {
                throw new ImageNotFoundException(e);
            }
            throw e;
        }
    }

    public Rectangle findImage(double timeout, String path) throws ImageNotFoundException {
        try {
            Match match;
            File file = new File(path);
            if (!file.exists()) {
                throw new ImageNotFoundException("File '" + path + "' does not exist.");
            }
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                if (files == null || files.length == 0) {
                    throw new ImageNotFoundException("Directory '" + path + "' is empty.");
                }
                // TODO: Filter for actual images.
                List<Object> imageFiles = Arrays.stream(files).filter(File::isFile).filter(File::canRead).map(f -> (Object) f.getAbsolutePath()).toList();
                if (imageFiles.isEmpty()) {
                    throw new ImageNotFoundException("Directory '" + path + "' does not contain any images.");
                }
                // TODO: Check if this should be "Best" or "Any" and then a filter to get the first.
                match = sikuliXScreen.waitBestList(timeout, imageFiles);
            } else {
                match = sikuliXScreen.wait(file.getAbsolutePath(), timeout);
            }
            if (!match.isValid()) {
                throw new ImageNotFoundException("Unable to find '" + path + "': Match invalid.");
            }
            return match.getRect();
        } catch (FindFailed e) {
            throw new ImageNotFoundException(e);
        } catch (RuntimeException e) { // SikuliX throws this. Blame them.
            String message = e.getMessage();
            if (message != null && message.startsWith("SikuliX:")) {
                throw new ImageNotFoundException(e);
            }
            throw e;
        }
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

    public void rightClick(Point target) {
        rightClick(target, 0);
    }

    public void rightClick(Point target, int modifiers) {
        rightClick(target, modifiers, 0.0);
    }

    public void rightClick(Point target, int modifiers, double delay) {
        performDelayedClickAction(sikuliXScreen::rightClick, target, modifiers, delay);
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

    private void linger(double lingerTime) {
        try {
            Thread.sleep((long) (lingerTime * 1000));
        } catch (InterruptedException e) {
            // TODO: See what to do with this exception.
            throw new RuntimeException(e);
        }
    }

    private void performDelayedClickAction(BiFunctionWithFF<Location, Integer, Integer> method, Point target, int modifiers, double delay) throws InteractionFailedException {
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
