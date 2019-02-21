package dbazile.util;

import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dbazile.util.ArgumentParser.Action;
import dbazile.util.ArgumentParser.HelpRequestedException;
import dbazile.util.ArgumentParser.Params;
import dbazile.util.ArgumentParser.ProgrammerException;
import dbazile.util.ArgumentParser.UserException;

import static org.junit.Assert.*;

public class ArgumentParserTest {
    private static final Logger LOG = LoggerFactory.getLogger(ArgumentParserTest.class);

    @Test
    public void usage() {
        final String usage = new ArgumentParser()
                .add("--add-color", "colors", Action.APPEND)
                .add("--foo", Action.STORE_TRUE)
                .add("--no-foo", "foo", Action.STORE_FALSE)
                .add("--keyvalue1")
                .add("--keyvalue2")
                .add("outfile")
                .add("infiles", Action.APPEND)
                .usage("myservice");

        LOG.debug(usage);

        assertEquals("Usage: myservice" +
                     " [-h | --help]" +
                     " [--keyvalue2=VALUE]" +
                     " [--foo]" +
                     " [--add-color=VALUE]" +
                     " [--keyvalue1=VALUE]" +
                     " [--no-foo]" +
                     " OUTFILE" +
                     " INFILES...",
                usage);
    }

    @Test(expected = HelpRequestedException.class)
    public void parse__flags__helpShort() {
        new ArgumentParser()
                .parse(new String[]{"-h"});
    }

    @Test(expected = HelpRequestedException.class)
    public void parse__flags__helpLong() {
        new ArgumentParser()
                .parse(new String[]{"--help"});
    }

    @Test
    public void parse__flags__short() {
        final Params p = new ArgumentParser()
                .add("-D", "debug", Action.STORE_TRUE)
                .add("-i", "included")
                .add("-a", "also_included")
                .add("-o", "omitted")
                .parse(new String[]{
                        "-D",
                        "-i=lorem",
                        "-a", "ipsum",
                });

        assertTrue(p.bool("debug"));
        assertEquals("lorem", p.string("included"));
        assertEquals("ipsum", p.string("also_included"));
        assertNull(p.string("omitted"));
    }

    @Test
    public void parse__flags__singleValue__combined() {
        final Params p = new ArgumentParser()
                .add("--myflag")
                .parse(new String[]{
                        "--myflag=test-value",
                });

        assertEquals("test-value", p.string("myflag"));
    }

    @Test
    public void parse__flags__singleValue__split() {
        final Params p = new ArgumentParser()
                .add("--myflag")
                .parse(new String[]{
                        "--myflag", "test-value",
                });

        assertEquals("test-value", p.string("myflag"));
    }

    @Test
    public void parse__flags__multiValue__combined() {
        final Params p = new ArgumentParser()
                .add("--add-color", "colors", Action.APPEND)
                .parse(new String[]{
                        "--add-color=red",
                        "--add-color=green",
                        "--add-color=blue",
                });

        assertEquals(List.of("red", "green", "blue"), p.strings("colors"));
    }

    @Test
    public void parse__flags__multiValue__split() {
        final Params p = new ArgumentParser()
                .add("--add-color", "colors", Action.APPEND)
                .parse(new String[]{
                        "--add-color", "red",
                        "--add-color", "green",
                        "--add-color", "blue",
                });

        assertEquals(List.of("red", "green", "blue"), p.strings("colors"));
    }

    @Test
    public void parse__flags__bool__storesTrueIfPresent() {
        final Params p = new ArgumentParser()
                .add("--myflag", Action.STORE_TRUE)
                .parse(new String[]{"--myflag"});

        assertTrue(p.bool("myflag"));
    }

    @Test
    public void parse__flags__bool__storesFalseIfAbsent() {
        final Params p = new ArgumentParser()
                .add("--myflag", Action.STORE_TRUE)
                .parse(new String[]{});

        assertFalse(p.bool("myflag"));
    }

    @Test
    public void parse__flags__bool__storesFalseIfPresent() {
        final Params p = new ArgumentParser()
                .add("--myflag", Action.STORE_FALSE)
                .parse(new String[]{"--myflag"});

        assertFalse(p.bool("myflag"));
    }

    @Test
    public void parse__flags__bool__storesTrueIfAbsent() {
        final Params p = new ArgumentParser()
                .add("--myflag", Action.STORE_FALSE)
                .parse(new String[]{});

        assertTrue(p.bool("myflag"));
    }

    @Test
    public void parse__flags__bool__canOverride() {
        final Params p = new ArgumentParser()
                .add("--myflag", Action.STORE_TRUE)
                .add("--no-myflag", "myflag", Action.STORE_FALSE)
                .parse(new String[]{"--myflag", "--no-myflag"});

        assertFalse(p.bool("myflag"));
    }

    @Test(expected = UserException.class)
    public void parse__flags__rejectsUnexpected() {
        new ArgumentParser()
                .add("--foo")
                .parse(new String[]{
                        "--definitely-not-a-foo",
                });
    }

    @Test
    public void parse__positionals() {
        final Params p = new ArgumentParser()
                .add("mypositional")
                .parse(new String[]{"test-value"});

        assertEquals("test-value", p.string("mypositional"));
    }

    @Test
    public void parse__positionals__supportsTailAppending() {
        final Params p = new ArgumentParser()
                .add("files", Action.APPEND)
                .parse(new String[]{"file1", "file2", "file3", "file4", "file5"});

        assertEquals(List.of("file1", "file2", "file3", "file4", "file5"), p.strings("files"));
    }

    @Test(expected = ProgrammerException.class)
    public void parse__positionals__rejectsIfDuplicate() {
        new ArgumentParser()
                .add("foo")
                .add("foo");
    }

    @Test(expected = UserException.class)
    public void parse__positionals__rejectsIfMissing() {
        new ArgumentParser()
                .add("mypositional")
                .parse(new String[]{});
    }

    @Test(expected = UserException.class)
    public void parse__positionals__rejectsIfTooFew() {
        new ArgumentParser()
                .add("foo")
                .parse(new String[]{
                        "one",
                        "two",
                        "three",
                });
    }

    @Test(expected = ProgrammerException.class)
    public void parse__positionals__rejectsIfAddingNonTailAppender() {
        new ArgumentParser()
                .add("foo", Action.APPEND)
                .add("foo2");
    }

    @Test(expected = UserException.class)
    public void parse__positionals__rejectsUnexpected() {
        new ArgumentParser()
                .add("--foo")
                .parse(new String[]{
                        "whee",
                });
    }

    @Test
    public void parse__mixed() {
        final Params p = new ArgumentParser()
                .add("--add-color", "colors", Action.APPEND)
                .add("--foo", Action.STORE_TRUE)
                .add("--no-foo", "foo", Action.STORE_FALSE)
                .add("--keyvalue1")
                .add("--keyvalue2")
                .add("infile")
                .add("outfile")
                .parse(new String[]{
                        "--no-foo",
                        "--keyvalue1", "test-keyvalue-1",
                        "--keyvalue2=test-keyvalue-2",
                        "--add-color=red",
                        "--add-color=green",
                        "--add-color=blue",
                        "test-filename-1",
                        "test-filename-2"
                });

        assertFalse(p.bool("foo"));
        assertEquals("test-keyvalue-1", p.string("keyvalue1"));
        assertEquals("test-keyvalue-2", p.string("keyvalue2"));
        assertEquals("test-filename-1", p.string("infile"));
        assertEquals("test-filename-2", p.string("outfile"));
        assertEquals(List.of("red", "green", "blue"), p.strings("colors"));
    }
}
