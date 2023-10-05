package com.olenickglobal.Utils;

import com.google.gson.Gson;
import net.sourceforge.tess4j.ITessAPI;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ConfigReader {
     public String readConfig(String configName) {
        Gson gson = new Gson();
        Map<String,String> configMap = new HashMap<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(this.getConfigPath()));
            configMap = gson.fromJson(br, Map.class);
            br.close();
            return configMap.get(configName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    public ITesseract getTesseract(){
        ITesseract iTesseract =  new Tesseract();
        iTesseract.setDatapath(new ConfigReader().readConfig("TESSERACT_DATA_PATH"));
        iTesseract.setLanguage("eng");
        iTesseract.setVariable("user_defined_dpi", "96");
        iTesseract.setVariable("tessedit_pageseg_mode", "11");
        return  iTesseract;
    }

    public String getConfigPath() {
        return System.getProperty("config_file");
    }

}
