public class CommandNode {
    public String command;
    public CommandNode next;

    public CommandNode(String command) {
        this.command = command;
        this.next = null;
    }
}