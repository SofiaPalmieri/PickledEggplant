package com.olenickglobal.Utils;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import org.sikuli.script.Screen;

import java.net.URL;


public class DependencyHandler {

    private static DependencyHandler instance;

    Screen screen;
    PatternHandler patternHandler;

    static public DependencyHandler getInstance() {
        if (instance == null) {
            instance = new DependencyHandler();
        }
        return instance;
    }

    public void loadDependencies(){
        nu.pattern.OpenCV.loadLocally();
    }

    public String getImage(String name) throws FindFailedHandler {
        return this.getFullPath(name + ".png");
    }

    public ITesseract getTesseract(){
        ITesseract iTesseract =  new Tesseract();
        iTesseract.setDatapath("C:/Users/sofia/OneDrive - UTN.BA/PC/OneDrive - UTN.BA/Qualitest/tessdata");
        iTesseract.setLanguage("eng");
        iTesseract.setVariable("user_defined_dpi", "96");
        iTesseract.setVariable("tessedit_pageseg_mode", "11");
        return  iTesseract;
    }

    public String getFullPath(String content) throws FindFailedHandler {
        ClassLoader classLoader = ScreenSection.class.getClassLoader();
        URL resource = classLoader.getResource(content);
        System.out.println(resource);
        if (resource != null) {
            return resource.getPath();
        } else {
            throw new FindFailedHandler(screen, patternHandler);
        }
    }

    private DependencyHandler(){
    }








}
