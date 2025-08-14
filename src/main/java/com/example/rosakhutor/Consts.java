package com.example.rosakhutor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Consts {
    static Properties properties = new Properties();
    private static Properties getPropertiesObj(){
        try {
            properties.load(new FileInputStream(new File("app.properties")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return properties;
    }

    public static final String DB_HOST = getPropertiesObj().getProperty("HOST");
    public static final String DB_PORT = getPropertiesObj().getProperty("PORT");
    public static final String DB_NAME = getPropertiesObj().getProperty("DB_NAME");
    public static final String DB_USER = getPropertiesObj().getProperty("DB_USER");
    public static final String DB_PASS = getPropertiesObj().getProperty("DB_PASS");

}
