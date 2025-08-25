package com.qacart.todo.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

// Build a logic that reads the properties file
public class ConfigReader {

    private static Properties properties;

    // static initializer run the block only once for the whole project
    static {

        String filePath = "src/test/resources/todo.properties";
        FileInputStream input = null;
        try {
            input = new FileInputStream(filePath);
            properties = new Properties();
            properties.load(input);

        }catch(IOException e) {
            System.out.println("File not found");
        }finally {
            assert input != null;
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
    }

    public static String getPropertiesValue(String key) {
        return properties.getProperty(key);
    }

}
