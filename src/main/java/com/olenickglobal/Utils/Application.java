package com.olenickglobal.Utils;


public class Application {

    private final String name;
    private final String version;

    public Application(String name, String version){
        this.name = name;
        this.version = version;
    }

    public Application(String name){
        this.name = name;
        this.version = "Unknown";
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }
}
