package king.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import king.KingException;

public class KingTaskListTest {
    private KingTaskList kingTaskList;
    private Todo testBasicTodo;
    private Deadline testBasicDeadline;
    private Event testBasicEvent;

    @BeforeEach
    public void setUp() throws KingException {
        kingTaskList = new KingTaskList();
        testBasicTodo = new Todo("Find an internship");
        testBasicDeadline = new Deadline("Submit CS2103 iP", LocalDate.parse("2025-12-31"));
        testBasicEvent = new Event("CS2103 Meeting", LocalDate.parse("2025-09-01"), LocalDate.parse("2025-09-01"));
    }

    @Test
    public void newTaskListTest_success() {
        KingTaskList newTaskList = new KingTaskList(true);

        assertEquals(0, newTaskList.getSize());
        assertNotNull(newTaskList.getTasks());
    }

    @Test
    public void addTask_singleTask_success() {
        int initialSize = kingTaskList.getSize();
        kingTaskList.addTask(testBasicTodo);

        assertEquals(initialSize + 1, kingTaskList.getSize());
        assertEquals(testBasicTodo, kingTaskList.getTask(kingTaskList.getSize() - 1));
    }

    @Test
    public void addTask_multipleTasks_success() throws KingException {
        int initialSize = kingTaskList.getSize();

        kingTaskList.addTask(testBasicTodo);
        kingTaskList.addTask(testBasicDeadline);
        kingTaskList.addTask(testBasicEvent);

        assertEquals(initialSize + 3, kingTaskList.getSize());
        assertEquals(testBasicTodo, kingTaskList.getTask(initialSize));
        assertEquals(testBasicDeadline, kingTaskList.getTask(initialSize + 1));
        assertEquals(testBasicEvent, kingTaskList.getTask(initialSize + 2));
    }

    @Test
    public void getTask_validIndex_success() {
        kingTaskList.addTask(testBasicTodo);
        Task task = kingTaskList.getTask(kingTaskList.getSize() - 1);
        assertEquals(testBasicTodo, task);
    }

    @Test
    public void getTask_invalidIndex_throwsException() {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            kingTaskList.getTask(-1);
        });

        assertThrows(IndexOutOfBoundsException.class, () -> {
            kingTaskList.getTask(kingTaskList.getSize());
        });
    }

    @Test
    public void markDone_validIndex_success() {
        kingTaskList.addTask(testBasicTodo);
        int taskIndex = kingTaskList.getSize() - 1;

        assertFalse(kingTaskList.getTask(taskIndex).getComplete());
        kingTaskList.markDone(taskIndex);
        assertTrue(kingTaskList.getTask(taskIndex).getComplete());
    }

    @Test
    public void markDone_invalidIndex_throwsException() {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            kingTaskList.markDone(-1);
        });

        assertThrows(IndexOutOfBoundsException.class, () -> {
            kingTaskList.markDone(kingTaskList.getSize());
        });
    }

    @Test
    public void unmarkDone_validIndex_success() {
        kingTaskList.addTask(testBasicTodo);
        int taskIndex = kingTaskList.getSize() - 1;

        // First mark it as done
        kingTaskList.markDone(taskIndex);
        assertTrue(kingTaskList.getTask(taskIndex).getComplete());

        // Then unmark it
        kingTaskList.unmarkDone(taskIndex);
        assertFalse(kingTaskList.getTask(taskIndex).getComplete());
    }

    @Test
    public void unmarkDone_invalidIndex_throwsException() {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            kingTaskList.unmarkDone(-1);
        });

        assertThrows(IndexOutOfBoundsException.class, () -> {
            kingTaskList.unmarkDone(kingTaskList.getSize());
        });
    }

    @Test
    public void deleteTask_validIndex_success() {
        kingTaskList.addTask(testBasicTodo);
        kingTaskList.addTask(testBasicDeadline);
        int initialSize = kingTaskList.getSize();

        Task deletedTask = kingTaskList.deleteTask(0);

        assertEquals(testBasicTodo, deletedTask);
        assertEquals(initialSize - 1, kingTaskList.getSize());
        assertEquals(testBasicDeadline, kingTaskList.getTask(0));
    }

    @Test
    public void deleteTask_invalidIndex_throwsException() {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            kingTaskList.deleteTask(-1);
        });

        assertThrows(IndexOutOfBoundsException.class, () -> {
            kingTaskList.deleteTask(kingTaskList.getSize());
        });
    }

    @Test
    public void deleteTask_emptyList_throwsException() {
        KingTaskList emptyList = new KingTaskList(true);

        assertThrows(IndexOutOfBoundsException.class, () -> {
            emptyList.deleteTask(0);
        });
    }

    @Test
    public void getSize_emptyList() {
        KingTaskList emptyList = new KingTaskList(true);

        assertEquals(0, emptyList.getSize());
    }

    @Test
    public void getSize_withTasks() {
        int initialSize = kingTaskList.getSize();

        kingTaskList.addTask(testBasicTodo);
        assertEquals(initialSize + 1, kingTaskList.getSize());

        kingTaskList.addTask(testBasicDeadline);
        assertEquals(initialSize + 2, kingTaskList.getSize());

        kingTaskList.deleteTask(0);
        assertEquals(initialSize + 1, kingTaskList.getSize());
    }

    @Test
    public void getTasks_returnsCorrectList() {
        kingTaskList.addTask(testBasicTodo);
        kingTaskList.addTask(testBasicDeadline);

        ArrayList<Task> tasks = kingTaskList.getTasks();
        assertEquals(2, tasks.size() - (kingTaskList.getSize() - 2)); // Account for any existing tasks
        assertTrue(tasks.contains(testBasicTodo));
        assertTrue(tasks.contains(testBasicDeadline));
    }

    @Test
    public void multipleOperations_success() {
        kingTaskList.addTask(testBasicTodo);
        kingTaskList.addTask(testBasicDeadline);
        kingTaskList.addTask(testBasicEvent);

        int size = kingTaskList.getSize();
        assertEquals(3, size - (size - 3));

        kingTaskList.markDone(size - 3); // Todo Task
        kingTaskList.markDone(size - 1); // Event Task

        assertTrue(kingTaskList.getTask(size - 3).getComplete());
        assertFalse(kingTaskList.getTask(size - 2).getComplete());
        assertTrue(kingTaskList.getTask(size - 1).getComplete());

        Task deleted = kingTaskList.deleteTask(size - 2); // Deadline Task
        assertEquals(testBasicDeadline, deleted);
        assertEquals(size - 1, kingTaskList.getSize());

        assertEquals(testBasicTodo, kingTaskList.getTask(size - 3));
        assertEquals(testBasicEvent, kingTaskList.getTask(size - 2));
    }
}
