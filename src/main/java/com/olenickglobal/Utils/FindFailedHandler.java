package com.olenickglobal.Utils;

import org.sikuli.script.FindFailed;
import org.sikuli.script.Image;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;

import javax.swing.*;
import java.io.IOException;

public class FindFailedHandler extends Throwable {

    Screen screen;

    PatternHandler patternHandler = new PatternHandler();

    public FindFailedHandler(Screen screen, PatternHandler patternHandler) {
        this.screen = screen;
        this.patternHandler = patternHandler;
    }


    public void handleFinds(String imageName) throws IOException, FindFailed, ClassNotFoundException {

        try {
            Pattern image = patternHandler.handlePattern(imageName);
            screen.click(image);
        } catch (FindFailed e) {
            String[] options = new String[]{"Try again", "Cancel"};

            int optionPane = JOptionPane.showOptionDialog(null,
                    "Image " + imageName + " was not found",
                    "Find Failed",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    options,
                    options[0]);

            if (optionPane == 0){
                //Settings.setImageCache(0);
                //ImagePath.reset(patternHandler.getPatternFromLocalFile(imageName));
                Image.unCache(patternHandler.getPatternFromLocalFile(imageName));
                Pattern image = patternHandler.handlePattern(imageName);
                screen.click(image);
                //Settings.setImageCache(64);
            }
            else{
                throw  new FindFailed("Find Failed");
            }

        }
    }


}
