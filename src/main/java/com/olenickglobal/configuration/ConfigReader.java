package com.olenickglobal.configuration;

import com.olenickglobal.exceptions.ConfigParamNotFoundError;
import com.olenickglobal.exceptions.ConfigurationError;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Properties;

public class ConfigReader {
    public enum ConfigParam {
        IMAGES_PATH,
        EXCEL_PATH,
        TESSERACT_DATA_PATH,
        SCREENSHOTS_PATH,
        TESTNG_XML_FILE,
        FEATURE_LIST_FILE,
        REPORTERS
    }

    public enum SupportedType {
        STRING,
        INTEGER,
        BOOLEAN,
        DOUBLE,
        LONG,
        JSON_ARRAY,
        JSON_OBJECT
    }

    private static volatile ConfigReader instance;
    private static final String STATIC_CONFIG_FILE_PROPERTY = "STATIC_CONFIG_FILE";
    private static final String RUNTIME_CONFIG_FILE_PROPERTY = "RUNTIME_CONFIG_FILE";

    private final JSONObject configurations;

    public static ConfigReader getInstance() {
        if (instance == null) {
            synchronized (ConfigReader.class) {
                if (instance == null) {
                    instance = new ConfigReader();
                }
            }
        }
        return instance;
    }

    private ConfigReader() {
        this.configurations = new JSONObject();
        JSONObject staticConfigs = getStaticConfigs();
        JSONObject runtimeConfigs = getRuntimeConfigs();
        JSONObject systemConfigs = getSystemConfigs();
        addConfigs(staticConfigs);
        addConfigs(runtimeConfigs);
        addConfigs(systemConfigs);
    }

    public String getImageName(String imageName) {
        return readConfig(ConfigParam.IMAGES_PATH, SupportedType.STRING) + "/" + imageName;
    }

    public String getScreenshotName(String screenshotName) {
        return readConfig(ConfigParam.SCREENSHOTS_PATH, SupportedType.STRING) + "/" + screenshotName;
    }

    public ITesseract getTesseract() {
        ITesseract iTesseract = new Tesseract();
        iTesseract.setDatapath(this.readConfig(ConfigParam.valueOf(ConfigParam.TESSERACT_DATA_PATH.name()), SupportedType.STRING));
        iTesseract.setLanguage("eng");
        iTesseract.setVariable("user_defined_dpi", "96");
        iTesseract.setVariable("tessedit_pageseg_mode", "11");
        return iTesseract;
    }

    public <T> T readConfig(ConfigParam configName, SupportedType type) {
        return readConfig(configName.name(), type);
    }

    public <T> T readConfig(String config, SupportedType type) {
        try {
            return switch (type) {
                case STRING -> (T) this.configurations.getString(config);
                case INTEGER -> (T) (Integer) this.configurations.getInt(config);
                case BOOLEAN -> (T) (Boolean) this.configurations.getBoolean(config);
                case DOUBLE -> (T) (Double) this.configurations.getDouble(config);
                case LONG -> (T) (Long) this.configurations.getLong(config);
                case JSON_OBJECT -> (T) this.configurations.getJSONObject(config);
                case JSON_ARRAY -> (T) this.configurations.getJSONArray(config);
            };
        } catch (JSONException e) {
            throw new ConfigParamNotFoundError(config);
        }
    }

    private void addConfigs(JSONObject configs) {
        for (String key : configs.keySet()) {
            this.configurations.put(key, configs.get(key));
        }
    }

    @SuppressWarnings("DataFlowIssue")
    private JSONObject getRuntimeConfigs() {
        JSONObject object;
        try {
            // TODO: Would it make sense to move this piece of code dealing with a default filename to FileUtils?
            String runtimeConfigResource = this.getClass().getClassLoader().getResource("runtime.json").getPath();
            String runtimeConfigFilename = System.getProperty(RUNTIME_CONFIG_FILE_PROPERTY, runtimeConfigResource.indexOf(':') > 0 ? runtimeConfigResource.substring(1) : runtimeConfigResource);
            String data = new String(Files.readAllBytes(Paths.get(runtimeConfigFilename)));
            object = new JSONObject(data);
        } catch (IOException e) {
            throw new ConfigurationError("Failure to add static configurations, remember to set -DRUNTIME_CONFIG_FILE in your vm options", e);
        } catch (JSONException e) {
            throw new ConfigurationError("Format error in static configuration. Must be JSON.", e);
        }
        return object;
    }

    @SuppressWarnings("DataFlowIssue")
    private JSONObject getStaticConfigs() {
        JSONObject object;
        try {
            // TODO: Would it make sense to move this piece of code dealing with a default filename to FileUtils?
            String staticConfigResource = this.getClass().getClassLoader().getResource("config.json").getPath();
            String staticConfigFilename = System.getProperty(STATIC_CONFIG_FILE_PROPERTY, staticConfigResource.indexOf(':') > 0 ? staticConfigResource.substring(1) : staticConfigResource);
            String data = new String(Files.readAllBytes(Paths.get(staticConfigFilename)));
            object = new JSONObject(data);
        } catch (IOException e) {
            throw new ConfigurationError("Failure to add static configurations, remember to set -DSTATIC_CONFIG_FILE in your vm options", e);
        } catch (JSONException e) {
            throw new ConfigurationError("Format error in static configuration. Must be JSON.", e);
        }
        return object;
    }

    private JSONObject getSystemConfigs() {
        JSONObject object = new JSONObject();
        Properties systemProperties = System.getProperties();
        Iterator<Object> iterator = systemProperties.keys().asIterator();
        while (iterator.hasNext()) {
            String obj = (String) iterator.next();
            if (obj.endsWith("+")) {
                JSONArray array = this.readConfig(obj, SupportedType.JSON_ARRAY);
                array.put(systemProperties.get(obj));
                object.put(obj, array);
            } else {
                object.put(obj, systemProperties.getProperty(obj));
            }
        }
        return object;
    }
}
