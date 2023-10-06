package com.olenickglobal.Utils;

public class Main {

    public static void main(String[] args) {
        System.setProperty("config_file","C:/Users/lauta/OneDrive/Escritorio/Qualitest/config.json");
        System.out.println(new ConfigReader().readConfig(ConfigReader.Configs.IMAGES_PATH));
       }
}
