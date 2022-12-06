package config;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Config {
   /* public static final String CONFIG_LOCATION = Config.class.getClassLoader().
            getResource("config.properties").getFile();
    public static Properties getProperties() {
        Properties properties = new Properties();
        try {
            properties.load(new FileReader(CONFIG_LOCATION));
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Eroare la citirea fisierului de configurare!");

        }
        return properties;
    }*/
}
