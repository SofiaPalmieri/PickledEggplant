package com.olenickglobal.entities;

import com.olenickglobal.configuration.ConfigReader;
import com.olenickglobal.elements.ScreenElement;
import com.olenickglobal.elements.events.Event;
import com.olenickglobal.elements.events.EventEmitter;
import com.olenickglobal.elements.events.EventListener;
import com.olenickglobal.elements.events.EventType;
import com.olenickglobal.elements.events.SaveScreenshotData;
import com.olenickglobal.elements.events.SelectMainBoundsData;
import com.olenickglobal.elements.events.TypingData;
import com.olenickglobal.exceptions.SUTRobotException;
import com.olenickglobal.exceptions.SavingScreenCaptureException;
import com.olenickglobal.utils.FileUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

// FIXME: Try to use Screen to access SikuliX instead of it directly. Can we remove this Robot instance too?
public class SUT {
    private static volatile SUT instance;

    // TODO: See if we should move this somewhere safer.
    static {
        System.setProperty("sun.java2d.uiScale.enabled", "false");
    }

    protected final EventEmitter eventEmitter;
    // TODO: See if it would be better to move the use of RPA into Screen.
    protected final Robot robot;
    // TODO: Multiple screens?
    protected final Screen screen;

    protected volatile ScreenElement mainParentBoundariesElement;

    public static SUT getInstance() {
        if (instance == null) {
            synchronized (SUT.class) {
                if (instance == null) {
                    instance = new SUT();
                }
            }
        }
        return instance;
    }

    protected SUT() throws SUTRobotException {
        try {
            eventEmitter = new EventEmitter();
            robot = new Robot();
            screen = new Screen(this);
        } catch (AWTException e) {
            throw new SUTRobotException(e);
        }
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

    public ScreenElement getMainParentBoundariesElement() {
        return mainParentBoundariesElement;
    }

    public Screen getScreen() {
        return screen;
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

    public ScreenElement selectMainParentBoundariesElement() {
        if (mainParentBoundariesElement == null) {
            synchronized (this) {
                if (mainParentBoundariesElement == null) {
                    EventType endEventType = EventType.AFTER_SELECT_MAIN_BOUNDS;
                    ScreenElement created = null;
                    Rectangle rectangle = null;
                    RuntimeException error = null;
                    LocalDateTime startTime = LocalDateTime.now();
                    eventEmitter.emit(new Event<>(startTime, EventType.BEFORE_SELECT_MAIN_BOUNDS, new SelectMainBoundsData(screen)));
                    try {
                        created = screen.interactivelyCreateElement();
                        rectangle = created.getLastMatchLocation();
                        mainParentBoundariesElement = created;
                    } catch (RuntimeException exception) { // TODO: Check this exception type.
                        endEventType = EventType.SELECT_MAIN_BOUNDS_ERROR;
                        throw error = exception;
                    } finally {
                        eventEmitter.emit(new Event<>(startTime, LocalDateTime.now(), endEventType,
                                new SelectMainBoundsData(screen, rectangle, created), error));
                    }
                }
            }
        }
        return mainParentBoundariesElement;
    }

    public File saveFullScreenshot(String filename) {
        return saveScreenshot(screen.getBounds(), filename);
    }

    public File saveScreenshot(String filename) {
        return saveScreenshot(mainParentBoundariesElement == null ? screen.getBounds()
                : mainParentBoundariesElement.getLastMatchLocation(), filename);
    }

    public File saveScreenshot(Rectangle area, String filename) throws SavingScreenCaptureException {
        String fullPath = ConfigReader.getInstance().getScreenshotName(filename);
        File file;
        EventType endEventType = EventType.AFTER_SAVE_SCREENSHOT;
        BufferedImage image = null;
        SavingScreenCaptureException error = null;
        LocalDateTime startTime = LocalDateTime.now();
        eventEmitter.emit(new Event<>(startTime, EventType.BEFORE_SAVE_SCREENSHOT, new SaveScreenshotData(area, filename)));
        try {
            image = screen.capture(area);
            file = FileUtils.createFile(fullPath);
            ImageIO.write(image, "png", file);
        } catch (IOException e) {
            endEventType = EventType.SAVE_SCREENSHOT_ERROR;
            throw error = new SavingScreenCaptureException("There was a failure saving the following image: " + filename, e);
        } finally {
            eventEmitter.emit(new Event<>(startTime, LocalDateTime.now(), endEventType, new SaveScreenshotData(area, image, filename), error));
        }
        return file;
    }

    public void typeText(String text) {
        // TODO: Should we move this to the Screen class?
        EventType endEventType = EventType.AFTER_TYPING;
        RuntimeException error = null;
        LocalDateTime startTime = LocalDateTime.now();
        eventEmitter.emit(new Event<>(startTime, EventType.BEFORE_TYPING, new TypingData(text)));
        try {
            for (char c : text.toCharArray()) {
                robot.keyPress(java.awt.event.KeyEvent.getExtendedKeyCodeForChar(c));
                robot.keyRelease(java.awt.event.KeyEvent.getExtendedKeyCodeForChar(c));
            }
        } catch (RuntimeException throwable) { // TODO: Check.
            endEventType = EventType.TYPING_ERROR;
            throw error = throwable;
        } finally {
            eventEmitter.emit(new Event<>(startTime, LocalDateTime.now(), endEventType, new TypingData(text), error));
        }
    }
}
