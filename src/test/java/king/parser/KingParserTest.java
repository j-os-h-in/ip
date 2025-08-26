package king.parser;

import king.KingException;
import king.storage.KingStorage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class KingParserTest {
    private KingParser kingParser;

    @BeforeEach
    public void setUp() {
        kingParser = new KingParser();
    }

    @Test
    public void help_1_success() throws KingException {
        kingParser.setNewInput("help");
        assertTrue(kingParser.checkParser(KingParser.Commands.HELP));
    }

    @Test
    public void help_2_success() throws KingException {
        kingParser.setNewInput("   help   ");
        assertTrue(kingParser.checkParser(KingParser.Commands.HELP));
    }

    @Test
    public void help_1_fail() throws KingException {
        kingParser.setNewInput("   help   me ");
        assertFalse(kingParser.checkParser(KingParser.Commands.HELP));
    }

    @Test
    public void list_1_success() throws KingException {
        kingParser.setNewInput("list");
        assertTrue(kingParser.checkParser(KingParser.Commands.LIST));
    }

    @Test
    public void list_2_success() throws KingException {
        kingParser.setNewInput("   list     ");
        assertTrue(kingParser.checkParser(KingParser.Commands.LIST));
    }

    @Test
    public void list_1_fail() throws KingException {
        kingParser.setNewInput("   list  x  ");
        assertFalse(kingParser.checkParser(KingParser.Commands.LIST));
    }

    @Test
    public void due_1_success() throws KingException {
        kingParser.setNewInput("   due    2025-23-10 ");
        assertTrue(kingParser.checkParser(KingParser.Commands.DUE));
    }

    @Test
    public void due_1_fail() throws KingException {
        kingParser.setNewInput("   due     ");
        assertThrows(KingException.class, () -> {
            kingParser.checkParser(KingParser.Commands.DUE);
        });
    }

    @Test
    public void todo_1_success() throws KingException {
        kingParser.setNewInput("   todo    Todo Task ");
        assertTrue(kingParser.checkParser(KingParser.Commands.TODO));
    }

    @Test
    public void todo_1_fail() throws KingException {
        kingParser.setNewInput("      todo     ");
        assertThrows(KingException.class, () -> {
            kingParser.checkParser(KingParser.Commands.TODO);
        });
    }

    @Test
    public void deadline_1_success() throws KingException {
        kingParser.setNewInput("deadline Deadline Task /by 2025-10-23 ");
        assertTrue(kingParser.checkParser(KingParser.Commands.DEADLINE));
    }

    @Test
    public void deadline_1_fail() throws KingException {
        kingParser.setNewInput("      deadline     ");
        assertThrows(KingException.class, () -> {
            kingParser.checkParser(KingParser.Commands.DEADLINE);
        });
    }

    @Test
    public void deadline_2_fail() throws KingException {
        kingParser.setNewInput("      deadline   Deadline Task  ");
        assertThrows(KingException.class, () -> {
            kingParser.checkParser(KingParser.Commands.DEADLINE);
        });
    }

    @Test
    public void deadline_3_fail() throws KingException {
        kingParser.setNewInput("      deadline   Deadline Task /by  ");
        assertThrows(KingException.class, () -> {
            kingParser.checkParser(KingParser.Commands.DEADLINE);
        });
    }

    @Test
    public void event_1_success() throws KingException {
        kingParser.setNewInput("event Event Task /from 2025-10-23 /to 2025-10-31 ");
        assertTrue(kingParser.checkParser(KingParser.Commands.EVENT));
    }

    @Test
    public void event_1_fail() throws KingException {
        kingParser.setNewInput("      event     ");
        assertThrows(KingException.class, () -> {
            kingParser.checkParser(KingParser.Commands.EVENT);
        });
    }

    @Test
    public void event_2_fail() throws KingException {
        kingParser.setNewInput("      event   Event Task  ");
        assertThrows(KingException.class, () -> {
            kingParser.checkParser(KingParser.Commands.EVENT);
        });
    }

    @Test
    public void event_3_fail() throws KingException {
        kingParser.setNewInput("      event   Event Task /from  ");
        assertThrows(KingException.class, () -> {
            kingParser.checkParser(KingParser.Commands.EVENT);
        });
    }

    @Test
    public void event_4_fail() throws KingException {
        kingParser.setNewInput("      event   Event Task /from  2025-10-23 /to");
        assertThrows(KingException.class, () -> {
            kingParser.checkParser(KingParser.Commands.EVENT);
        });
    }

    @Test
    public void mark_1_success() throws KingException {
        kingParser.setNewInput("   mark 3 ");
        assertTrue(kingParser.checkParser(KingParser.Commands.MARK));
    }


    @Test
    public void mark_1_fail() throws KingException {
        kingParser.setNewInput("  mark ");
        assertThrows(KingException.class, () -> {
            kingParser.checkParser(KingParser.Commands.MARK);
        });
    }

    @Test
    public void unmark_1_success() throws KingException {
        kingParser.setNewInput("   unmark 3 ");
        assertTrue(kingParser.checkParser(KingParser.Commands.UNMARK));
    }

    @Test
    public void unmark_1_fail() throws KingException {
        kingParser.setNewInput("  unmark ");
        assertThrows(KingException.class, () -> {
            kingParser.checkParser(KingParser.Commands.UNMARK);
        });
    }

    @Test
    public void delete_1_success() throws KingException {
        kingParser.setNewInput("   delete 3 ");
        assertTrue(kingParser.checkParser(KingParser.Commands.DELETE));
    }


    @Test
    public void delete_1_fail() throws KingException {
        kingParser.setNewInput("  delete ");
        assertThrows(KingException.class, () -> {
            kingParser.checkParser(KingParser.Commands.DELETE);
        });
    }

    @Test
    public void bye_1_success() throws KingException {
        kingParser.setNewInput("   bye");
        assertTrue(kingParser.checkParser(KingParser.Commands.BYE));
    }

}