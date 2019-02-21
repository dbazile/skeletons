package dbazile.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Unreasonable facsimile of Python stdlib's argparse module.
 * <p>
 * Apache Commons CLI is obviously the better choice but this is meant
 * to be a cheap way to get command line param handling via copy/paste.
 * Accordingly, more complex use cases like short flag combinations or
 * mutual exclusions aren't supported.
 */
public class ArgumentParser {
    public enum Action {
        APPEND,
        STORE_TRUE,
        STORE_FALSE,
        STORE_VALUE,
    }

    private static final Logger LOG = LoggerFactory.getLogger(ArgumentParser.class);

    private final Map<String, Argument> flags;
    private final List<Argument> positionals;
    private final Set<String> positionalsNames;

    public ArgumentParser() {
        this.flags = new HashMap<>();
        this.positionals = new ArrayList<>();
        this.positionalsNames = new HashSet<>();
    }

    public ArgumentParser add(String name) {
        return add(name, Action.STORE_VALUE);
    }

    public ArgumentParser add(String name, String target) {
        return add(name, target, Action.STORE_VALUE);
    }

    public ArgumentParser add(String name, Action action) {
        return add(name, toTarget(name), action);
    }

    public ArgumentParser add(String name, String target, Action action) {
        final Argument a = new Argument();
        a.name = required(name, "name is required");
        a.target = required(target, "target is required");
        a.action = required(action, "action is required");

        if (a.name.startsWith("-")) {
            if (flags.containsKey(a.name)) {
                throw new ProgrammerException("duplicate flag '" + a.name + "'");
            }
            flags.put(a.name, a);
        }
        else {
            if (a.action != Action.STORE_VALUE && a.action != Action.APPEND) {
                throw new ProgrammerException("invalid action for positional '" + a.name + "': " + a.action);
            }

            if (positionalsNames.contains(a.name)) {
                throw new ProgrammerException("duplicate positional '" + a.name + "'");
            }

            if (!positionals.isEmpty() && positionals.get(positionals.size() - 1).action == Action.APPEND) {
                throw new ProgrammerException("cannot add positionals after a positional appender");
            }

            positionalsNames.add(a.name);
            positionals.add(a);
        }

        return this;
    }

    public String usage(String entrypoint) {
        final StringBuilder sb = new StringBuilder();

        sb.append("Usage: ");
        sb.append(entrypoint);

        sb.append(" [-h | --help]");

        for (Argument argument : flags.values()) {
            sb.append(" [");
            sb.append(argument.name);
            if (argument.action != Action.STORE_FALSE && argument.action != Action.STORE_TRUE) {
                sb.append("=VALUE");
            }
            sb.append("]");
        }

        for (Argument argument : positionals) {
            sb.append(" ");
            sb.append(argument.name.toUpperCase());
            if (argument.action == Action.APPEND) {
                sb.append("...");
            }
        }

        return sb.toString();
    }

    public Params parse(String[] args) throws HelpRequestedException {
        LOG.debug("All arguments: {}", (Object) args);

        // Check for help request
        for (String arg : args) {
            if (arg.equalsIgnoreCase("-h") || arg.equalsIgnoreCase("--help")) {
                throw new HelpRequestedException();
            }
        }

        final Params params = new Params();
        final Iterator<Argument> positionalIterator = positionals.iterator();
        Argument deferredFlag = null;
        Argument deferredPositional = null;
        boolean endOfFlags = false;

        for (String arg : args) {
            LOG.debug("Current argument: {}", arg);

            if (arg.equals("--")) {
                endOfFlags = true;
                continue;
            }

            if (!endOfFlags) {
                // Flag argument: "--key" or "--key=value"
                if (arg.startsWith("-") && arg.length() > 1) {
                    if (deferredFlag != null) {
                        throw new UserException("was expecting a value, got '" + arg + "'");
                    }

                    final String[] chunks = arg.split("=", 2);
                    final Argument flag = flags.get(chunks[0]);
                    if (flag == null) {
                        throw new UserException("unsupported flag '" + chunks[0] + "'");
                    }

                    // Flag argument: "--key=value"
                    if (chunks.length == 2) {
                        switch (flag.action) {
                            case STORE_TRUE:
                            case STORE_FALSE:
                                throw new UserException("'" + chunks[0] + "' takes no parameter");
                            case APPEND:
                                append(params, flag.target, chunks[1]);
                                continue;
                            case STORE_VALUE:
                                store(params, flag.target, chunks[1]);
                                continue;
                            default:
                                throw new ProgrammerException("not sure what to do with action '" + flag.action + "'");
                        }
                    }

                    // Flag argument: "--bool" or "--key" "value"
                    switch (flag.action) {
                        case STORE_TRUE:
                            store(params, flag.target, true);
                            continue;
                        case STORE_FALSE:
                            store(params, flag.target, false);
                            continue;
                        case APPEND:
                        case STORE_VALUE:
                            LOG.debug("==> defer");
                            deferredFlag = flag;
                            continue;
                        default:
                            throw new ProgrammerException("not sure what to do with action '" + flag.action + "'");
                    }
                }

                // Flag argument: deferred value
                if (deferredFlag != null) {
                    if (deferredFlag.action == Action.APPEND) {
                        append(params, deferredFlag.target, arg);
                    }
                    else {
                        store(params, deferredFlag.target, arg);
                    }

                    deferredFlag = null;
                    continue;
                }
            }

            // Positional argument
            if (deferredPositional != null) {
                append(params, deferredPositional.target, arg);
                continue;
            }

            if (!positionalIterator.hasNext()) {
                throw new UserException("too many positional arguments");
            }

            final Argument positional = positionalIterator.next();
            if (positional.action == Action.APPEND) {
                deferredPositional = positional;
                append(params, positional.target, arg);
                continue;
            }

            store(params, positional.target, arg);
        }

        // Handle flag defaults if needed
        for (Argument flag : flags.values()) {
            switch (flag.action) {
                case APPEND:
                    ensureDefault(params.multiValues, flag.target, new ArrayList<>());
                    break;
                case STORE_TRUE:
                    ensureDefault(params.singleValues, flag.target, false);
                    break;
                case STORE_FALSE:
                    ensureDefault(params.singleValues, flag.target, true);
                    break;
                case STORE_VALUE:
                    ensureDefault(params.singleValues, flag.target, null);
                    break;
            }
        }

        // Handle leftover positionals
        if (positionalIterator.hasNext()) {
            throw new UserException("not enough positional arguments");
        }

        return params;
    }

    private static void append(Params p, String key, String value) {
        LOG.debug("==> append: {} = {}", key, value);
        p.multiValues.computeIfAbsent(key, (k) -> new ArrayList<>()).add(value);
    }

    private static <T> void ensureDefault(Map<String, T> map, String key, T value) {
        if (map.containsKey(key)) {
            return;
        }

        LOG.debug("==> default: {} => {}", key, value);
        map.put(key, value);
    }

    private static <T> T required(T o, String message) {
        if (o == null) {
            throw new ProgrammerException(message);
        }
        return o;
    }

    private static String required(String value, String message) {
        if (value == null) {
            throw new ProgrammerException(message);
        }

        value = value.trim();
        if (value.isEmpty()) {
            throw new ProgrammerException(message);
        }

        return value;
    }

    private static <T> void store(Params p, String key, T value) {
        LOG.debug("==> store: {} = {}", key, value);
        p.singleValues.put(key, value);
    }

    private static String toTarget(String name) {
        return required(name, "name is required").replaceFirst("^--", "");
    }

    private static final class Argument {
        private String name;
        private String target;
        private Action action;
    }

    public static final class Params {
        private final Map<String, Object> singleValues = new HashMap<>();
        private final Map<String, List<String>> multiValues = new HashMap<>();

        public String string(String key) {
            if (!singleValues.containsKey(key)) {
                throw new ProgrammerException("no string param named '" + key + "'");
            }

            final Object o = singleValues.get(key);
            if (o == null) {
                return null;
            }

            if (!(o instanceof String)) {
                throw new ProgrammerException("'" + key + "' is not a string");
            }

            return (String) o;
        }

        public boolean bool(String key) {
            if (!singleValues.containsKey(key)) {
                throw new ProgrammerException("no boolean param named '" + key + "'");
            }
            return (boolean) singleValues.get(key);
        }

        public List<String> strings(String key) {
            if (!multiValues.containsKey(key)) {
                throw new ProgrammerException("no multivalue param named '" + key + "'");
            }
            return multiValues.get(key);
        }
    }

    public static final class HelpRequestedException extends RuntimeException {
    }

    public static final class UserException extends RuntimeException {
        private UserException(String message) {
            super(message);
        }
    }

    public static final class ProgrammerException extends RuntimeException {
        private ProgrammerException(String message) {
            super(message);
        }
    }
}
