package dbazile;

import dbazile.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Main {
    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        try {
            final Config config = Config.getInstance();

            LOG.info("[GREEN {} (waitTime={})]", config.getGreeting(), config.getWaitTime());

            Thread.sleep(config.getWaitTime().toMillis());

            LOG.info(config.getFarewell());
        }
        catch (InterruptedException ignored) {
            System.exit(1);
        }
        catch (RuntimeException e) {
            LOG.error("oh no", e);
            System.exit(1);
        }
    }
}
