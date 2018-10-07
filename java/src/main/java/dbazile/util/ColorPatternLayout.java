package dbazile.util;

import org.apache.log4j.PatternLayout;
import org.apache.log4j.spi.LoggingEvent;

public final class ColorPatternLayout extends PatternLayout {
    private boolean decolorize = false;

    @Override
    public String format(LoggingEvent event) {
        return decolorize
                ? AnsiColor.decolorize(super.format(event))
                : AnsiColor.colorize(super.format(event));
    }

    public void setDecolorize(boolean value) {
        this.decolorize = value;
    }
}
