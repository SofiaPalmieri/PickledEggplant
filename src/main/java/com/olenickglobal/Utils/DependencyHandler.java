package com.olenickglobal.Utils;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import org.sikuli.script.Screen;

import java.net.URL;


public class DependencyHandler {

    private static DependencyHandler instance;


    static public DependencyHandler getInstance() {
        if (instance == null) {
            instance = new DependencyHandler();
        }
        return instance;
    }


    public ITesseract getTesseract(){
        ITesseract iTesseract =  new Tesseract();
        iTesseract.setDatapath("C:/Users/sofia/OneDrive - UTN.BA/PC/OneDrive - UTN.BA/Qualitest/tessdata");
        iTesseract.setLanguage("eng");
        iTesseract.setVariable("user_defined_dpi", "96");
        iTesseract.setVariable("tessedit_pageseg_mode", "11");
        return  iTesseract;
    }











}
