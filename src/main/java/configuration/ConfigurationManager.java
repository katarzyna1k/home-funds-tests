package configuration;

import java.io.InputStream;
import java.util.Properties;

public class ConfigurationManager {

    private static final Properties PROPERTIES = loadProperties();
    private static final String APP_URL = "app.url";
    private static final String APPLICATION_PROPERTIES_FILE_NAME = "application.properties";

    private ConfigurationManager() {
    }

    private static Properties loadProperties() {
        Properties properties = new Properties();
        try {
            InputStream inputStream = ConfigurationManager.class.getClassLoader()
                    .getResourceAsStream(APPLICATION_PROPERTIES_FILE_NAME);{

                if (inputStream == null) {
                    throw new IllegalStateException("Configuration file not found: " + APPLICATION_PROPERTIES_FILE_NAME);
                }
            }
            properties.load(inputStream);

        } catch (Exception e) {
            throw new RuntimeException("Failed to load configuration file: " + APPLICATION_PROPERTIES_FILE_NAME);
        }
        return properties;
    }

    public static String getUrl() {
        return PROPERTIES.getProperty(APP_URL);
    }
}
