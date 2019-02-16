package dbazile.config;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Config {
    private static final Logger LOG = LoggerFactory.getLogger(Config.class);

    private static final String PROPERTY_OVERRIDE_PATH = "config_path";
    private static final String RESOURCE_PATH = "config.properties";

    private static final String KEY_COLOR = "myproject.color";
    private static final String KEY_HOST = "myproject.host";
    private static final String KEY_PORT = "myproject.port";

    private static Config INSTANCE = new Config();

    private final InetSocketAddress addr;
    private final String color;

    private Config() {
        final Properties props = loadProperties();

        this.addr = new InetSocketAddress(readString(props, KEY_HOST), readInt(props, KEY_PORT));
        this.color = readString(props, KEY_COLOR);
    }

    public static Config getInstance() throws ConfigException {
        return INSTANCE;
    }

    public InetSocketAddress getBindAddr() {
        return addr;
    }

    public String getColor() {
        return color;
    }

    private static Properties loadProperties() {
        final Properties properties = new Properties();

        LOG.info("Reading config from classpath '{}'", RESOURCE_PATH);

        // Read configuration defaults
        try (final InputStream stream = Config.class.getClassLoader().getResourceAsStream(RESOURCE_PATH)) {
            properties.load(stream);
        }
        catch (IOException e) {
            throw new ConfigException("could not read resource '" + RESOURCE_PATH + "'", e);
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
                throw new ConfigException("could not read '" + path + "': ", e);
            }

            properties.putAll(overrides);
        }

        return properties;
    }

    private static int readInt(Properties props, String key) {
        try {
            return Integer.valueOf(readString(props, key));
        }
        catch (NumberFormatException e) {
            throw new ConfigException("invalid integer at property '" + key + "': " + e.getMessage(), e);
        }
    }

    private static String readString(Properties props, String key) {
        final String value = System.getProperty(key, props.getProperty(key, ""));

        if (value.trim().isEmpty()) {
            throw new ConfigException("missing value for '" + key + "'");
        }

        return value.trim();
    }

    public static final class ConfigException extends RuntimeException {
        ConfigException(String message) {
            super(message);
        }

        ConfigException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
