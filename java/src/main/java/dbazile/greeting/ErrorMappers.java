package dbazile.greeting;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ErrorMappers {
    private static final Logger LOG = LoggerFactory.getLogger(ErrorMappers.class);

    @Provider
    public static class NotFound implements ExceptionMapper<NotFoundException> {
        public NotFound() {
            LOG.info("instantiate {}", getClass().getSimpleName());
        }

        @Override
        public Response toResponse(NotFoundException exception) {
            return Response
                    .status(404)
                    .entity(Map.of("error", "lolwut"))
                    .type(MediaType.APPLICATION_JSON_TYPE)
                    .build();
        }
    }

    @Provider
    public static class EverythingElse implements ExceptionMapper<Exception> {
        public EverythingElse() {
            LOG.info("instantiate {}", getClass().getSimpleName());
        }

        @Override
        public Response toResponse(Exception e) {
            LOG.info("toResponse: {}", e);
            final StringWriter stringWriter = new StringWriter();
            e.printStackTrace(new PrintWriter(stringWriter));
            return Response
                    .status(400)
                    .entity(Map.of("error", e.getMessage(), "trace", stringWriter.toString()))
                    .type(MediaType.APPLICATION_JSON_TYPE)
                    .build();
        }
    }
}
