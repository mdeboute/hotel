package hotelproject.controllers.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.Properties;

public class ConfigManager {
    private String configPath;

    public ConfigManager(String configPath) {
        this.configPath = configPath;
    }

    public String getConfigPath() {
        return configPath;
    }

    public void setConfigPath(String configPath) {
        this.configPath = configPath;
    }

    /**
     *
     * @param key for getting the focal property's value.
     * @return the property return as a string.
     * @brief According a file path to get configuration property.
     */
    public String getPValue(String key) {
        Properties prop = new Properties();
        InputStream is = null;
        try {
            is = new FileInputStream(new File(configPath).getAbsolutePath());
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        try {
            prop.load(is);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return prop.getProperty(key);
    }

    /**
     * @param keys All the keys for getting the values.
     * @return All the values will saved in ArrayList.
     * @brief According several file paths to get several configuration properties.
     */
    public ArrayList<String> getPValue(ArrayList<String> keys) {
        Properties prop = new Properties();
        InputStream is = null;
        ArrayList<String> values = new ArrayList<>();
        try {
            is = new FileInputStream(configPath);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        try {
            prop.load(is);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        for (String key : keys) {
            values.add(prop.getProperty(key));
        }

        return values;
    }
}
