package dbazile.config;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.format.DateTimeParseException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Config {
    private static final Logger LOG = LoggerFactory.getLogger(Config.class);

    private static final String PROPERTY_OVERRIDE_PATH = "config_path";
    private static final String RESOURCE_PATH = "config.properties";

    private static final String KEY_GREETING = "myproject.greeting";
    private static final String KEY_FAREWELL = "myproject.farewell";
    private static final String KEY_WAIT_TIME = "myproject.wait_time";

    private static Config INSTANCE;

    private final String farewell;
    private final String greeting;
    private final Duration waitTime;

    private Config() {
        final Properties props = loadProperties();

        this.waitTime = readDuration(props, KEY_WAIT_TIME);
        this.greeting = readString(props, KEY_GREETING);
        this.farewell = readString(props, KEY_FAREWELL);
    }

    public static synchronized Config getInstance() throws Error {
        if (INSTANCE == null) {
            INSTANCE = new Config();
        }

        return INSTANCE;
    }

    public String getFarewell() {
        return farewell;
    }

    public String getGreeting() {
        return greeting;
    }

    public Duration getWaitTime() {
        return waitTime;
    }

    private static Properties loadProperties() {
        final Properties properties = new Properties();

        LOG.info("Reading config from classpath '{}'", RESOURCE_PATH);

        // Read configuration defaults
        try (final InputStream stream = Config.class.getClassLoader().getResourceAsStream(RESOURCE_PATH)) {
            properties.load(stream);
        }
        catch (IOException e) {
            throw new Error("could not read resource '" + RESOURCE_PATH + "'", e);
        }

        // Apply configuration overrides
        final String overridePath = System.getProperty(PROPERTY_OVERRIDE_PATH);
        if (overridePath != null) {
            final Properties overrides = new Properties();
            final Path path = Paths.get(overridePath);

            LOG.info("Reading config overrides from '{}'", path.toAbsolutePath());

            try (final InputStream stream = Files.newInputStream(path)) {
                overrides.load(stream);
            }
            catch (NoSuchFileException e) {
                LOG.warn("Override file '{}' not found", overridePath);
            }
            catch (IOException e) {
                throw new Error("could not read '" + path + "': ", e);
            }

            properties.putAll(overrides);
        }

        return properties;
    }

    private static Duration readDuration(Properties props, String key) {
        try {
            return Duration.parse(readString(props, key));
        }
        catch (DateTimeParseException e) {
            throw new Error("invalid duration at property '" + key + "': " + e.getMessage(), e);
        }
    }

    private static String readString(Properties props, String key) {
        final String value = System.getProperty(key, props.getProperty(key));

        if (value == null || value.trim().equals("")) {
            throw new Error("missing value for '" + key + "'");
        }

        return value.trim();
    }

    public static final class Error extends RuntimeException {
        Error(String message) {
            super(message);
        }

        Error(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
