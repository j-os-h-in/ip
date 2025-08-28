package king.parser;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import king.KingException;

public class KingParserTest {
    private KingParser kingParser;

    @BeforeEach
    public void setUp() {
        kingParser = new KingParser();
    }

    @Test
    public void help1_success() throws KingException {
        kingParser.setNewInput("help");
        assertTrue(kingParser.checkParser(KingParser.Commands.HELP));
    }

    @Test
    public void help2_success() throws KingException {
        kingParser.setNewInput("   help   ");
        assertTrue(kingParser.checkParser(KingParser.Commands.HELP));
    }

    @Test
    public void help1_fail() throws KingException {
        kingParser.setNewInput("   help   me ");
        assertFalse(kingParser.checkParser(KingParser.Commands.HELP));
    }

    @Test
    public void list1_success() throws KingException {
        kingParser.setNewInput("list");
        assertTrue(kingParser.checkParser(KingParser.Commands.LIST));
    }

    @Test
    public void list2_success() throws KingException {
        kingParser.setNewInput("   list     ");
        assertTrue(kingParser.checkParser(KingParser.Commands.LIST));
    }

    @Test
    public void list1_fail() throws KingException {
        kingParser.setNewInput("   list  x  ");
        assertFalse(kingParser.checkParser(KingParser.Commands.LIST));
    }

    @Test
    public void due1_success() throws KingException {
        kingParser.setNewInput("   due    2025-23-10 ");
        assertTrue(kingParser.checkParser(KingParser.Commands.DUE));
    }

    @Test
    public void due1_fail() throws KingException {
        kingParser.setNewInput("   due     ");
        assertThrows(KingException.class, () -> {
            kingParser.checkParser(KingParser.Commands.DUE);
        });
    }

    @Test
    public void todo1_success() throws KingException {
        kingParser.setNewInput("   todo    Todo Task ");
        assertTrue(kingParser.checkParser(KingParser.Commands.TODO));
    }

    @Test
    public void todo1_fail() throws KingException {
        kingParser.setNewInput("      todo     ");
        assertThrows(KingException.class, () -> {
            kingParser.checkParser(KingParser.Commands.TODO);
        });
    }

    @Test
    public void deadline1_success() throws KingException {
        kingParser.setNewInput("deadline Deadline Task /by 2025-10-23 ");
        assertTrue(kingParser.checkParser(KingParser.Commands.DEADLINE));
    }

    @Test
    public void deadline1_fail() throws KingException {
        kingParser.setNewInput("      deadline     ");
        assertThrows(KingException.class, () -> {
            kingParser.checkParser(KingParser.Commands.DEADLINE);
        });
    }

    @Test
    public void deadline2_fail() throws KingException {
        kingParser.setNewInput("      deadline   Deadline Task  ");
        assertThrows(KingException.class, () -> {
            kingParser.checkParser(KingParser.Commands.DEADLINE);
        });
    }

    @Test
    public void deadline3_fail() throws KingException {
        kingParser.setNewInput("      deadline   Deadline Task /by  ");
        assertThrows(KingException.class, () -> {
            kingParser.checkParser(KingParser.Commands.DEADLINE);
        });
    }

    @Test
    public void event1_success() throws KingException {
        kingParser.setNewInput("event Event Task /from 2025-10-23 /to 2025-10-31 ");
        assertTrue(kingParser.checkParser(KingParser.Commands.EVENT));
    }

    @Test
    public void event1_fail() throws KingException {
        kingParser.setNewInput("      event     ");
        assertThrows(KingException.class, () -> {
            kingParser.checkParser(KingParser.Commands.EVENT);
        });
    }

    @Test
    public void event2_fail() throws KingException {
        kingParser.setNewInput("      event   Event Task  ");
        assertThrows(KingException.class, () -> {
            kingParser.checkParser(KingParser.Commands.EVENT);
        });
    }

    @Test
    public void event3_fail() throws KingException {
        kingParser.setNewInput("      event   Event Task /from  ");
        assertThrows(KingException.class, () -> {
            kingParser.checkParser(KingParser.Commands.EVENT);
        });
    }

    @Test
    public void event4_fail() throws KingException {
        kingParser.setNewInput("      event   Event Task /from  2025-10-23 /to");
        assertThrows(KingException.class, () -> {
            kingParser.checkParser(KingParser.Commands.EVENT);
        });
    }

    @Test
    public void mark1_success() throws KingException {
        kingParser.setNewInput("   mark 3 ");
        assertTrue(kingParser.checkParser(KingParser.Commands.MARK));
    }


    @Test
    public void mark1_fail() throws KingException {
        kingParser.setNewInput("  mark ");
        assertThrows(KingException.class, () -> {
            kingParser.checkParser(KingParser.Commands.MARK);
        });
    }

    @Test
    public void unmark1_success() throws KingException {
        kingParser.setNewInput("   unmark 3 ");
        assertTrue(kingParser.checkParser(KingParser.Commands.UNMARK));
    }

    @Test
    public void unmark1_fail() throws KingException {
        kingParser.setNewInput("  unmark ");
        assertThrows(KingException.class, () -> {
            kingParser.checkParser(KingParser.Commands.UNMARK);
        });
    }

    @Test
    public void delete1_success() throws KingException {
        kingParser.setNewInput("   delete 3 ");
        assertTrue(kingParser.checkParser(KingParser.Commands.DELETE));
    }


    @Test
    public void delete1_fail() throws KingException {
        kingParser.setNewInput("  delete ");
        assertThrows(KingException.class, () -> {
            kingParser.checkParser(KingParser.Commands.DELETE);
        });
    }

    @Test
    public void bye1_success() throws KingException {
        kingParser.setNewInput("   bye");
        assertTrue(kingParser.checkParser(KingParser.Commands.BYE));
    }
}
