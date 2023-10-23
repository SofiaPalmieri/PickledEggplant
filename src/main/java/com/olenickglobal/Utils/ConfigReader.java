package com.olenickglobal.Utils;

import com.google.gson.Gson;
import com.olenickglobal.Exceptions.FailureToAddRuntimeProperties;
import com.olenickglobal.Exceptions.FailureToAddStaticConfig;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ConfigReader {

    private static ConfigReader instance;

    Map<String,String> configurations = new HashMap<>();

    private ConfigReader() {
        addStaticConfigs();
        addRuntimeConfigs();
    }

    private void addStaticConfigs() {
        Gson gson = new Gson();
        try(BufferedReader br = new BufferedReader(new FileReader(this.readConfig(Configs.CONFIG_FILE)))){
            this.configurations = gson.fromJson(br, Map.class);
        } catch (IOException e) {
            throw new FailureToAddStaticConfig(e);
        }
    }

    private void addRuntimeConfigs(){
        File properties = new File(this.readConfig(Configs.RUNTIME_PROPERTIES_FILE));
        if (properties.exists()){
            try(BufferedReader br = new BufferedReader(new FileReader(properties))){
                String line;
                while ((line = br.readLine()) != null){
                    String[] keyValue = line.split("=");
                    this.configurations.put(keyValue[0],keyValue[1]);
                }
            } catch (IOException e) {
                throw new FailureToAddRuntimeProperties(e);
            }
        }
    }


    public static ConfigReader getInstance() {
        if (instance == null) {
            instance = new ConfigReader();
        }
        return instance;
    }

    public String getImageName(String imageName) {
        return readConfig(Configs.IMAGES_PATH) + "/" + imageName;
    }

    public String getScreenshotName(String screenshotName) {
        return readConfig(Configs.SCREENSHOTS_PATH) + "/" + screenshotName;
    }

    public enum Configs {
        IMAGES_PATH,
        EXCEL_PATH,
        TESSERACT_DATA_PATH,
        SCREENSHOTS_PATH,
        CONFIG_FILE,
        RUNTIME_PROPERTIES_FILE,
        TESTNG_XML_FILE,
        FEATURE_LIST_FILE;

    }

     public String readConfig(Configs configName) {
       return readConfig(configName.name());
    }

    public String readConfig(String configName) {
        String configValue = null;
        if (System.getProperty(configName) != null){
            configValue = System.getProperty(configName);
        } else if (this.configurations.get(configName) != null){
            configValue = this.configurations.get(configName);
        } else {
            throw new RuntimeException("Config " + configName + " not found");
        }
        return configValue;
    }


    public ITesseract getTesseract(){
        ITesseract iTesseract =  new Tesseract();
        iTesseract.setDatapath(readConfig(Configs.valueOf(Configs.TESSERACT_DATA_PATH.name())));
        iTesseract.setLanguage("eng");
        iTesseract.setVariable("user_defined_dpi", "96");
        iTesseract.setVariable("tessedit_pageseg_mode", "11");
        return  iTesseract;
    }

}
