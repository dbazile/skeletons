package dbazile.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class AnsiColorTest {

    @Test
    public void decolorize() {
        assertEquals(
                "alpha red bravo yellow charlie green delta cyan echo blue foxtrot magenta golf grey hotel [WAT lol]",
                AnsiColor.decolorize("alpha [RED red] bravo [YELLOW yellow] charlie [GREEN green] delta [CYAN cyan] echo [BLUE blue] foxtrot [MAGENTA magenta] golf [GREY grey] hotel [WAT lol]"));
    }

    @Test
    public void colorize() {
        assertEquals(
                "alpha \u001B[31mred\u001B[0m bravo \u001B[33myellow\u001B[0m charlie \u001B[32mgreen\u001B[0m delta \u001B[36mcyan\u001B[0m echo \u001B[34mblue\u001B[0m foxtrot \u001B[35mmagenta\u001B[0m golf \u001B[37mgrey\u001B[0m hotel [WAT lol]",
                AnsiColor.colorize("alpha [RED red] bravo [YELLOW yellow] charlie [GREEN green] delta [CYAN cyan] echo [BLUE blue] foxtrot [MAGENTA magenta] golf [GREY grey] hotel [WAT lol]"));
    }
}
