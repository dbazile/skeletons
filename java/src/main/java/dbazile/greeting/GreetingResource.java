package dbazile.greeting;

import java.time.Instant;
import java.util.Date;
import javax.inject.Inject;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import dbazile.config.Config;

@Path("/")
public class GreetingResource {
    private final Config config;

    @Inject
    public GreetingResource(Config config) {
        this.config = config;
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Greeting greet(@QueryParam("name") @DefaultValue("World") String name) {
        return new Greeting("Hi there " + name, config.getColor());
    }

    @XmlRootElement
    @XmlAccessorType(XmlAccessType.FIELD)
    static class Greeting {
        private final String color;
        private final String data;

        @XmlJavaTypeAdapter(JaxbInstantAdapter.class)
        private final Instant timestamp;

        private Greeting() {
            this.color = null;
            this.data = null;
            this.timestamp = null;
        }

        public Greeting(String data, String color) {
            this.color = color;
            this.data = data;
            this.timestamp = Instant.now();
        }

        public String getColor() {
            return color;
        }

        public String getData() {
            return data;
        }

        public String getTimestamp() {
            return timestamp.toString();
        }
    }

    public static class JaxbInstantAdapter extends XmlAdapter<Date, Instant> {

        @Override
        public Date marshal(Instant instant) {
            return Date.from(instant);
        }

        @Override
        public Instant unmarshal(Date date) {
            return date.toInstant();
        }
    }
}
