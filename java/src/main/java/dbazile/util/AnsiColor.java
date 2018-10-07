package dbazile.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class AnsiColor {
    private static final Pattern PATTERN = Pattern.compile("\\[(?<color>RED|GREEN|YELLOW|BLUE|MAGENTA|CYAN|GREY) (?<value>[^]]+)]");
    private static final Map<String, String> MAPPING = createMapping();

    private static final String RESET   = "\u001B[0m";
    private static final String RED     = "\u001B[31m";
    private static final String GREEN   = "\u001B[32m";
    private static final String YELLOW  = "\u001B[33m";
    private static final String BLUE    = "\u001B[34m";
    private static final String MAGENTA = "\u001B[35m";
    private static final String CYAN    = "\u001B[36m";
    private static final String GREY    = "\u001B[37m";

    public static String decolorize(String input) {
        if (input.indexOf('[') == -1) {
            return input;
        }

        return PATTERN.matcher(input).replaceAll("$2");
    }

    public static String colorize(String input) {
        if (input.indexOf('[') == -1) {
            return input;
        }

        final Matcher m = PATTERN.matcher(input);
        int offset = 0;
        final StringBuilder sb = new StringBuilder();
        while (m.find()) {
            sb
                    .append(input, offset, m.start())
                    .append(MAPPING.get(m.group("color")))
                    .append(m.group("value"))
                    .append(RESET);
            offset = m.end();
        }

        // Add the remaining parts
        sb.append(input.substring(offset));

        return sb.toString();
    }

    private static Map<String, String> createMapping() {
        final Map<String, String> mapping = new HashMap<>();

        mapping.put("RESET", RESET);
        mapping.put("RED", RED);
        mapping.put("GREEN", GREEN);
        mapping.put("YELLOW", YELLOW);
        mapping.put("BLUE", BLUE);
        mapping.put("MAGENTA", MAGENTA);
        mapping.put("CYAN", CYAN);
        mapping.put("GREY", GREY);

        return Collections.unmodifiableMap(mapping);
    }
}
