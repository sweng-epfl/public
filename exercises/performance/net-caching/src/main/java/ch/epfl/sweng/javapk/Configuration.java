package ch.epfl.sweng.javapk;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class Configuration {
    private static final Properties defaultProperties;
    private static final File configFile = new File("javapk.properties");

    static {
        defaultProperties = new Properties();
        defaultProperties.setProperty("mirror", "http://dl-cdn.alpinelinux.org/alpine/v3.12/%repository%/%architecture%/");
        defaultProperties.setProperty("architecture", "x86_64");
        defaultProperties.setProperty("repositories", "main,community");
    }

    private final Properties properties = new Properties(defaultProperties);

    public Configuration() {
        if (configFile.exists()) {
            try {
                properties.load(new FileReader(configFile));
            } catch (IOException e) {
                System.out.println("Could not load configuration file.");
                e.printStackTrace();
            }
        }
    }

    /**
     * @return the base URL of the mirror to use
     */
    public String getMirror() {
        return properties.getProperty("mirror");
    }

    /**
     * @return the processor architecture currently in use
     */
    public String getArchitecture() {
        return properties.getProperty("architecture");
    }

    /**
     * @return the list of repositories names to use (example: [core, community])
     */
    public List<String> getRepositories() {
        String[] repos = properties.getProperty("repositories").split(",");
        return Arrays.asList(repos);
    }
}
