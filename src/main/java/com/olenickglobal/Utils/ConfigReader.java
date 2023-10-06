package com.olenickglobal.Utils;

import com.google.gson.Gson;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ConfigReader {

    public String getImageName(String imageName) {
        return readConfig(Configs.IMAGES_PATH) + "//" + imageName;
    }

    public enum Configs {
        IMAGES_PATH,
        EXCEL_PATH,
        TESSERACT_DATA_PATH,
        SCREENSHOTS_PATH

    }

     public String readConfig(Configs configName) {
        Gson gson = new Gson();
        Map<String,String> configMap = new HashMap<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(this.getConfigPath()));
            configMap = gson.fromJson(br, Map.class);
            br.close();
            return configMap.get(configName.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    public ITesseract getTesseract(){
        ITesseract iTesseract =  new Tesseract();
        iTesseract.setDatapath(new ConfigReader().readConfig(Configs.valueOf(Configs.TESSERACT_DATA_PATH.name())));
        iTesseract.setLanguage("eng");
        iTesseract.setVariable("user_defined_dpi", "96");
        iTesseract.setVariable("tessedit_pageseg_mode", "11");
        return  iTesseract;
    }

    public String getConfigPath() {
        return System.getProperty("config_file");
    }

}
