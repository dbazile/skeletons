package dbazile.greeting;

import java.time.Instant;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlRootElement;

@Path("/")
public class GreetingResource {

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
    public Greeting greet(@QueryParam("name") @DefaultValue("World") String name) {
        return new Greeting("hi there " + name);
    }

    @XmlRootElement
    static class Greeting {
        private final String data;
        private final Instant timestamp;

//        public Greeting() {
//        }

        public Greeting(String data) {
            this.data = data;
            this.timestamp = Instant.now();
        }

//        public Greeting(String data, Instant timestamp) {
//            this.data = data;
//            this.timestamp = timestamp;
//        }

        public String getData() {
            return data;
        }

        public String getTimestamp() {
            return timestamp.toString();
        }

//        public void setData(String data) {
//            this.data = data;
//        }
//
//        public void setTimestamp(Instant timestamp) {
//            this.timestamp = timestamp;
//        }
    }
}
