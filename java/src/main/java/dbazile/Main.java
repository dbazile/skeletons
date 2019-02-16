package dbazile;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.Slf4jRequestLog;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.jetty.JettyHttpContainer;
import org.glassfish.jersey.server.ContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dbazile.config.Config;

public final class Main {
    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        final Config config = Config.getInstance();

        final Server server = new Server(config.getBindAddr());

        // Enable request logging
        server.setRequestLog(new Slf4jRequestLog());

        // Attach application handler
        final AppConfig appConfig = new AppConfig(config);
        server.setHandler(ContainerFactory.createContainer(JettyHttpContainer.class, appConfig));

        try {
            server.start();
            server.join();
        }
        catch (Exception e) {
            LOG.error("Oh no", e);
            System.exit(1);
        }
    }

    private static class AppConfig extends ResourceConfig {
        AppConfig(Config config) {
            // Point at JAX-RS resource package
            packages("dbazile.greeting");

            // Configure dependency injection
            register(new AbstractBinder() {
                @Override
                protected void configure() {
                    bind(config).to(Config.class);
                }
            });
        }
    }
}
