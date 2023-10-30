package com.olenickglobal.Utils;

import com.olenickglobal.Exceptions.FailureToAddRuntimeProperties;
import com.olenickglobal.Exceptions.FailureToAddStaticConfig;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;


public class ConfigReader {

    public interface JsonSupportedType {
    }

    public enum SupportedTypes implements JsonSupportedType {
        STRING,
        INTEGER,
        BOOLEAN,
        DOUBLE,
        LONG,

        JSON_ARRAY,
        JSON_OBJECT;

    }


    private final String STATIC_CONFIG_FILE_PROPERTY = "STATIC_CONFIG_FILE";
    private final String RUNTIME_CONFIG_FILE_PROPERTY = "RUNTIME_CONFIG_FILE";

    private static ConfigReader instance;

    private final JSONObject configurations;


    private ConfigReader() {
        this.configurations = new JSONObject();
        JSONObject staticConfigs = getStaticConfigs();
        JSONObject runtimeConfigs = getRuntimeConfigs();
        JSONObject systemConfigs = getSystemConfigs();
        addConfigs(staticConfigs);
        addConfigs(runtimeConfigs);
        addConfigs(systemConfigs);
    }

    private void addConfigs(JSONObject configs){
        for (String key : configs.keySet()){
            this.configurations.put(key,configs.get(key));
        }
    }


    private JSONObject getSystemConfigs() {
        JSONObject object = new JSONObject();
        Properties systemProperties = System.getProperties();
        Iterator<Object> iterator = systemProperties.keys().asIterator();
        while (iterator.hasNext()) {
            String obj = (String) iterator.next();
            if (obj.endsWith("+")) {
                JSONArray array =  this.readConfig(obj,SupportedTypes.JSON_ARRAY);
                array.put(systemProperties.get(obj));
                object.put(obj,array);
            } else {
                object.put(obj, systemProperties.getProperty(obj));
            }
        }
        return object;
    }

    private JSONObject getStaticConfigs() {
        JSONObject object;
        try {
            String data = new String(Files.readAllBytes(Paths.get(System.getProperty(this.STATIC_CONFIG_FILE_PROPERTY))));
            object = new JSONObject(data);
        } catch (IOException e) {
            throw new FailureToAddStaticConfig(e);
        }
        return object;
    }

    private JSONObject getRuntimeConfigs() {
        JSONObject object;
        try {
            String data = new String(Files.readAllBytes(Paths.get(System.getProperty(this.RUNTIME_CONFIG_FILE_PROPERTY))));
            object = new JSONObject(data);
        } catch (IOException e) {
            throw new FailureToAddStaticConfig(e);
        }
        return object;
    }


    public static ConfigReader getInstance() {
        if (instance == null) {
            instance = new ConfigReader();
        }
        return instance;
    }

    public String getImageName(String imageName) {
        return readConfig(Configs.IMAGES_PATH,SupportedTypes.STRING) + "/" + imageName;
    }

    public String getScreenshotName(String screenshotName) {
        return readConfig(Configs.SCREENSHOTS_PATH,SupportedTypes.STRING) + "/" + screenshotName;
    }

    public enum Configs {
        IMAGES_PATH,
        EXCEL_PATH,
        TESSERACT_DATA_PATH,
        SCREENSHOTS_PATH,
        TESTNG_XML_FILE,
        FEATURE_LIST_FILE,
        REPORTERS;

    }

    public <T> T readConfig(Configs configName,SupportedTypes type) {
        return readConfig(configName.name(),type);
    }

    public <T> T readConfig(String config, SupportedTypes type){
        return switch (type) {
            case STRING -> (T) this.configurations.getString(config);
            case INTEGER -> (T) (Integer) this.configurations.getInt(config);
            case BOOLEAN -> (T) (Boolean) this.configurations.getBoolean(config);
            case DOUBLE -> (T) (Double) this.configurations.getDouble(config);
            case LONG -> (T) (Long) this.configurations.getLong(config);
            case JSON_OBJECT -> (T) this.configurations.getJSONObject(config);
            case JSON_ARRAY -> (T) this.configurations.getJSONArray(config);
            default -> throw new UnsupportedTypeException();
        };
    }



    public ITesseract getTesseract() {
        ITesseract iTesseract = new Tesseract();
        iTesseract.setDatapath(this.readConfig(Configs.valueOf(Configs.TESSERACT_DATA_PATH.name()),SupportedTypes.STRING));
        iTesseract.setLanguage("eng");
        iTesseract.setVariable("user_defined_dpi", "96");
        iTesseract.setVariable("tessedit_pageseg_mode", "11");
        return iTesseract;
    }

}
