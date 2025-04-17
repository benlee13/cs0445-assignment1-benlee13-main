import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Before;
import org.junit.Test;

public class A1Test {

    private A1Interface processor;

    public A1Test(){
        
    }

    @Before
    public void setup() {
        processor = new A1();
    }

    private CommandNode buildCommands(String... cmds) {
        CommandNode head = null, tail = null;
        for (String cmd : cmds) {
            CommandNode node = new CommandNode(cmd);
            if (head == null) head = node;
            else tail.next = node;
            tail = node;
        }
        return head;
    }

    @Test
    public void testBasicAddResize() {
        String[] arr = new String[3];
        arr[0] = "a";
        arr[1] = "b";
        arr[2] = "c";
        CommandNode cmds = buildCommands("add 1 x");
        arr = processor.processArrayCommands(arr, cmds);
        String[] expected = {"a", "x", "b", "c", null, null};
        assertArrayEquals(expected, arr);
    }
    
    @Test
    public void testMultipleAddsAndResize() {
        String[] arr = new String[2];
        arr[0] = "1";
        arr[1] = "2";
        CommandNode cmds = buildCommands("add 2 3", "add 3 4", "add 4 5");
        arr = processor.processArrayCommands(arr, cmds);
        String[] expected = {"1", "2", "3", "4", "5", null, null, null};
        assertArrayEquals(expected, arr);
    }

    @Test
    public void testSwapValidAndInvalid() {
        String[] arr = new String[] {"a", "b", "c", null, null};
        CommandNode cmds = buildCommands("swap 0 2", "swap 0 10");
        arr = processor.processArrayCommands(arr, cmds);
        assertEquals("c", arr[0]);
        assertEquals("b", arr[1]);
        assertEquals("a", arr[2]);
    }

    @Test
    public void testRemoveAndInvalidIndex() {
        String[] arr = new String[] {"x", "y", "z", null};
        CommandNode cmds = buildCommands("remove 1", "remove 5");
        arr = processor.processArrayCommands(arr, cmds);
        String[] expected = {"x", "z", null, null};
        assertArrayEquals(expected, arr);
    }

    @Test
    public void testSkipForwardAndBackward() {
        String[] arr = new String[5];
        arr[0] = "0";
        arr[1] = "1";
        CommandNode cmds = buildCommands(
            "add 2 A", "skip +2", "add 3 SKIPPED", "add 2 B", "ifvalue 2 A skip -1", "add 4 C"
        );
        arr = processor.processArrayCommands(arr, cmds);
        String[] expected = {"0", "1", "B", "A", "C"};
        assertArrayEquals(expected, arr);
    }

    @Test
    public void testIfValueConditionTrueAndFalse() {
        String[] arr = new String[] {"one", "two", "three", null};
        CommandNode cmds = buildCommands("ifvalue 1 two add 1 match", "ifvalue 0 none add 0 fail");
        arr = processor.processArrayCommands(arr, cmds);
        String[] expected = {"one", "match", "two", "three"};
        assertArrayEquals(expected, arr);
    }

    @Test
    public void testEmptyCommandList() {
        String[] arr = new String[] {"a", "b", "c"};
        arr = processor.processArrayCommands(arr, null);
        String[] expected = {"a", "b", "c"};
        assertArrayEquals(expected, arr);
    }

    @Test
    public void testArrayExpansionAfterMultipleCommands() {
        String[] arr = new String[1];
        arr[0] = "0";
        CommandNode cmds = buildCommands("add 1 1", "add 2 2", "add 3 3", "add 4 4", "add 5 5");
        arr = processor.processArrayCommands(arr, cmds);
        String[] expected = {"0", "1", "2", "3", "4", "5", null, null};
        assertArrayEquals(expected, arr);
    }


    @Test
    public void testLinkedListInsertAndDelete() {
        Node head = new Node("a");
        head.next = new Node("b");
        head.next.next = new Node("c");
        String[] cmds = {"insert 1 x", "delete 2"};
        Node result = processor.processLinkedListCommands(head, cmds);
        assertEquals("a", result.data);
        assertEquals("x", result.next.data);
        assertEquals("c", result.next.next.data);
    }

    @Test
    public void testLinkedListMoveAndReverse() {
        Node head = new Node("1");
        head.next = new Node("2");
        head.next.next = new Node("3");
        String[] cmds = {"move 2 0", "reverse"};
        Node result = processor.processLinkedListCommands(head, cmds);
        assertEquals("2", result.data);
        assertEquals("1", result.next.data);
        assertEquals("3", result.next.next.data);
    }

    @Test
    public void testLinkedListIfValueAndSkip() {
        Node head = new Node("x");
        head.next = new Node("y");
        String[] cmds = {"ifvalue 0 x insert 1 z", "skip +2", "insert 2 fail"};
        Node result = processor.processLinkedListCommands(head, cmds);
        assertEquals("x", result.data);
        assertEquals("z", result.next.data);
        assertEquals("y", result.next.next.data);
        assertNull(result.next.next.next);
    }

    @Test
    public void testLinkedListOutOfBoundsSkipAndInvalidDelete() {
        Node head = new Node("start");
        String[] cmds = {"skip +10", "delete 5"};
        Node result = processor.processLinkedListCommands(head, cmds);
        assertEquals("start", result.data);
        assertNull(result.next);
    }
    
}
