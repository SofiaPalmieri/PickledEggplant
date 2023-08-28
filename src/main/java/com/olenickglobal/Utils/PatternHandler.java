package com.olenickglobal.Utils;

import org.sikuli.script.Pattern;

import java.io.File;

public class PatternHandler{

    public Pattern handlePattern(String imageName){
        return new Pattern(getPatternFromLocalFile(imageName));
    }

    @SuppressWarnings("ConstantConditions")
    public String getPatternFromLocalFile(String imageName) {
        /*
            return new File(getClass().getClassLoader().getResource("images/" + imageName).toURI()).getAbsolutePath();
            */
        return new File("C:/Users/sofia/OneDrive - UTN.BA/PC/OneDrive - UTN.BA/Test/Images/" + imageName).getAbsolutePath();
    }

}
