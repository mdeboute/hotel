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
     * You should not use this method, use this
     * @see ConfigManager#getPValue(java.lang.String) instead
     */
    @Deprecated
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
